package popup;

import dao.ReaderDAO;
import util.SessionManager;

import javax.swing.*;

public class ReaderLoginPopup {
    public static void showLogin(JFrame parentFrame, Runnable onLoginSuccess, ReaderDAO readerDAO) {
        JTextField txtCardNumber = new JTextField(15);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter Card Number:"));
        panel.add(txtCardNumber);
        int result = JOptionPane.showConfirmDialog(parentFrame, panel, "Reader Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String cardNumber = txtCardNumber.getText().trim().toUpperCase();
            if (cardNumber.isEmpty()) {
                JOptionPane.showMessageDialog(parentFrame, "Card number cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (readerDAO.isValidReader(cardNumber)) {
                SessionManager.getInstance().setCurrentReaderCardNumber(cardNumber);
                onLoginSuccess.run();
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Invalid card number. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
