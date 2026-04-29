import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class MembersScreen extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private MemberDAO dao = new MemberDAO();

    public MembersScreen() {
        setTitle("Members Management");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(45, 45, 45));

        // Header
        JLabel header = new JLabel("👥 MEMBERS MANAGEMENT", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 22));
        header.setOpaque(true);
        header.setBackground(new Color(34, 34, 34));
        header.setForeground(Color.YELLOW);
        header.setPreferredSize(new Dimension(900, 55));
        add(header, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "First Name", "Last Name", "Email", "Phone", "Join Date", "Tier"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(52, 152, 219));
        table.getTableHeader().setForeground(Color.WHITE);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottom = new JPanel(new FlowLayout());
        bottom.setBackground(new Color(34, 34, 34));

        JTextField searchField = new JTextField(15);
        JButton btnSearch = createButton("🔍 Search", new Color(52, 152, 219));
        JButton btnAdd = createButton("➕ Add Member", new Color(46, 204, 113));
        JButton btnDelete = createButton("🗑️ Delete", new Color(231, 76, 60));
        JButton btnRefresh = createButton("🔄 Refresh", new Color(155, 89, 182));

        bottom.add(new JLabel("Search:") {{setForeground(Color.WHITE);}});
        bottom.add(searchField);
        bottom.add(btnSearch);
        bottom.add(btnAdd);
        bottom.add(btnDelete);
        bottom.add(btnRefresh);
        add(bottom, BorderLayout.SOUTH);

        // Load data
        loadMembers();

        // Actions
        btnRefresh.addActionListener(e -> loadMembers());

        btnSearch.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            if (!keyword.isEmpty()) {
                loadSearchResults(keyword);
            } else {
                loadMembers();
            }
        });

        btnAdd.addActionListener(e -> showAddMemberDialog());

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select a member first!");
                return;
            }
            int id = Integer.parseInt(model.getValueAt(row, 0).toString());
            int confirm = JOptionPane.showConfirmDialog(null, "Delete this member?");
            if (confirm == 0) {
                dao.deleteMember(id);
                loadMembers();
            }
        });
    }

    private void loadMembers() {
        model.setRowCount(0);
        List<String[]> members = dao.getAllMembers();
        for (String[] m : members) model.addRow(m);
    }

    private void loadSearchResults(String keyword) {
        model.setRowCount(0);
        List<String[]> members = dao.searchMember(keyword);
        for (String[] m : members) model.addRow(m);
    }

    private void showAddMemberDialog() {
        JTextField fname = new JTextField();
        JTextField lname = new JTextField();
        JTextField email = new JTextField();
        JTextField phone = new JTextField();
        JTextField tierID = new JTextField();

        Object[] fields = {
            "First Name:", fname,
            "Last Name:", lname,
            "Email:", email,
            "Phone:", phone,
            "Tier ID (1=Gold, 2=Silver, 3=Normal):", tierID
        };

        int result = JOptionPane.showConfirmDialog(null, fields, "Add New Member", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            dao.addMember(fname.getText(), lname.getText(),
                    email.getText(), phone.getText(),
                    Integer.parseInt(tierID.getText()));
            loadMembers();
            JOptionPane.showMessageDialog(null, "Member Added Successfully!");
        }
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