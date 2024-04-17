package view;

import model.DocumentDetail;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ReturnDocumentsView extends JFrame{
    private JList<DocumentDetail> documentList;
    private JButton btnQuit;

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

        // Panel for the button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnQuit = new JButton("Quit");
        buttonPanel.add(btnQuit);

        // Adding panels to the frame
        add(listPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void displayDocuments(List<DocumentDetail> documents) {
        DefaultListModel<DocumentDetail> model = new DefaultListModel<>();
        for (DocumentDetail doc : documents) {
            model.addElement(doc);
        }
        documentList.setModel(model);
    }

    public JButton getBtnQuit() {
        return btnQuit;
    }
}

