package view;

import model.DocumentStatus;
import util.ToggleSelectionModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DocumentStatusView extends JFrame {
    private JTextField searchText;
    private JButton btnSearch;
    private JButton btnQuit;
    private JList<DocumentStatus> documentList;

    public DocumentStatusView() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Document Status");
        setSize(800, 400);
        setLayout(new BorderLayout());

        // Create a search panel with FlowLayout for alignment
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add some padding

        // Initialize search components
        JLabel searchLabel = new JLabel("Search:");
        searchText = new JTextField(20);  // Create a text field with a width of 20 columns
        btnSearch = new JButton("Search");  // Create a search button
        btnQuit = new JButton("Quit");

        // Add components to the search panel
        searchPanel.add(searchLabel);
        searchPanel.add(searchText);
        searchPanel.add(btnSearch);
        searchPanel.add(btnQuit);

        // Add the search panel to the top of the frame
        add(searchPanel, BorderLayout.NORTH);

        // Dynamic panel for displaying document details
        JPanel dynamicPanel = new JPanel(new BorderLayout());
        documentList = new JList<>();
        documentList.setSelectionModel(new ToggleSelectionModel());
        dynamicPanel.add(new JScrollPane(documentList), BorderLayout.CENTER);
        add(dynamicPanel, BorderLayout.CENTER);
    }

    public JTextField getSearchText() {
        return searchText;
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public JButton getBtnQuit() {
        return btnQuit;
    }

    public void displayDocuments(List<DocumentStatus> documents) {
        DefaultListModel<DocumentStatus> model = new DefaultListModel<>();
        for (DocumentStatus doc : documents) {
            model.addElement(doc);
        }
        documentList.setModel(model);
    }
}

