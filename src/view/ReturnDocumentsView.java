package view;

import model.DocumentDetail;
import model.ReturnableDocument;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ReturnDocumentsView extends JFrame{
    private JList<ReturnableDocument> documentList;
    private JButton btnQuit;
    private JButton btnReturnDocuments;
    private JLabel statusLabel;

    public ReturnDocumentsView() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Return Documents");
        setSize(800, 400);
        setLayout(new BorderLayout());

        // Panel for the list
        JPanel listPanel = new JPanel(new BorderLayout());
        documentList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(documentList);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel for the buttons and label
        JPanel southPanel = new JPanel(new BorderLayout());

        // Panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnReturnDocuments = new JButton("Return Documents");
        btnQuit = new JButton("Quit");
        buttonPanel.add(btnReturnDocuments);
        buttonPanel.add(btnQuit);

        // Status label at the bottom left
        statusLabel = new JLabel("Ready to process return...");
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelPanel.add(statusLabel);

        southPanel.add(labelPanel, BorderLayout.WEST);
        southPanel.add(buttonPanel, BorderLayout.EAST);

        // Adding panels to the frame
        add(listPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }

    public void displayDocuments(List<ReturnableDocument> documents) {
        DefaultListModel<ReturnableDocument> model = new DefaultListModel<>();
        for (ReturnableDocument doc : documents) {
            model.addElement(doc);
        }
        documentList.setModel(model);
    }

    public JButton getBtnReturnDocuments() {
        return btnReturnDocuments;
    }

    public JButton getBtnQuit() {
        return btnQuit;
    }

    public void setStatusMessage(String message) {
        statusLabel.setText(message);
    }
}