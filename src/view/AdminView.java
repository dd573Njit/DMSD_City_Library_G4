package view;

import java.awt.*;
import javax.swing.*;

public class AdminView extends JFrame {
    private JButton btnAddReader;
    private JButton btnAddDoc;
    private JButton btnSearchDoc;
    private JButton btnBranchInfo;
    private JButton btnLogout;

    public AdminView() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Admin Functions");
        setSize(800, 400);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        btnAddReader = new JButton("Add Reader");
        btnAddDoc = new JButton("Add Document");
        btnSearchDoc = new JButton("Search Document");
        btnBranchInfo = new JButton("Branch Info");
        btnLogout = new JButton("Logout");
        topPanel.add(btnAddReader);
        topPanel.add(btnAddDoc);
        topPanel.add(btnSearchDoc);
        topPanel.add(btnBranchInfo);
        topPanel.add(btnLogout);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.PAGE_AXIS));
        northPanel.add(topPanel);
        add(northPanel, BorderLayout.NORTH);
    }

    public JButton getBtnAddReader() {
        return btnAddReader;
    }

    public JButton getBtnAddDoc() {
        return btnAddDoc;
    }

    public JButton getBtnSearchDoc() {
        return btnSearchDoc;
    }

    public JButton getBtnBranchInfo() {
        return btnBranchInfo;
    }

    public JButton getBtnLogout() {
        return btnLogout;
    }
}
