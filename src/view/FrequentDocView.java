package view;

import java.awt.*;
import javax.swing.*;
import java.text.SimpleDateFormat;

public class FrequentDocView extends JFrame {
    // Top Panel
    private JButton btnSearchDoc;
    private JButton btnBranchInfo;
    private JButton btnLogout;

    // Document Panel
    private JButton btnFreqBorrowers;
    private JButton btnFreqBorrowedBooks;
    private JButton btnAvgFinePaid;
    private JTextField txtNumber;
    private JTextField txtBranchId;
    private JTextField txtBookPubYear;
    private JTextField txtStartDate;
    private JTextField txtEndDate;
    private SimpleDateFormat dateFormat;
    private JPanel documentPanel;

    //Branch info panel
    private JList<String> branchList;
    private JPanel dynamicBranchPanel;

    JPanel cardPanel;


    public FrequentDocView() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Frequent Docs View");
        setSize(800, 400);
        setLayout(new BorderLayout());

        addTopPanel();
        setupDocumentPanel();
        setUpBranchPanel();
    }

    private void addTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout());
        btnBranchInfo = new JButton("Branch Info");
        btnLogout = new JButton("Logout");
        topPanel.add(btnBranchInfo);
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

        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        btnFreqBorrowers = new JButton("Frequent Borrowers");
        btnFreqBorrowedBooks = new JButton("Frequently Borrowed Books");
        btnAvgFinePaid = new JButton("AVG Fine Paid");
        documentPanel.add(btnFreqBorrowers);
        documentPanel.add(btnFreqBorrowedBooks);
        documentPanel.add(btnAvgFinePaid);
        txtNumber = new JTextField(5);
        txtBranchId = new JTextField(10);
        txtBookPubYear = new JTextField(4);
        txtStartDate = new JTextField(10);
        txtEndDate = new JTextField(10);

        addLabelAndField(documentPanel, "Number:", txtNumber, 0, gbc);
        addLabelAndField(documentPanel, "BranchID:", txtBranchId, 1, gbc);
        addLabelAndField(documentPanel, "BookPubYear: :", txtBookPubYear, 2, gbc);
        addLabelAndField(documentPanel, "StartDate:", txtStartDate, 3, gbc);
        addLabelAndField(documentPanel, "EndDate:", txtEndDate, 4, gbc);
        btnSearchDoc = new JButton("Search");
        documentPanel.add(btnSearchDoc);

        add(documentPanel, BorderLayout.CENTER);
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

    private void setupPanelWithCardLayout() {
        cardPanel = new JPanel(new CardLayout());
        cardPanel.add(documentPanel, "Frequent Docs Panel");
        cardPanel.add(dynamicBranchPanel, "Branch Panel");

        // Add the card to panel to the Frame
        add(cardPanel, BorderLayout.CENTER);
    }

    public JButton getBtnBranchInfo() {
        return btnBranchInfo;
    }

    public JButton getBtnLogout() {
        return btnLogout;
    }

    public JButton getBtnFreqBorrowers() {
        return btnFreqBorrowers;
    }

    public JButton getBtnFreqBorrowedBooks() {
        return btnFreqBorrowedBooks;
    }

    public JButton getBtnAvgFinePaid() {
        return btnAvgFinePaid;
    }

    public String getTxtNumber() {
        return txtNumber.getText();
    }

    public String getTxtBranchId() {
        return txtBranchId.getText();
    }

    public String getTxtBookPubYear() {
        return txtBookPubYear.getText();
    }

    public String getStartDate() {
        return txtStartDate.getText();
    }

    public String getEndDate() {
        return txtEndDate.getText();
    }

    public JButton getBtnSearchDoc() {
        return btnSearchDoc;
    }

    // Method in controller or somewhere to switch panels
    public void showPanel(String cardName) {
        CardLayout cl = (CardLayout)(cardPanel.getLayout());
        cl.show(cardPanel, cardName);  // Switches to the given card by name
    }

    public void displayBranchInfo(String[] branches) {
        branchList.setListData(branches);
    }
}
