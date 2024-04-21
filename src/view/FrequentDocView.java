package view;

import com.toedter.calendar.JDateChooser;
import model.BranchFineInfo;
import model.DocumentDetail;
import model.Reader;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class FrequentDocView extends JFrame {
    private JTextField numberField;
    private JComboBox<String> branchNumberComboBox;
    private JButton frequentBorrowersButton;
    private JButton borrowedBooksButton;
    private JButton btnPopularBooks;
    private JButton btnBranchAvgFine;
    private JList<Reader> readerList;
    private JList<DocumentDetail> documentList;
    private JList<BranchFineInfo> branchFineInfoList;
    private JPanel cardPanel;
    private JComboBox<Integer> yearComboBox;
    private JDateChooser startDateChooser;
    private JDateChooser endDateChooser;

    public FrequentDocView() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Top Doc and Reader Viewer");
        setSize(800, 400);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(1, 2)); // Grid layout to divide into two columns
        add(topPanel, BorderLayout.NORTH);

        // Setup the text field panel on the left with BoxLayout for vertical alignment
        JPanel textFieldPanel = new JPanel();
        textFieldPanel.setLayout(new BoxLayout(textFieldPanel, BoxLayout.PAGE_AXIS));
        topPanel.add(textFieldPanel);

        JLabel numberLabel = new JLabel("Number:");
        numberField = new JTextField();
        textFieldPanel.add(numberLabel);
        textFieldPanel.add(numberField);

        JLabel branchNumberLabel = new JLabel("Branch Number:");
        branchNumberComboBox = new JComboBox<>();
        textFieldPanel.add(branchNumberLabel);
        textFieldPanel.add(branchNumberComboBox);

        JLabel yearLabel = new JLabel("Year:");
        yearComboBox = new JComboBox<>();
        populateYearComboBox(1990, 2050);
        textFieldPanel.add(yearLabel);
        textFieldPanel.add(yearComboBox);

        JPanel dateChooserPanel = new JPanel();
        dateChooserPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        startDateChooser = new JDateChooser();
        endDateChooser = new JDateChooser();
        dateChooserPanel.add(new JLabel("Start Date:"));
        dateChooserPanel.add(startDateChooser);
        dateChooserPanel.add(new JLabel("End Date:"));
        dateChooserPanel.add(endDateChooser);
        textFieldPanel.add(dateChooserPanel);

        // Setup the button panel on the right with BoxLayout for vertical alignment
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        topPanel.add(buttonPanel);

        frequentBorrowersButton = new JButton("Frequent Borrowers");
        borrowedBooksButton = new JButton("Borrowed Books");
        btnPopularBooks = new JButton("Popular Books");
        btnBranchAvgFine = new JButton("Branch Average Fine");

        buttonPanel.add(frequentBorrowersButton);
        buttonPanel.add(borrowedBooksButton);
        buttonPanel.add(btnPopularBooks);
        buttonPanel.add(btnBranchAvgFine);

        frequentBorrowersButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        borrowedBooksButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPopularBooks.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBranchAvgFine.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Panel for the list
        readerList = new JList<>();
        documentList = new JList<>();
        branchFineInfoList = new JList<>();
        JPanel readerListPanel = new JPanel(new BorderLayout());
        JScrollPane readerScrollPane = new JScrollPane(readerList);
        readerListPanel.add(readerScrollPane, BorderLayout.CENTER);

        JPanel documentListPanel = new JPanel(new BorderLayout());
        JScrollPane documentScrollPane = new JScrollPane(documentList);
        documentListPanel.add(documentScrollPane, BorderLayout.CENTER);

        JPanel branchFineInfoListPanel = new JPanel(new BorderLayout());
        JScrollPane branchFineInfoScrollPane = new JScrollPane(branchFineInfoList);
        branchFineInfoListPanel.add(branchFineInfoScrollPane, BorderLayout.CENTER);

        cardPanel = new JPanel(new CardLayout());
        setupCardLayout(readerListPanel, "Readers");
        setupCardLayout(documentListPanel, "Documents");
        setupCardLayout(branchFineInfoListPanel, "Branch Fine");
        add(cardPanel, BorderLayout.CENTER);
    }

    private void populateYearComboBox(int startYear, int endYear) {
        for (int i = startYear; i <= endYear; i++) {
            yearComboBox.addItem(i);
        }
    }

    private void setupCardLayout(JPanel jPanel, String panelName) {
        cardPanel.add(jPanel, panelName);
    }

    private void showPanel(String cardName) {
        CardLayout cl = (CardLayout)(cardPanel.getLayout());
        cl.show(cardPanel, cardName);  // Switches to the given card by name
    }

    public JButton getBorrowedBooksButton() {
        return borrowedBooksButton;
    }

    public JButton getFrequentBorrowersButton() {
        return frequentBorrowersButton;
    }

    public JButton getPopularBooksButton() {
        return btnPopularBooks;
    }

    public JButton getBranchAvgFineButton() {
        return btnBranchAvgFine;
    }

    public String getNumberField() {
        return numberField.getText();
    }

    public void displayReaderList(List<Reader> readerList) {
        DefaultListModel<Reader> model = new DefaultListModel<>();
        for (Reader reader : readerList) {
            model.addElement(reader);
        }
        this.readerList.setModel(model);
        showPanel("Readers");
    }

    public void displayDocumentList(List<DocumentDetail> docList) {
        DefaultListModel<DocumentDetail> model = new DefaultListModel<>();
        for (DocumentDetail doc : docList) {
            model.addElement(doc);
        }
        this.documentList.setModel(model);
        showPanel("Documents");
    }

    public void displayBranchFineInfoList(List<BranchFineInfo> branchFineInfoList) {
        DefaultListModel<BranchFineInfo> model = new DefaultListModel<>();
        for (BranchFineInfo bf : branchFineInfoList) {
            model.addElement(bf);
        }
        this.branchFineInfoList.setModel(model);
        showPanel("Branch Fine");
    }

    public String getSelectedBranchNumber() {
        return Objects.requireNonNull(branchNumberComboBox.getSelectedItem()).toString();
    }

    public void populateBranchNumbers(List<String> branchNumbers) {
        for (String number : branchNumbers) {
            branchNumberComboBox.addItem(number);
        }
    }

    public int getYear() {
        try {
            return (int) (Integer) yearComboBox.getSelectedItem();
        } catch (Exception e) {
            return 0;
        }
    }

    public Date getStartDate() {
        return startDateChooser.getDate();
    }

    public Date getEndDate() {
        return endDateChooser.getDate();
    }
}
