package controller;

import dao.DocumentDAO;
import model.DocumentDetail;
import util.MessageUtil;
import util.SessionManager;
import view.ReaderView;

import java.util.List;

public class ReaderController {
    private final ReaderView readerView;
    private final ListDocumentController listDocumentController;
    private final ReturnDocumentsController returnDocumentsController;
    private final DocumentDAO documentDAO;
    private final List<DocumentDetail> documents;

    public ReaderController() {
        readerView = new ReaderView();
        documentDAO = new DocumentDAO();
        listDocumentController = new ListDocumentController();
        returnDocumentsController = new ReturnDocumentsController();
        documents = documentDAO.getReturnableDocuments();
        attachHandlers();
    }

    public void showReaderView() {
        readerView.setVisible(true);
    }

    private void attachHandlers() {
        readerView.getBtnReserveDocument().addActionListener(e -> showReserveDocument());
        readerView.getBtnCheckoutDocument().addActionListener(e -> showCheckoutDocument());
        readerView.getBtnReturnDocument().addActionListener(e -> showReturnableDocuments());
        readerView.getBtnListDocument().addActionListener(e -> openListDocument());
        readerView.getBtnLogout().addActionListener(e -> logoutHandler());
        readerView.getSearchText().addActionListener(e -> performSearch(readerView.getSearchText().getText()));
        readerView.getBtnSearch().addActionListener(e -> performSearch(readerView.getSearchText().getText()));
    }

    private void performSearch(String query) {
        List<DocumentDetail> results = documentDAO.searchDocuments(query); // Method now returns List<DocumentData>
        readerView.displayDocuments(results);
    }

    private void showReturnableDocuments() {
        returnDocumentsController.showReturnDocuments(documents);
    }

    private void openListDocument() {
        listDocumentController.showListDocument(documents);
    }

    private void logoutHandler() {
        SessionManager.getInstance().setCurrentReaderCardNumber(null);
        readerView.dispose();
    }

    private void showReserveDocument() {
        List<DocumentDetail> documents = readerView.getSelectedDocuments();
        if(documents.isEmpty()) {
            MessageUtil.showErrorMessage("No Documents selected",readerView);
            return;
        }
        new ReserveController().showReserveView(documents);
    }

    private void showCheckoutDocument() {
        List<DocumentDetail> documents = readerView.getSelectedDocuments();
        if(documents.isEmpty()) {
            MessageUtil.showErrorMessage("No Documents selected",readerView);
            return;
        }
        new CheckoutController().showCheckoutView(documents);
    }
}
