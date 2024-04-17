package util;
import javax.swing.JOptionPane;
import java.awt.*;

public class MessageUtil {

    // Displays a success message with a standard title and icon
    public static void showSuccessMessage(String message, Component parent) {
        JOptionPane.showMessageDialog(parent, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    // Displays an error message with a standard title and icon
    public static void showErrorMessage(String message, Component parent) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}

