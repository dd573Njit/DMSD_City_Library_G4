package controller;

import dao.DocumentDAO;
import model.DocumentDetail;
import util.MessageUtil;
import util.SessionManager;
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

    public void showListDocument() {
        listDocumentView.setVisible(true);
        String rId = SessionManager.getInstance().getCurrentReaderCardNumber();
        try {
            List<DocumentDetail> documents = new DocumentDAO().getReservedDocuments(rId);
            listDocumentView.displayDocuments(documents);
        } catch (Exception e) {
            MessageUtil.showErrorMessage(e.getMessage(),listDocumentView);
        }
    }
}
