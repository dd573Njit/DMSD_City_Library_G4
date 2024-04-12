package view;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import model.Document;
import java.util.List;

public class ReaderView extends JFrame {
    private JTable documentsTable;

    public ReaderView() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Reader Functions - View Documents");
        setSize(500, 400);
        setLayout(new BorderLayout());

        documentsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(documentsTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void displayDocuments(List<Document> documents) {
        String[] columnNames = {"ID", "Title", "Publisher"};
        Object[][] data = new Object[documents.size()][columnNames.length];

        for (int i = 0; i < documents.size(); i++) {
            Document doc = documents.get(i);
            data[i][0] = doc.getId();
            data[i][1] = doc.getTitle();
            data[i][2] = doc.getPublisher();
        }

        documentsTable.setModel(new DefaultTableModel(data, columnNames));
    }
}
