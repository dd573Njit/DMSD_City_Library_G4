package controller;

import dao.DocumentDAO;
import model.DocumentDetail;
import util.SessionManager;
import view.CheckoutView;
import view.ReaderView;
import view.ReserveView;

import java.util.List;

public class ReaderController {
    private final ReaderView readerView;
    private final DocumentDAO documentDAO;
    private boolean isReserved = true;

    public ReaderController() {
        readerView = new ReaderView();
        documentDAO = new DocumentDAO();
        attachHandlers();
    }

    public void showReaderView() {
        readerView.setVisible(true);
    }

    private void attachHandlers() {
        readerView.getBtnReserveDocument().addActionListener(e -> {
            isReserved = true;
            activateSearch();
        });
        readerView.getBtnCheckoutDocument().addActionListener(e -> {
            isReserved = false;
            activateSearch();
        });
        readerView.getBtnReturnDocument().addActionListener(e -> showReturnableDocuments());
        readerView.getBtnListDocument().addActionListener(e -> showReturnableDocuments());
        readerView.getBtnLogout().addActionListener(e -> logoutHandler());
        readerView.getSearchText().addActionListener(e -> performSearch(readerView.getSearchText().getText()));
        readerView.getBtnSearch().addActionListener(e -> performSearch(readerView.getSearchText().getText()));
        readerView.getBtnAddDocument().addActionListener(e -> documentReserveOrCheckout());
    }

    private void activateSearch() {
        readerView.setComponentVisibility(true);
    }

    private void performSearch(String query) {
        List<DocumentDetail> results = documentDAO.searchDocuments(query); // Method now returns List<DocumentData>
        readerView.displayDocuments(results);
    }

    private void showReturnableDocuments() { // Placeholder for actual reader ID logic
        List<DocumentDetail> documents = documentDAO.getReturnableDocuments();
        readerView.setComponentVisibility(false); // Hide search components
        readerView.displayDocuments(documents);
    }

    private void logoutHandler() {
        SessionManager.getInstance().setCurrentReaderCardNumber(null);
        readerView.dispose();
    }

    private void documentReserveOrCheckout() {
        List<DocumentDetail> documents = readerView.getSelectedDocuments();
        if(isReserved) {
            new ReserveController().showReserveView(documents);
        }
        else {
            new CheckoutController().showCheckoutView(documents);
        }

    }
}
