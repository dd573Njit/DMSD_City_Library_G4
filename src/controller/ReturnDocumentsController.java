package controller;

import dao.DocumentDAO;
import model.ReturnableDocument;
import view.ReturnDocumentsView;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ReturnDocumentsController {
    private final ReturnDocumentsView returnDocumentsView;
    private final float lateFine = 0.2f;
    public ReturnDocumentsController() {
        returnDocumentsView = new ReturnDocumentsView();
        attachHandlers();
    }

    private void attachHandlers() {
        returnDocumentsView.getBtnQuit().addActionListener(e -> closeReturnDocuments());
        //returnDocumentsView.getBtnReturnDocuments().addActionListener(e -> );
    }

    private void closeReturnDocuments() {
        returnDocumentsView.dispose();
    }

    private void computeFineForCurrentDate(List<ReturnableDocument> documents) {
        Date currentDate = new Date();
        long totalDays = 0;
        for (ReturnableDocument returnableDocument : documents) {
            Date returnDate = returnableDocument.getBDTime();
            if (currentDate.after(returnDate)) {
                long diffInMillis = currentDate.getTime() - returnDate.getTime();
                long days = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
                totalDays += days;
            }
        }
        returnDocumentsView.setStatusMessage("Total fine: " + (totalDays * lateFine));
    }

    public void showReturnDocuments() {
        returnDocumentsView.setVisible(true);
        List<ReturnableDocument> documents = new DocumentDAO().getReturnableDocuments();
        returnDocumentsView.displayDocuments(documents);
        computeFineForCurrentDate(documents);
    }
}
