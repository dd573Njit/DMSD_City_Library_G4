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
    private boolean docsBorrowed = false;
    public CheckoutController() {
        checkoutView = new CheckoutView();
        attachHandlers();
    }

    public void showCheckoutView(List<DocumentDetail> documents) {
        checkoutView.setVisible(true);
        docsBorrowed = false;
        this.checkoutDocuments = documents;
        System.out.println(checkoutDocuments);
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
        if(docsBorrowed) {
            MessageUtil.showErrorMessage("Documents already CheckedOut", checkoutView);
            return;
        }
        int borCount = checkoutDAO.getBorrowingCount() + 1;
        String prefix = borCount > 9 ? "BOR0" : "BOR00";
        String borNo = prefix + borCount;
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
                String bId = documentDetail.getBId().toUpperCase();
                Borrows borrows = new Borrows(borNo, docId, copyNo, bId, rId);
                checkoutDAO.addBorrowsDetail(borrows);
            }
            MessageUtil.showSuccessMessage("Successfully CheckedOut", checkoutView);
            removeReservedDoc();
            docsBorrowed = true;
        } catch (SQLException ex) {
            MessageUtil.showErrorMessage("Document already CheckedOut", checkoutView);
        }
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
