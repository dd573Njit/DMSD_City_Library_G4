package controller;

import model.DocumentDetail;
import view.ReturnDocumentsView;

import java.util.List;

public class ReturnDocumentsController {
    private final ReturnDocumentsView returnDocumentsView;

    public ReturnDocumentsController() {
        returnDocumentsView = new ReturnDocumentsView();
        attachHandlers();
    }

    private void attachHandlers() {
        returnDocumentsView.getBtnQuit().addActionListener(e -> closeReturnDocuments());
    }

    private void closeReturnDocuments() {
        returnDocumentsView.dispose();
    }

    public void showReturnDocuments(List<DocumentDetail> documents) {
        returnDocumentsView.setVisible(true);
        returnDocumentsView.displayDocuments(documents);
    }
}
