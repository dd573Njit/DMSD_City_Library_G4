package view;
import model.Document;
import model.DocumentDetail;
import util.ToggleSelectionModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ReaderView extends JFrame {
    private JTextField searchText;
    private JButton btnReserveDocument;
    private JButton btnReturnDocument;
    private JButton btnCheckoutDocument;
    private JButton btnListDocument;
    private JButton btnLogout;
    private JButton btnSearch; // Search button
    private JButton btnAddDocument;
    private JList<DocumentDetail> documentList;  // To display document details or search results
    private JLabel searchLabel;

    public ReaderView() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Reader Functions");
        setSize(800, 400);
        setLayout(new BorderLayout());

        // Top panel with action buttons
        JPanel topPanel = new JPanel(new FlowLayout());
        btnReserveDocument = new JButton("Reserve Document");
        btnReturnDocument = new JButton("Return Document");
        btnCheckoutDocument = new JButton("Checkout Document");
        btnListDocument = new JButton("List Document");
        btnLogout = new JButton("Logout");
        btnAddDocument = new JButton("Add Document");

        topPanel.add(btnReserveDocument);
        topPanel.add(btnReturnDocument);
        topPanel.add(btnCheckoutDocument);
        topPanel.add(btnListDocument);
        topPanel.add(btnLogout);

        // Search panel setup
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.LINE_AXIS));
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        searchLabel = new JLabel("Search Document:");
        searchText = new JTextField();
        searchText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        searchText.setForeground(Color.GRAY);
        btnSearch = new JButton("Search");

        searchPanel.add(searchLabel);
        searchPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        searchPanel.add(searchText);
        searchPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        searchPanel.add(btnSearch);
        searchPanel.add(btnAddDocument);

        // Container panel that includes both the top panel and search panel
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.PAGE_AXIS));
        northPanel.add(topPanel);
        northPanel.add(searchPanel);

        add(northPanel, BorderLayout.NORTH);

        // Dynamic panel for displaying document details
        JPanel dynamicPanel = new JPanel(new BorderLayout());
        documentList = new JList<>();
        documentList.setSelectionModel(new ToggleSelectionModel());
        documentList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        dynamicPanel.add(new JScrollPane(documentList), BorderLayout.CENTER);
        add(dynamicPanel, BorderLayout.CENTER);
    }

    public void setComponentVisibility(boolean visible) {
        searchText.setVisible(visible);
        searchLabel.setVisible(visible);
        btnSearch.setVisible(visible);
        if (!visible) {
            searchText.setText("");
        }
    }

    public JButton getBtnReserveDocument() {
        return btnReserveDocument;
    }

    public JButton getBtnReturnDocument() {
        return btnReturnDocument;
    }

    public JButton getBtnCheckoutDocument() {
        return btnCheckoutDocument;
    }

    public JButton getBtnListDocument() {
        return btnListDocument;
    }

    public JButton getBtnLogout() {
        return btnLogout;
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public JButton getBtnAddDocument() {
        return btnAddDocument;
    }

    public JTextField getSearchText() {
        return searchText;
    }

    public void displayDocuments(List<DocumentDetail> documents) {
        DefaultListModel<DocumentDetail> model = new DefaultListModel<>();
        for (DocumentDetail doc : documents) {
            model.addElement(doc);
        }
        documentList.setModel(model);
    }

    public List<DocumentDetail> getSelectedDocuments() {
        return documentList.getSelectedValuesList();
    }
}
