package controller;

import dao.*;
import model.*;
import util.MessageUtil;
import util.SessionManager;
import view.AdminView;
import view.FrequentDocView;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.ArrayList;
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
    }

    private void getNFrequentBorrowers() {
        try {
            int n = Integer.parseInt(frequentDocView.getNumberField());
            List<Reader> readers = null;
            String bId = frequentDocView.getSelectedBranchNumber();
            if (bId.isEmpty())
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
            String bId = frequentDocView.getSelectedBranchNumber();
            if (bId.isEmpty())
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
    }
