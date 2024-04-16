package controller;

import dao.DocumentDAO;
import model.Document;
import util.SessionManager;
import view.ReaderView;

import java.util.List;

public class ReaderController {
    private final ReaderView readerView;
    private final DocumentDAO documentDAO;

    public ReaderController() {
        readerView = new ReaderView();
        documentDAO = new DocumentDAO();
        attachHandlers();
    }

    public void showReaderView() {
        readerView.setVisible(true);
    }

    private void attachHandlers() {
        readerView.getBtnReserveDocument().addActionListener(e -> activateSearch("Enter document ID or title to reserve:"));
        readerView.getBtnCheckoutDocument().addActionListener(e -> activateSearch("Enter document ID or title to checkout:"));
        readerView.getBtnReturnDocument().addActionListener(e -> showReturnableDocuments());
        readerView.getBtnListDocument().addActionListener(e -> showReturnableDocuments());
        readerView.getBtnLogout().addActionListener(e -> logoutHandler());
        readerView.getSearchText().addActionListener(e -> performSearch(readerView.getSearchText().getText()));
        readerView.getBtnSearch().addActionListener(e -> performSearch(readerView.getSearchText().getText()));
        readerView.getBtnAddDocument().addActionListener(e -> readerView.getSelectedDocuments());
    }

    private void activateSearch(String label) {
        readerView.setComponentVisibility(true);
    }

    private void performSearch(String query) {
        List<Document> results = documentDAO.searchDocuments(query); // Method now returns List<DocumentData>
        readerView.displayDocuments(results);
    }

    private void showReturnableDocuments() { // Placeholder for actual reader ID logic
        List<Document> documents = documentDAO.getReturnableDocuments();
        readerView.setComponentVisibility(false); // Hide search components
        readerView.displayDocuments(documents);
    }

    private void logoutHandler() {
        SessionManager.getInstance().setCurrentReaderCardNumber(null);
        readerView.dispose();
    }
}
