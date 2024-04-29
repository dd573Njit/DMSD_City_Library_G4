package controller;

import dao.DocumentDAO;
import dao.ReserveDAO;
import model.DocumentDetail;
import util.CalendarUtil;
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
        String rId = SessionManager.getInstance().getCurrentReaderCardNumber().toUpperCase();
        if(CalendarUtil.isCurrentTimeAfter6Pm()) {
            try {
                new ReserveDAO().removeReservedDocs(rId);
                MessageUtil.showErrorMessage("Any reserved documents gets cancelled after 6pm", listDocumentView);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            List<DocumentDetail> documents = new DocumentDAO().getReservedDocuments(rId);
            listDocumentView.displayDocuments(documents);
        } catch (Exception e) {
            MessageUtil.showErrorMessage(e.getMessage(),listDocumentView);
        }
    }
}
