package controller;

import model.DocumentDetail;
import view.ListDocumentView;

import java.util.List;

public class ListDocumentController {
    private final ListDocumentView listDocumentView;

    public ListDocumentController() {
        listDocumentView = new ListDocumentView();
        attachHandlers();
    }

    private void attachHandlers() {
        listDocumentView.getBtnQuit().addActionListener(e -> closeListDocument());
    }

    private void closeListDocument() {
        listDocumentView.dispose();
    }

    public void showListDocument(List<DocumentDetail> documents) {
        listDocumentView.setVisible(true);
        listDocumentView.displayDocuments(documents);
    }
}
