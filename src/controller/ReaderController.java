package controller;

import dao.DocumentDAO;
import model.Document;
import view.ReaderView;

import java.util.List;

public class ReaderController {
    private ReaderView readerView;
    private DocumentDAO documentDAO;

    public ReaderController() {
        readerView = new ReaderView();
        documentDAO = new DocumentDAO();
    }

    public void showReaderView() {
        List<Document> documents = documentDAO.getAllDocuments();
        readerView.displayDocuments(documents);
        readerView.setVisible(true);
    }
}