package view;

import model.DocumentDetail;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ReserveView extends JFrame {
    private JList<DocumentDetail> documentList;
    private JButton btnReserve;
    private JButton btnQuit;

    public ReserveView() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Reserve Documents");
        setSize(800, 400);
        setLayout(new BorderLayout());

        // Panel for the list
        JPanel listPanel = new JPanel(new BorderLayout());
        documentList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(documentList);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel for the button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Aligns the button to the right
        btnReserve = new JButton("Reserve");
        btnQuit = new JButton("Quit");
        buttonPanel.add(btnReserve);
        buttonPanel.add(btnQuit);

        // Adding panels to the frame
        add(listPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH); // Places the button at the bottom
    }

    public void displayDocuments(List<DocumentDetail> documents) {
        DefaultListModel<DocumentDetail> model = new DefaultListModel<>();
        for (DocumentDetail doc : documents) {
            model.addElement(doc);
        }
        documentList.setModel(model);
    }

    public JButton getBtnReserve() {
        return btnReserve;
    }

    public JButton getBtnQuit() {
        return btnQuit;
    }
}
