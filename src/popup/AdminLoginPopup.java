package popup;
import util.SessionManager;

import javax.swing.*;
import java.awt.*;

public class AdminLoginPopup {
    public static void showLogin(JFrame parentFrame, Runnable onLoginSuccess) {
        JTextField txtAdminId = new JTextField(15);
        JPasswordField txtPassword = new JPasswordField(15);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Admin ID:"));
        panel.add(txtAdminId);
        panel.add(new JLabel("Password:"));
        panel.add(txtPassword);
        int result = JOptionPane.showConfirmDialog(parentFrame, panel, "Admin Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String adminId = txtAdminId.getText();
            String password = new String(txtPassword.getPassword());

            if (adminId.trim().isEmpty() || password.trim().isEmpty()) {
                JOptionPane.showMessageDialog(parentFrame, "Admin ID and password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else if(adminId.equals("admin") && password.equals("secret")) {
                SessionManager.getInstance().setAdminId(adminId);
                SessionManager.getInstance().setAdminPassword(password);
                onLoginSuccess.run();
            }
            else {
                JOptionPane.showMessageDialog(parentFrame, "Admin ID and password does not match", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
