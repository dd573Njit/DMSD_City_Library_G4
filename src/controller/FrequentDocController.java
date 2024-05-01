package controller;

import dao.*;
import model.*;
import util.MessageUtil;
import view.FrequentDocView;
import java.sql.Date;
import java.util.List;

public class FrequentDocController {
    private final FrequentDocView frequentDocView;
    private final FrequentDocDAO frequentDocDAO;
    public FrequentDocController() {
        frequentDocView = new FrequentDocView();
        frequentDocDAO = new FrequentDocDAO();
        attachHandlers();
    }

    public void showFrequentDocView() {
        frequentDocView.setVisible(true);
        frequentDocView.populateBranchNumbers(new BranchDAO().getBranchNumbers());
    }

    public void attachHandlers() {
        frequentDocView.getFrequentBorrowersButton().addActionListener(e -> getNFrequentBorrowers());
        frequentDocView.getBorrowedBooksButton().addActionListener(e -> getNFrequentDocs());
        frequentDocView.getPopularBooksButton().addActionListener(e -> getPopularBooks());
        frequentDocView.getBranchAvgFineButton().addActionListener(e -> showBranchFineInfoList());
    }

    private void getNFrequentBorrowers() {
        try {
            int n = Integer.parseInt(frequentDocView.getNumberField());
            List<Reader> readers = null;
            String bId = frequentDocView.getSelectedBranchNumber().toUpperCase();
            if (bId.equals("NONE"))
                readers = frequentDocDAO.getNFreqBorrowers(n);
            else
                readers = frequentDocDAO.getNFreqBorrowers(n, bId);
            frequentDocView.displayReaderList(readers);
        }catch (NumberFormatException e) {
            MessageUtil.showErrorMessage("Please add a number",frequentDocView);
        } catch (Exception e) {
            MessageUtil.showErrorMessage("Something went wrong",frequentDocView);
        }
    }

    private void getNFrequentDocs() {
        try {

            int n = Integer.parseInt(frequentDocView.getNumberField());
            List<DocumentDetail> docs = null;
            String bId = frequentDocView.getSelectedBranchNumber().toUpperCase();
            if (bId.equals("NONE"))
                docs = frequentDocDAO.getNFreqBorrowedDocuments(n);
            else
                docs = frequentDocDAO.getNFreqBorrowedDocuments(n,bId);
            frequentDocView.displayDocumentList(docs);
            } catch (NumberFormatException e) {
                MessageUtil.showErrorMessage("Please add a number",frequentDocView);
            } catch (Exception e) {
                MessageUtil.showErrorMessage("Something went wrong",frequentDocView);
            }
        }

    private void getPopularBooks() {
        List<DocumentDetail> docs = frequentDocDAO.getNPopularBooks(frequentDocView.getYear());
        frequentDocView.displayDocumentList(docs);
    }

    private void showBranchFineInfoList() {
        try {
            Date startDate = new Date(frequentDocView.getStartDate().getTime());
            Date endDate = new Date(frequentDocView.getEndDate().getTime());
            List<BranchFineInfo> branchFineInfoList = frequentDocDAO.getAvgFinePaid(startDate, endDate);
            frequentDocView.displayBranchFineInfoList(branchFineInfoList);
        } catch(Exception e) {
            MessageUtil.showErrorMessage("Start date or end date is not selected",frequentDocView);
        }
    }
}
