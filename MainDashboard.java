import javax.swing.*;
import java.awt.*;

public class MainDashboard extends JFrame {

    public MainDashboard() {
        setTitle("Gym Management System");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("🏋️ GYM MANAGEMENT SYSTEM", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 28));
        header.setOpaque(true);
        header.setBackground(new Color(34, 34, 34));
        header.setForeground(Color.YELLOW);
        header.setPreferredSize(new Dimension(800, 70));
        add(header, BorderLayout.NORTH);

        // Buttons Panel
        JPanel panel = new JPanel(new GridLayout(2, 2, 20, 20));
        panel.setBackground(new Color(45, 45, 45));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        JButton btnMembers = createButton("👥 Members", new Color(52, 152, 219));
        JButton btnTiers = createButton("🏆 Membership Tiers", new Color(46, 204, 113));
        JButton btnCheckIn = createButton("✅ Check In", new Color(231, 76, 60));
        JButton btnArchive = createButton("📦 Run Archive", new Color(155, 89, 182));

        panel.add(btnMembers);
        panel.add(btnTiers);
        panel.add(btnCheckIn);
        panel.add(btnArchive);

        add(panel, BorderLayout.CENTER);

        // Footer
        JLabel footer = new JLabel("University of Gujrat — SE-204 Database Systems", SwingConstants.CENTER);
        footer.setOpaque(true);
        footer.setBackground(new Color(34, 34, 34));
        footer.setForeground(Color.GRAY);
        footer.setPreferredSize(new Dimension(800, 30));
        add(footer, BorderLayout.SOUTH);

        // Button Actions
        btnMembers.addActionListener(e -> {
            new MembersScreen().setVisible(true);
        });

        btnTiers.addActionListener(e -> {
            new TiersScreen().setVisible(true);
        });

        btnCheckIn.addActionListener(e -> {
            new CheckInScreen().setVisible(true);
        });

        btnArchive.addActionListener(e -> {
            CheckInDAO dao = new CheckInDAO();
            dao.runArchive();
            JOptionPane.showMessageDialog(null, "Archive Completed Successfully!");
        });

        setVisible(true);
    }

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static void main(String[] args) {
        new MainDashboard();
    }
}