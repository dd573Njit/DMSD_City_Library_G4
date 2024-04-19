package controller;

import dao.CheckoutDAO;
import model.*;
import util.MessageUtil;
import util.SessionManager;
import view.CheckoutView;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CheckoutController {
    private final CheckoutView checkoutView;
    private List<DocumentDetail> documentDetail;
    public CheckoutController() {
        checkoutView = new CheckoutView();
        attachHandlers();
    }

    public void showCheckoutView(List<DocumentDetail> documents) {
        checkoutView.setVisible(true);
        this.documentDetail = documents;
        checkoutView.displayDocuments(documents);
    }

    private void attachHandlers() {
        checkoutView.getBtnCheckout().addActionListener(e -> addBorrowingDetail());
        checkoutView.getBtnQuit().addActionListener(e -> closeCheckoutView());
    }

    private void closeCheckoutView() {
        checkoutView.dispose();
    }

    private void addBorrowingDetail() {
        CheckoutDAO checkoutDAO = new CheckoutDAO();
        String rId = SessionManager.getInstance().getCurrentReaderCardNumber();
        int resCount = checkoutDAO.getBorrowingCountForAReader(rId);
        if(resCount >= 10) {
            MessageUtil.showErrorMessage("You already have 10 borrowed docs", checkoutView);
            return;
        }
        else {
            String borNo = String.format("BOR%d",resCount + 1);
            Date bDate = new Date();
            try {
                Borrowing borrowing = new Borrowing(borNo, bDate, null);
                checkoutDAO.addBorrowingDate(borrowing);
                addCheckoutDetail(checkoutDAO,rId,borNo);
            } catch (SQLException ex) {
                addCheckoutDetail(checkoutDAO,rId,borNo);
            }
        }
    }

    private void addCheckoutDetail(CheckoutDAO checkoutDAO, String rId, String borNo) {
        try {
            for (DocumentDetail documentDetail : documentDetail) {
                String docId = documentDetail.getDocId();
                String copyNo = documentDetail.getCopyNo();
                String bId = documentDetail.getBId();
                Borrows borrows = new Borrows(borNo, docId, copyNo, bId, rId);
                checkoutDAO.addBorrowsDetail(borrows);
                MessageUtil.showSuccessMessage("Successfully CheckedOut", checkoutView);
            }
        } catch (SQLException ex) {
            MessageUtil.showErrorMessage("Document already CheckedOut", checkoutView);
        }
    }
}
