package view;

import javax.swing.*;

public class MainView extends JFrame {
    private JButton btnReaderFunctions;
    private JButton btnAdminFunctions;
    private JButton btnQuit;

    public MainView() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Library System Main Menu");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        btnReaderFunctions = new JButton("Reader Functions");
        btnAdminFunctions = new JButton("Administrative Functions");
        btnQuit = new JButton("Quit");

        add(btnReaderFunctions);
        add(btnAdminFunctions);
        add(btnQuit);
    }

    public JButton getBtnReaderFunctions() {
        return btnReaderFunctions;
    }

    public JButton getBtnAdminFunctions() {
        return btnAdminFunctions;
    }

    public JButton getBtnQuit() {
        return btnQuit;
    }
}
