package controller;

import dao.*;
import model.*;
import util.MessageUtil;
import util.SessionManager;
import view.AdminView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminController {
    private final AdminView adminView;
    public AdminController() {
        adminView = new AdminView();
        attachHandlers();
    }

    public void showAdminView() {
        adminView.setVisible(true);
    }

    private void attachHandlers() {
        adminView.getBtnLogout().addActionListener(e -> logoutHandler());
        adminView.getBtnAddDoc().addActionListener(e -> adminView.showPanel("Document Panel"));
        adminView.getBtnAddReader().addActionListener(e -> adminView.showPanel("Reader Panel"));
        adminView.getBtnBranchInfo().addActionListener(e -> showBranchInfo());
        adminView.getBtnAddCurrentDoc().addActionListener(e -> {
            if(adminView.areAllDocFieldsFilled()) {
                addPublisherDetail();
            }
        });

        adminView.getBtnAddCurrentReader().addActionListener(e -> {
            if(adminView.areAllReaderFieldsFilled()) {
                addReaderDetail();
            }
        });
    }

    private void logoutHandler() {
        SessionManager.getInstance().setAdminId(null);
        SessionManager.getInstance().setAdminPassword(null);
        adminView.dispose();
    }

    private  void addPublisherDetail() {
        String pubId = adminView.getTxtPubId();
        String pubName = adminView.getTxtPubName();
        String pubAddress = adminView.getTxtPubAddress();
        try {
            Publisher publisher = new Publisher(pubId, pubName, pubAddress);
            PublisherDAO publisherDAO = new PublisherDAO();
            publisherDAO.addPublisher(publisher);
            addDocDetail(pubId);
        } catch (SQLException ex) {
            addDocDetail(pubId);
        }
    }

    private void addDocDetail(String pubId) {
        String docId = adminView.getTxtDocId();
        String docTitle = adminView.getTxtDocTitle();
        try {
            Document document = new Document(docId, docTitle, new Date(), pubId);
            DocumentDAO documentDAO = new DocumentDAO();
            documentDAO.addDocument(document);
            addCopyDetail(docId);
        } catch (SQLException ex) {
            addCopyDetail(docId);
        }
    }

    private void addCopyDetail(String docId) {
        String copyNo = adminView.getTxtCopyNumber();
        String bId = adminView.getTxtBranchId();
        String position = adminView.getCopyPosition();
        try {
            Copy copy = new Copy(docId, copyNo, bId, position);
            CopyDAO copyDAO = new CopyDAO();
            copyDAO.addCopy(copy);
            MessageUtil.showSuccessMessage("Successfully added",adminView);
        } catch (SQLException ex) {
            MessageUtil.showErrorMessage("Same Copy number", adminView);
        }
    }

    private void addReaderDetail() {
        String rId = adminView.getTxtRId();
        String rName = adminView.getTxtRName();
        String rAddress = adminView.getTxtRAddress();
        String rType = adminView.getTxtRType();

        try {
            int rPhone = Integer.parseInt(adminView.getTxtRPhone());
            Reader reader = new Reader(rId, rName, rType, rAddress, rPhone);
            ReaderDAO readerDAO = new ReaderDAO();
            readerDAO.addReader(reader);
            MessageUtil.showSuccessMessage("Successfully added",adminView);
        } catch(NumberFormatException e) {
            MessageUtil.showErrorMessage("Phone Number is not correct", adminView);
        } catch (SQLException ex) {
            MessageUtil.showErrorMessage("Reader already Exists", adminView);
        }
    }

    private void showBranchInfo() {
        BranchDAO branchDAO = new BranchDAO();
        List<String> branches = new ArrayList<>();
        branches.add(String.format("%-20s %s", "Branch Name", "Branch Location"));
        for(Branch branch : branchDAO.getAllBranches()) {
            branches.add(String.format("%-25s %s",branch.getLName(),branch.getLocation()));
        }
        adminView.showPanel("Branch Panel");
        adminView.dsiplayBranchInfo(branches.toArray(new String[0]));
    }
}
