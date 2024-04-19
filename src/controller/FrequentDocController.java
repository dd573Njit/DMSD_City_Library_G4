package controller;

import dao.*;
import model.*;
import util.MessageUtil;
import util.SessionManager;
import view.AdminView;
import view.FrequentDocView;

import java.sql.SQLException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class FrequentDocController {
    private final FrequentDocView frequentDocView;

    public FrequentDocController() {
        frequentDocView = new FrequentDocView();
        attachHandlers();
    }

    public void showFrequentDocView() {
        frequentDocView.setVisible(true);
    }

    public void attachHandlers() {
        frequentDocView.getBtnFreqBorrowers().addActionListener(e -> showFreqBorrowers());
        frequentDocView.getBtnFreqBorrowedBooks().addActionListener(e -> showFreqBorrowedBooks());
        frequentDocView.getBtnAvgFinePaid().addActionListener(e -> showAvgFinedReaders());
        frequentDocView.getBtnBranchInfo().addActionListener(e -> showBranchInfo());

        frequentDocView.getBtnLogout().addActionListener(e -> logoutHandler());
    }

    private void showFreqBorrowers() {
        List<String> readers = new ArrayList<>();
    }

    private void showFreqBorrowedBooks() {
        List<String> books = new ArrayList<>();
    }

    private void showAvgFinedReaders() {
        List<String> readers = new ArrayList<>();
    }

    private void showBranchInfo() {
        BranchDAO branchDAO = new BranchDAO();
        List<String> branches = new ArrayList<>();
        branches.add(String.format("%-20s %s", "Branch Name", "Branch Location"));
        for(Branch branch : branchDAO.getAllBranches()) {
            branches.add(String.format("%-25s %s", branch.getLName(), branch.getLocation()));
        }
        frequentDocView.showPanel("Branch Panel");
        frequentDocView.displayBranchInfo(branches.toArray(new String[0]));
    }

    private void logoutHandler() {
        SessionManager.getInstance().setAdminId(null);
        SessionManager.getInstance().setAdminPassword(null);
        frequentDocView.dispose();
    }
}
