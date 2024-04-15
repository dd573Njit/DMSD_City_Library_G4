package controller;

import dao.DocumentDAO;
import util.SessionManager;
import view.ReaderView;

public class ReaderController {
    private ReaderView readerView;
    private DocumentDAO documentDAO;

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
    }

    private void activateSearch(String label) {
        readerView.setComponentVisibility(true);
    }

    private void performSearch(String query) {
        String[] results = documentDAO.searchDocuments(query).toArray(new String[0]);
        readerView.displayDocuments(results);
    }

    private void showReturnableDocuments() { // Placeholder for actual reader ID logic
        String[] documents = documentDAO.getReturnableDocuments().toArray(new String[0]);
        readerView.setComponentVisibility(false); // Hide search components
        readerView.displayDocuments(documents);
    }

    private void logoutHandler() {
        SessionManager.getInstance().setCurrentReaderCardNumber(null);
        readerView.dispose();
    }
}
