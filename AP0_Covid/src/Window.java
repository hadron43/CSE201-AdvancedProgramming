import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.time.LocalDate;
import java.util.Vector;

public class Window extends JFrame {
    Patient[] patients;

    JTextField day, mon, year;
    JCheckBox A, B, C, D;

    JPanel searchDate, searchTower, top, middle, left;
    JScrollPane patientRecord, towerRecord;

    JButton search;

    Window(Patient[] patients) {
        setTitle("Covid 19 Tracker");
        this.patients = patients;
        tableData = new Vector<>();

        day = new JTextField();
        mon = new JTextField();
        year = new JTextField();
        day.setPreferredSize(new Dimension(50, 20));
        mon.setPreferredSize(new Dimension(50, 20));
        year.setPreferredSize(new Dimension(50, 20));

        A = new JCheckBox("A", false);
        B = new JCheckBox("B", false);
        C = new JCheckBox("C", false);
        D = new JCheckBox("D", false);

        search = new JButton("Search");
        search.addActionListener(actionEvent -> {
            try{
                int d = Integer.parseInt(day.getText());
                int m = Integer.parseInt(mon.getText());
                int y = Integer.parseInt(year.getText());

                boolean isA, isB, isC, isD;
                isA = A.isSelected();
                isB = B.isSelected();
                isC = C.isSelected();
                isD = D.isSelected();

                LocalDate date = LocalDate.of(y, m, d);

                if(LocalDate.of(2020, 4, 1).isAfter(date) ||
                    date.isAfter(LocalDate.of(2020, 8, 31))) {
                    String message = "Date Range: April 2020 to August 2020";
                    JOptionPane.showMessageDialog(new JFrame(), message, "Data Not Found",
                            JOptionPane.ERROR_MESSAGE);
                }
                else {
                    displayResult(date, isA, isB, isC, isD);
                }
            }
            catch (Exception e){
                String message = "Exception Encountered: " + e.toString();
                JOptionPane.showMessageDialog(new JFrame(), "Invalid Date!", "Error Encountered",
                        JOptionPane.ERROR_MESSAGE);
                System.out.println(message);
            }
        });

        searchDate = new JPanel();
        searchTower = new JPanel();
        top = new JPanel();
        middle = new JPanel();
        left = new JPanel();
        middle.setLayout(new BoxLayout(middle, BoxLayout.Y_AXIS));
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.add(new Label("Enter your query"));

        towerRecord = new JScrollPane(left);
        towerRecord.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        towerRecord.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        patientRecord = new JScrollPane(middle);
        patientRecord.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        patientRecord.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        searchDate.add(new JLabel("Day"));
        searchDate.add(day);
        searchDate.add(new JLabel("Month"));
        searchDate.add(mon);
        searchDate.add(new JLabel("Year"));
        searchDate.add(year);
        searchTower.add(A);
        searchTower.add(B);
        searchTower.add(C);
        searchTower.add(D);

        top.add(searchDate);
        top.add(searchTower);
        top.add(search);

        setLayout(new BorderLayout());
        JScrollPane buttons = new JScrollPane(top);
        buttons.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        add(buttons, BorderLayout.PAGE_START);
        add(patientRecord, BorderLayout.CENTER);
        add(towerRecord, BorderLayout.LINE_START);
        setSize(700, 430);
        setMaximizedBounds(new Rectangle(675, 390));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    Vector<Vector<Object>> tableData;
    int recA, recB, recC, recD, actA, actB, actC, actD;

    void displayResult(LocalDate date, boolean isA, boolean isB, boolean isC, boolean isD){
        towerRecord.setVisible(false);
        patientRecord.setVisible(false);
        recA = recB = recC = recD = actA = actB = actC = actD = 0;

        tableData.clear();
        for (Patient p : patients) {
            if (p.recovered(date)) {
                if (p.getTower() == 'A')
                    recA++;
                else if (p.getTower() == 'B')
                    recB++;
                else if (p.getTower() == 'C')
                    recC++;
                else if (p.getTower() == 'D')
                    recD++;
            }
            else if(p.hasBeenInfected(date)) {
                if (p.getTower() == 'A')
                    actA++;
                else if (p.getTower() == 'B')
                    actB++;
                else if (p.getTower() == 'C')
                    actC++;
                else if (p.getTower() == 'D')
                    actD++;
            }

            if(p.hasBeenInfected(date) &&
                    ((p.getTower() == 'A' && isA) || (p.getTower() == 'B' && isB) || (p.getTower() == 'C' && isC) || (p.getTower() == 'D' && isD))){
                Vector<Object> v = new Vector<>();
                v.add(p.getName());
                v.add(p.getAge());
                v.add(p.getTower());
                v.add(p.getReportDate());
                v.add(p.getRecoverDate());
                v.add(p.recovered(date) ? "Yes" : "No");
                tableData.add(v);
            }
        }
        Vector<Object> headings =  new Vector<>();
        headings.add("Name");
        headings.add("Age");
        headings.add("Tower");
        headings.add("Reported On");
        headings.add("Recovery Date");
        headings.add("Recovered");
        JTable table = new JTable(tableData, headings){
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        middle.removeAll();

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );

        for(int x=1; tableData.size()>0 && x<tableData.get(0).size(); x++){
            table.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
        }

        middle.add(table.getTableHeader());
        middle.add(table);

        left.removeAll();

        if(isA){
            left.add(new Label(""));
            left.add(new JLabel("Tower A "));
            left.add(new JLabel("Active: "+actA+" "));
            left.add(new JLabel("Recovered: "+recA+" "));
        }
        if(isB){
            left.add(new Label(""));
            left.add(new JLabel("Tower B "));
            left.add(new JLabel("Active: "+actB+" "));
            left.add(new JLabel("Recovered: "+recB+" "));
        }
        if(isC){
            left.add(new Label(""));
            left.add(new JLabel("Tower C "));
            left.add(new JLabel("Active: "+actC+" "));
            left.add(new JLabel("Recovered: "+recC+" "));
        }
        if(isD){
            left.add(new Label(""));
            left.add(new JLabel("Tower D "));
            left.add(new JLabel("Active: "+actD+" "));
            left.add(new JLabel("Recovered: "+recD+" "));
        }
        left.add(new Label(""));

        towerRecord.setVisible(true);
        patientRecord.setVisible(true);
    }
}
