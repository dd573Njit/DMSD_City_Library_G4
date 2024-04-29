package controller;

import dao.CheckoutDAO;
import dao.ReserveDAO;
import model.*;
import util.MessageUtil;
import util.SessionManager;
import view.CheckoutView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CheckoutController {
    private final CheckoutView checkoutView;
    private List<DocumentDetail> checkoutDocuments;
    private List<DocumentDetail> allDocuments;
    private boolean cannotCheckout = false;
    public CheckoutController() {
        checkoutView = new CheckoutView();
        attachHandlers();
    }

    public void showCheckoutView(List<DocumentDetail> documents) {
        checkoutView.setVisible(true);
        this.checkoutDocuments = documents;
        getReservedDocs();
        if(cannotCheckout) {
            MessageUtil.showErrorMessage("You cannot checkout more than 10 documents", checkoutView);
            return;
        }
    }

    private void attachHandlers() {
        checkoutView.getBtnCheckout().addActionListener(e -> addBorrowingDetail());
        checkoutView.getBtnQuit().addActionListener(e -> closeCheckoutView());
    }

    private void closeCheckoutView() {
        checkoutView.dispose();
    }

    private void addBorrowingDetail() {
        if(cannotCheckout) {
            MessageUtil.showErrorMessage("You cannot checkout more than 10 documents", checkoutView);
            return;
        }
        CheckoutDAO checkoutDAO = new CheckoutDAO();
        String rId = SessionManager.getInstance().getCurrentReaderCardNumber().toUpperCase();
        if(areDocsBorrowed(checkoutDAO, rId)) {
            MessageUtil.showErrorMessage("Documents already CheckedOut", checkoutView);
            return;
        }
        int borCount = checkoutDAO.getBorrowingCount();
        String prefix = borCount > 9 ? borCount + "BOR0" : "BOR00";
        String borNo = String.format(prefix + "%d",borCount + 1).toUpperCase();
        Date bDate = new Date();
        try {
                Borrowing borrowing = new Borrowing(borNo, bDate, null);
                checkoutDAO.addBorrowingDate(borrowing);
                addCheckoutDetail(checkoutDAO,rId,borNo);
        } catch (SQLException ex) {
                addCheckoutDetail(checkoutDAO,rId,borNo);
        }
    }

    private void addCheckoutDetail(CheckoutDAO checkoutDAO, String rId, String borNo) {
        try {
            for (DocumentDetail documentDetail : allDocuments) {
                String docId = documentDetail.getDocId().toUpperCase();
                String copyNo = documentDetail.getCopyNo();
                String bId = documentDetail.getBId();
                Borrows borrows = new Borrows(borNo, docId, copyNo, bId, rId.toUpperCase());
                checkoutDAO.addBorrowsDetail(borrows);
                MessageUtil.showSuccessMessage("Successfully CheckedOut", checkoutView);
            }
            removeReservedDoc();
        } catch (SQLException ex) {
            MessageUtil.showErrorMessage("Document already CheckedOut", checkoutView);
        }
    }

    private boolean areDocsBorrowed(CheckoutDAO checkoutDAO, String rId) {
        try {
            List<DocumentDetail> docs = checkoutDAO.getBorrowedDocId(rId);
            if(docs.getFirst().getDocId().equals(checkoutDocuments.getFirst().getDocId())) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private void getReservedDocs() {
        try {
            ReserveDAO reserveDAO = new ReserveDAO();
            String rId = SessionManager.getInstance().getCurrentReaderCardNumber().toUpperCase();
            List<DocumentDetail> reservedDocs = reserveDAO.getReservedDocId(rId);
            if(reservedDocs.size() + checkoutDocuments.size() > 10) {
                cannotCheckout = true;
                return;
            }
            allDocuments = new ArrayList<>();
            allDocuments.addAll(reservedDocs);
            allDocuments.addAll(checkoutDocuments);
           checkoutView.displayDocuments(allDocuments);

        }catch (Exception e) {
            MessageUtil.showErrorMessage("Reservation Not Found", checkoutView);
        }
    }

    private void removeReservedDoc() {
        try {
            ReserveDAO reserveDAO = new ReserveDAO();
            String rId = SessionManager.getInstance().getCurrentReaderCardNumber().toUpperCase();
            reserveDAO.removeReservedDocs(rId);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
