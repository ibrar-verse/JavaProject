import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.border.*;

public class LoginScreen extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel animatedLabel;
    private Timer animationTimer;
    private int dotCount = 0;

    // Gold and Black theme colors
    private static final Color GOLD = new Color(255, 215, 0);
    private static final Color DARK_GOLD = new Color(184, 134, 11);
    private static final Color BLACK = new Color(10, 10, 10);
    private static final Color DARK_GRAY = new Color(25, 25, 25);
    private static final Color CARD_BG = new Color(30, 30, 30);

    public LoginScreen() {
        setTitle("Ibi_verse Gym — Admin Login");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BLACK);

        // ===== TOP PANEL (Logo + Animated Text) =====
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(BLACK);
        topPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 10, 20));

        // Gym Logo
        JLabel logoLabel = new JLabel();
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        try {
            URL imgURL = getClass().getResource("/images/gym_logo.png");
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image scaled = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                logoLabel.setIcon(new ImageIcon(scaled));
            } else {
                logoLabel.setText("🏋️");
                logoLabel.setFont(new Font("Arial", Font.PLAIN, 60));
                logoLabel.setForeground(GOLD);
            }
        } catch (Exception e) {
            logoLabel.setText("🏋️");
            logoLabel.setFont(new Font("Arial", Font.PLAIN, 60));
            logoLabel.setForeground(GOLD);
        }
        topPanel.add(logoLabel);
        topPanel.add(Box.createVerticalStrut(10));

        // Gym Name
        JLabel gymName = new JLabel("IBi_VERSE GYM");
        gymName.setFont(new Font("Arial", Font.BOLD, 28));
        gymName.setForeground(GOLD);
        gymName.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(gymName);
        topPanel.add(Box.createVerticalStrut(5));

        // Animated Welcome Text
        animatedLabel = new JLabel("Welcome Admin");
        animatedLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        animatedLabel.setForeground(DARK_GOLD);
        animatedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(animatedLabel);

        // Start animation
        startAnimation();

        add(topPanel, BorderLayout.NORTH);

        // ===== CENTER PANEL (Login Form) =====
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(CARD_BG);
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(20, 40, 20, 40),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GOLD, 1),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
            )
        ));

        // Title
        JLabel loginTitle = new JLabel("ADMIN LOGIN");
        loginTitle.setFont(new Font("Arial", Font.BOLD, 20));
        loginTitle.setForeground(GOLD);
        loginTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(loginTitle);
        centerPanel.add(Box.createVerticalStrut(25));

        // Username
        JLabel userLabel = new JLabel("USERNAME");
        userLabel.setFont(new Font("Arial", Font.BOLD, 12));
        userLabel.setForeground(GOLD);
        userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(userLabel);
        centerPanel.add(Box.createVerticalStrut(5));

        usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBackground(new Color(45, 45, 45));
        usernameField.setForeground(Color.WHITE);
        usernameField.setCaretColor(GOLD);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(DARK_GOLD, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        centerPanel.add(usernameField);
        centerPanel.add(Box.createVerticalStrut(15));

        // Password
        JLabel passLabel = new JLabel("PASSWORD");
        passLabel.setFont(new Font("Arial", Font.BOLD, 12));
        passLabel.setForeground(GOLD);
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(passLabel);
        centerPanel.add(Box.createVerticalStrut(5));

        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBackground(new Color(45, 45, 45));
        passwordField.setForeground(Color.WHITE);
        passwordField.setCaretColor(GOLD);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(DARK_GOLD, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        centerPanel.add(passwordField);
        centerPanel.add(Box.createVerticalStrut(25));

        // Login Button
        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setFont(new Font("Arial", Font.BOLD, 16));
        loginBtn.setBackground(GOLD);
        loginBtn.setForeground(BLACK);
        loginBtn.setFocusPainted(false);
        loginBtn.setBorderPainted(false);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(loginBtn);

        add(centerPanel, BorderLayout.CENTER);

        // ===== FOOTER =====
        JLabel footer = new JLabel("© 2024 Ibi_verse Gym — All Rights Reserved", SwingConstants.CENTER);
        footer.setFont(new Font("Arial", Font.PLAIN, 11));
        footer.setOpaque(true);
        footer.setBackground(BLACK);
        footer.setForeground(DARK_GOLD);
        footer.setPreferredSize(new Dimension(500, 35));
        add(footer, BorderLayout.SOUTH);

        // Login Action
        loginBtn.addActionListener(e -> handleLogin());
        passwordField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) handleLogin();
            }
        });

        setVisible(true);
    }

    private void startAnimation() {
        animationTimer = new Timer(500, e -> {
            dotCount = (dotCount + 1) % 4;
            String dots = ".".repeat(dotCount);
            animatedLabel.setText("Welcome Admin" + dots);
        });
        animationTimer.start();
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.equals("ibiverse") && password.equals("Ibi@2024")) {
            animationTimer.stop();
            dispose();
            new MainDashboard();
        } else {
            JOptionPane.showMessageDialog(this,
                "Invalid Username or Password!",
                "Access Denied",
                JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }

    public static void main(String[] args) {
        new LoginScreen();
    }
}