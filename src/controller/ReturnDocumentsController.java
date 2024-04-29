package controller;

import dao.BorrowsDAO;
import dao.DocumentDAO;
import model.ReturnableDocument;
import util.MessageUtil;
import util.SessionManager;
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
        returnDocumentsView.getBtnReturnDocuments().addActionListener(e -> returnDocuments());
    }

    private void closeReturnDocuments() {
        returnDocumentsView.dispose();
    }

    private void computeFineForCurrentDate(List<ReturnableDocument> documents) {
        long totalDays = 0;
        for (ReturnableDocument returnableDocument : documents) {
            Date returnDate = returnableDocument.getRDTime();
            totalDays += getExtendedDays(returnDate);
        }
        returnDocumentsView.setStatusMessage("Total fine: " + (totalDays * lateFine));
    }

    private long getExtendedDays(Date returnDate) {
        Date currentDate = new Date();
        if (currentDate.after(returnDate)) {
            long diffInMillis = currentDate.getTime() - returnDate.getTime();
            return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
        }
        return 0;
    }

    private void returnDocuments() {
        List<ReturnableDocument> documents = returnDocumentsView.getAllDocuments();
        if(documents.isEmpty()) {
            MessageUtil.showErrorMessage("No Documents Present",returnDocumentsView);
            return;
        }
        BorrowsDAO borrowsDAO = new BorrowsDAO();
        String rId = SessionManager.getInstance().getCurrentReaderCardNumber().toUpperCase();
        Date currentDate = new Date();
        java.sql.Date currentSqlDate = new java.sql.Date(currentDate.getTime());
        try {
            for (ReturnableDocument returnableDocument : documents) {
                Date returnDate = returnableDocument.getRDTime();
                long days = getExtendedDays(returnDate);
                borrowsDAO.returnBorrowedDocuments(rId, currentSqlDate, (days * lateFine));
            }
            MessageUtil.showSuccessMessage("Returned documents successfully",returnDocumentsView);
        }catch (Exception e) {
            MessageUtil.showErrorMessage(e.getMessage(), returnDocumentsView);
        }
    }

    public void showReturnDocuments() {
        returnDocumentsView.setVisible(true);
        List<ReturnableDocument> documents = new DocumentDAO().getReturnableDocuments();
        returnDocumentsView.displayDocuments(documents);
        computeFineForCurrentDate(documents);
    }
}
