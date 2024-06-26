package view;

import model.DocumentDetail;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ListDocumentView extends JFrame {
    private JList<DocumentDetail> documentList;
    private JButton btnQuit;

    public ListDocumentView() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Document List View");
        setSize(800, 400);
        setLayout(new BorderLayout());

        // Label just above the document list
        // Label for the document list
        JLabel instructionLabel = new JLabel("Reservation will be cancelled after 6PM");
        instructionLabel.setHorizontalAlignment(JLabel.CENTER);  // Center the label text

        // Panel for the list
        JPanel listPanel = new JPanel(new BorderLayout());
        documentList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(documentList);
        listPanel.add(instructionLabel, BorderLayout.NORTH); // Add label to the top
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