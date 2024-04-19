package view;

import java.awt.*;
import javax.swing.*;

public class AdminView extends JFrame {
    //Top panel
    private JButton btnAddReader;
    private JButton btnAddDoc;
    private JButton btnSearchDoc;
    private JButton btnBranchInfo;
    private JButton btnFreqQuery;
    private JButton btnLogout;

    //Document panel
    private JButton btnAddCurrentDoc;
    private JTextField txtPubId;
    private JTextField txtPubName;
    private JTextField txtPubAddress;
    private JTextField txtDocId;
    private JTextField txtDocTitle;
    private JTextField txtCopyNumber;
    private JTextField txtBranchId;
    private JTextField txtCopyPosition;
    private JPanel documentPanel;

    //Reader panel
    private JTextField txtRId;
    private JTextField txtRName;
    private JTextField txtRAddress;
    private JTextField txtRPhone;
    private JTextField txtRType;
    private JButton btnAddCurrentReader;
    private JPanel readerPanel;

    //Branch info panel
    private JList<String> branchList;
    private JPanel dynamicBranchPanel;

    JPanel cardPanel;

    public AdminView() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Admin Functions");
        setSize(800, 400);
        setLayout(new BorderLayout());

        addTopPanel();
        setupDocumentPanel();
        setupReaderPanel();
        setUpBranchPanel();
        setupPanelsWithCardLayout();
    }

    private void addTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout());
        btnAddReader = new JButton("Add Reader");
        btnAddDoc = new JButton("Add Document");
        btnSearchDoc = new JButton("Search Document");
        btnBranchInfo = new JButton("Branch Info");
        btnFreqQuery = new JButton("Filter Queries");
        btnLogout = new JButton("Logout");
        topPanel.add(btnAddReader);
        topPanel.add(btnAddDoc);
        topPanel.add(btnSearchDoc);
        topPanel.add(btnBranchInfo);
        topPanel.add(btnFreqQuery);
        topPanel.add(btnLogout);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.PAGE_AXIS));
        northPanel.add(topPanel);
        add(northPanel, BorderLayout.NORTH);
    }

    private void setupDocumentPanel() {
        documentPanel = new JPanel();
        documentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(2, 2, 2, 2); // Smaller gaps between components
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        txtPubId = new JTextField(5);
        txtDocId = new JTextField(5);
        txtDocTitle = new JTextField(5);
        txtPubName = new JTextField(5);
        txtPubAddress = new JTextField(5);
        txtCopyNumber = new JTextField(5);
        txtBranchId = new JTextField(5);
        txtCopyPosition = new JTextField(5);

        addLabelAndField(documentPanel, "Publisher ID:", txtPubId, 0, gbc);
        addLabelAndField(documentPanel, "Publisher Name:", txtPubName, 1, gbc);
        addLabelAndField(documentPanel, "Address:", txtPubAddress, 2, gbc);
        addLabelAndField(documentPanel, "Doc ID:", txtDocId, 3, gbc);
        addLabelAndField(documentPanel, "Doc Title:", txtDocTitle, 4, gbc);
        addLabelAndField(documentPanel, "Doc Copy Number:", txtCopyNumber, 5, gbc);
        addLabelAndField(documentPanel, "Branch ID:", txtBranchId, 6, gbc);
        addLabelAndField(documentPanel, "Copy Position:", txtCopyPosition, 7, gbc);
        btnAddCurrentDoc = new JButton("Add Current Document");
        documentPanel.add(btnAddCurrentDoc);

        add(documentPanel, BorderLayout.CENTER);
    }

    private void setupReaderPanel() {
        readerPanel = new JPanel();
        readerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(2, 2, 2, 2); // Smaller gaps between components
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        txtRId = new JTextField(5);
        txtRAddress = new JTextField(5);
        txtRName = new JTextField(5);
        txtRPhone = new JTextField(5);
        txtRType = new JTextField(5);

        addLabelAndField(readerPanel, "Reader ID:", txtRId, 0, gbc);
        addLabelAndField(readerPanel, "Reader Name:", txtRName, 1, gbc);
        addLabelAndField(readerPanel, "Reader Address:", txtRAddress, 2, gbc);
        addLabelAndField(readerPanel, "Reader Phone:", txtRPhone, 3, gbc);
        addLabelAndField(readerPanel, "Reader Type:", txtRType, 4, gbc);
        btnAddCurrentReader = new JButton("Add Current Reader");
        readerPanel.add(btnAddCurrentReader);

        add(readerPanel, BorderLayout.CENTER);
    }

    private void setUpBranchPanel() {
        branchList = new JList<>();
        dynamicBranchPanel = new JPanel(new BorderLayout());
        dynamicBranchPanel.add(new JScrollPane(branchList), BorderLayout.CENTER);
        add(dynamicBranchPanel, BorderLayout.CENTER);
    }

    private void addLabelAndField(JPanel panel, String labelText, JTextField textField, int gridy, GridBagConstraints gbc) {
        JLabel label = new JLabel(labelText);
        gbc.gridx = 0;
        gbc.gridy = gridy;
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(textField, gbc);
    }

    private void setupPanelsWithCardLayout() {
        cardPanel = new JPanel(new CardLayout());  // This holds the panels like cards
        cardPanel.add(readerPanel, "Reader Panel");
        cardPanel.add(documentPanel, "Document Panel");
        cardPanel.add(dynamicBranchPanel, "Branch Panel");

        // Add the card panel to the frame
        add(cardPanel, BorderLayout.CENTER);
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

    public JButton getBtnFreqQuery() {
        return btnFreqQuery;
    }

    public JButton getBtnLogout() {
        return btnLogout;
    }

    public JButton getBtnAddCurrentDoc() {
        return btnAddCurrentDoc;
    }

    public String getTxtPubId() {
        return txtPubId.getText();
    }

    public String getTxtPubName() {
        return txtPubName.getText();
    }

    public String getTxtPubAddress() {
        return txtPubAddress.getText();
    }

    public String getTxtDocId() {
        return txtDocId.getText();
    }

    public String getTxtDocTitle() {
        return txtDocTitle.getText();
    }

    public String getTxtCopyNumber() {
        return txtCopyNumber.getText();
    }

    public String getTxtBranchId() {
        return txtBranchId.getText();
    }

    public String getCopyPosition() {
        return txtCopyPosition.getText();
    }

    public String getTxtRId() {
        return txtRId.getText();
    }

    public String getTxtRName() {
        return txtRName.getText();
    }

    public String getTxtRAddress() {
        return txtRAddress.getText();
    }

    public String getTxtRPhone() {
        return txtRPhone.getText();
    }

    public String getTxtRType() {
        return txtRType.getText();
    }

    public JButton getBtnAddCurrentReader() {
        return btnAddCurrentReader;
    }

    // Method in controller or somewhere to switch panels
    public void showPanel(String cardName) {
        CardLayout cl = (CardLayout)(cardPanel.getLayout());
        cl.show(cardPanel, cardName);  // Switches to the given card by name
    }

    public boolean areAllDocFieldsFilled() {
        if(txtDocId.getText().isEmpty() || txtDocTitle.getText().isEmpty() || txtPubAddress.getText().isEmpty() || txtPubId.getText().isEmpty() || txtPubName.getText().isEmpty() || txtCopyNumber.getText().isEmpty() || txtBranchId.getText().isEmpty() || txtCopyPosition.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Some fields are empty", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean areAllReaderFieldsFilled() {
        if(txtRType.getText().isEmpty() || txtRId.getText().isEmpty() || txtRPhone.getText().isEmpty() || txtRAddress.getText().isEmpty() || txtRAddress.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Some fields are empty", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public void dsiplayBranchInfo(String[] branches) {
        branchList.setListData(branches);
    }
}
