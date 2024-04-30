package controller;

import dao.DocumentDAO;
import model.DocumentStatus;
import view.DocumentStatusView;

import java.util.List;

public class DocumentStatusController {
    private final DocumentStatusView documentStatusView;
    private final DocumentDAO documentDAO;
    public DocumentStatusController() {
        documentStatusView = new DocumentStatusView();
        documentDAO = new DocumentDAO();
        attachHandlers();
    }

    public void showDocumentStatusView() {
        documentStatusView.setVisible(true);
    }

    private void attachHandlers() {
        documentStatusView.getSearchText().addActionListener(e -> performSearch(documentStatusView.getSearchText().getText()));
        documentStatusView.getBtnSearch().addActionListener(e -> performSearch(documentStatusView.getSearchText().getText()));
        documentStatusView.getBtnQuit().addActionListener(e -> documentStatusView.dispose());
    }

    private void performSearch(String query) {
        try {
            List<DocumentStatus> results = documentDAO.getDocumentStatuses("%" + query + "%");
            documentStatusView.displayDocuments(results);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
