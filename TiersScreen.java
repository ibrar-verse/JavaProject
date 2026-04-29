import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class TiersScreen extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private MembershipTierDAO dao = new MembershipTierDAO();

    public TiersScreen() {
        setTitle("Membership Tiers");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("🏆 MEMBERSHIP TIERS", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 22));
        header.setOpaque(true);
        header.setBackground(new Color(34, 34, 34));
        header.setForeground(Color.YELLOW);
        header.setPreferredSize(new Dimension(700, 55));
        add(header, BorderLayout.NORTH);

        // Table
        String[] columns = {"Tier ID", "Tier Name", "Monthly Price", "Access Level"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(46, 204, 113));
        table.getTableHeader().setForeground(Color.WHITE);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Bottom
        JPanel bottom = new JPanel(new FlowLayout());
        bottom.setBackground(new Color(34, 34, 34));

        JButton btnAdd = createButton("➕ Add Tier", new Color(46, 204, 113));
        JButton btnDelete = createButton("🗑️ Delete Tier", new Color(231, 76, 60));
        JButton btnRefresh = createButton("🔄 Refresh", new Color(52, 152, 219));

        bottom.add(btnAdd);
        bottom.add(btnDelete);
        bottom.add(btnRefresh);
        add(bottom, BorderLayout.SOUTH);

        loadTiers();

        btnRefresh.addActionListener(e -> loadTiers());

        btnAdd.addActionListener(e -> {
            JTextField name = new JTextField();
            JTextField price = new JTextField();
            JTextField access = new JTextField();
            Object[] fields = {"Tier Name:", name, "Monthly Price:", price, "Access Level:", access};
            int result = JOptionPane.showConfirmDialog(null, fields, "Add Tier", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                dao.addTier(name.getText(), Double.parseDouble(price.getText()), Integer.parseInt(access.getText()));
                loadTiers();
                JOptionPane.showMessageDialog(null, "Tier Added!");
            }
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(null, "Select a tier first!"); return; }
            int id = Integer.parseInt(model.getValueAt(row, 0).toString());
            dao.deleteTier(id);
            loadTiers();
        });
    }

    private void loadTiers() {
        model.setRowCount(0);
        List<String[]> tiers = dao.getAllTiers();
        for (String[] t : tiers) model.addRow(t);
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