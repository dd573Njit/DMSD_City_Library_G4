package controller;

import dao.DocumentDAO;
import dao.PublisherDAO;
import model.DocumentDetail;
import util.MessageUtil;
import util.SessionManager;
import view.ReaderView;

import java.sql.SQLException;
import java.util.List;

public class ReaderController {
    private final ReaderView readerView;
    private final ReturnDocumentsController returnDocumentsController;
    private final DocumentDAO documentDAO;

    public ReaderController() {
        readerView = new ReaderView();
        documentDAO = new DocumentDAO();
        returnDocumentsController = new ReturnDocumentsController();
        attachHandlers();
    }

    public void showReaderView() {
        readerView.setVisible(true);
        populatePublishers();
    }

    private void attachHandlers() {
        readerView.getBtnReserveDocument().addActionListener(e -> showReserveDocument());
        readerView.getBtnCheckoutDocument().addActionListener(e -> showCheckoutDocument());
        readerView.getBtnReturnDocument().addActionListener(e -> showReturnableDocuments());
        readerView.getBtnListReservedDocuments().addActionListener(e -> openListDocument());
        readerView.getBtnLogout().addActionListener(e -> logoutHandler());
        readerView.getSearchText().addActionListener(e -> performSearch(readerView.getSearchText().getText()));
        readerView.getBtnSearch().addActionListener(e -> performSearch(readerView.getSearchText().getText()));
        readerView.getBtnPrintDocuments().addActionListener(e -> showDocumentForPublisher());
    }

    private void performSearch(String query) {
        List<DocumentDetail> results = documentDAO.searchDocuments(query); // Method now returns List<DocumentData>
        readerView.displayDocuments(results);
    }

    private void showReturnableDocuments() {
        returnDocumentsController.showReturnDocuments();
    }

    private void openListDocument() {
        new ListDocumentController().showListDocument();
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

    private void showDocumentForPublisher() {
        String pubName = readerView.getPublisherName();
        List<DocumentDetail> documents = null;
        try {
            documents = documentDAO.getDocumentsForPublisher(pubName);
            readerView.displayDocuments(documents);
        } catch (Exception e) {
            MessageUtil.showErrorMessage(e.getMessage(),readerView);
        }
    }

    private void populatePublishers() {
        try {
            readerView.populatePublisher(new PublisherDAO().getAllPublishers());
        }
        catch (SQLException e) {
            MessageUtil.showErrorMessage(e.getMessage(),readerView);
        }
    }
}
