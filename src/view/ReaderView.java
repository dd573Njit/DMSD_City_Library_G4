package view;
import model.DocumentDetail;
import model.Publisher;
import util.ToggleSelectionModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class ReaderView extends JFrame {
    private JTextField searchText;
    private JButton btnReserveDocument;
    private JButton btnReturnDocument;
    private JButton btnCheckoutDocument;
    private JButton btnListReservedDocuments;
    private JButton btnLogout;
    private JButton btnSearch;
    private JButton btnPrintDocuments;
    private JList<DocumentDetail> documentList;
    private JComboBox<String> cbPublisherNames;

    public ReaderView() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Reader Functions");
        setSize(900, 400);
        setLayout(new BorderLayout());

        // Top panel with action buttons
        JPanel topPanel = new JPanel(new FlowLayout());
        btnReserveDocument = new JButton("Reserve Document");
        btnReturnDocument = new JButton("Return Document");
        btnCheckoutDocument = new JButton("Checkout Document");
        btnListReservedDocuments = new JButton("List Reserved Documents");
        btnLogout = new JButton("Logout");

        topPanel.add(btnReserveDocument);
        topPanel.add(btnReturnDocument);
        topPanel.add(btnCheckoutDocument);
        topPanel.add(btnListReservedDocuments);
        topPanel.add(btnLogout);

        // Search panel setup
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.LINE_AXIS));
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel searchLabel = new JLabel("Search Document:");
        searchText = new JTextField();
        searchText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        searchText.setForeground(Color.GRAY);
        btnSearch = new JButton("Search");

        searchPanel.add(searchLabel);
        searchPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        searchPanel.add(searchText);
        searchPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        searchPanel.add(btnSearch);

        // Publisher panel setup
        JPanel publisherPanel = new JPanel();
        publisherPanel.setLayout(new BoxLayout(publisherPanel, BoxLayout.LINE_AXIS));
        publisherPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        cbPublisherNames = new JComboBox<>();
        btnPrintDocuments = new JButton("Print Documents");

        publisherPanel.add(cbPublisherNames);
        publisherPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        publisherPanel.add(btnPrintDocuments);

        // Container panel that includes both the top panel and search panels
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.PAGE_AXIS));
        northPanel.add(topPanel);
        northPanel.add(searchPanel);
        northPanel.add(publisherPanel); // Add the publisher panel below the search panel

        add(northPanel, BorderLayout.NORTH);

        // Dynamic panel for displaying document details
        JPanel dynamicPanel = new JPanel(new BorderLayout());
        documentList = new JList<>();
        documentList.setSelectionModel(new ToggleSelectionModel());
        documentList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        dynamicPanel.add(new JScrollPane(documentList), BorderLayout.CENTER);
        add(dynamicPanel, BorderLayout.CENTER);
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

    public JButton getBtnListReservedDocuments() {
        return btnListReservedDocuments;
    }

    public JButton getBtnLogout() {
        return btnLogout;
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public JButton getBtnPrintDocuments() {
        return btnPrintDocuments;
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

    public String getPublisherName() {
        return Objects.requireNonNull(cbPublisherNames.getSelectedItem()).toString();
    }

    public void populatePublisher(List<Publisher> publishers) {
        for (Publisher publisher : publishers) {
            cbPublisherNames.addItem(publisher.getPubName());
        }
    }

    public void clearDocumentList() {
        DefaultListModel<DocumentDetail> model = (DefaultListModel<DocumentDetail>) documentList.getModel();
        model.removeAllElements();
    }
}
