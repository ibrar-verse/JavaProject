import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class CheckInScreen extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private CheckInDAO dao = new CheckInDAO();

    public CheckInScreen() {
        setTitle("Check In");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("✅ CHECK IN LOGS", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 22));
        header.setOpaque(true);
        header.setBackground(new Color(34, 34, 34));
        header.setForeground(Color.YELLOW);
        header.setPreferredSize(new Dimension(700, 55));
        add(header, BorderLayout.NORTH);

        // Table
        String[] columns = {"Log ID", "First Name", "Last Name", "Check In Time"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(231, 76, 60));
        table.getTableHeader().setForeground(Color.WHITE);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Bottom
        JPanel bottom = new JPanel(new FlowLayout());
        bottom.setBackground(new Color(34, 34, 34));

        JTextField memberIDField = new JTextField(10);
        JButton btnCheckIn = createButton("✅ Check In", new Color(46, 204, 113));
        JButton btnRefresh = createButton("🔄 Refresh", new Color(52, 152, 219));

        bottom.add(new JLabel("Member ID:") {{setForeground(Color.WHITE);}});
        bottom.add(memberIDField);
        bottom.add(btnCheckIn);
        bottom.add(btnRefresh);
        add(bottom, BorderLayout.SOUTH);

        loadCheckIns();

        btnRefresh.addActionListener(e -> loadCheckIns());

        btnCheckIn.addActionListener(e -> {
            String idText = memberIDField.getText().trim();
            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter Member ID!");
                return;
            }
            dao.checkIn(Integer.parseInt(idText));
            loadCheckIns();
            JOptionPane.showMessageDialog(null, "Checked In Successfully!");
            memberIDField.setText("");
        });
    }

    private void loadCheckIns() {
        model.setRowCount(0);
        List<String[]> logs = dao.getAllCheckIns();
        for (String[] l : logs) model.addRow(l);
    }

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        return btn;
    }
}