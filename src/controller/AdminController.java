package controller;

import dao.*;
import model.*;
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
                try {
                    addDocDetail();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        adminView.getBtnAddCurrentReader().addActionListener(e -> {
            if(adminView.areAllReaderFieldsFilled()) {
                try {
                    addReaderDetail();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void logoutHandler() {
        SessionManager.getInstance().setAdminId(null);
        SessionManager.getInstance().setAdminPassword(null);
        adminView.dispose();
    }

    private void addDocDetail() throws SQLException {
        String docId = adminView.getTxtDocId();
        String docTitle = adminView.getTxtDocTitle();
        String pubId = adminView.getTxtPubId();
        String pubName = adminView.getTxtPubName();
        String pubAddress = adminView.getTxtPubAddress();
        String copyNo = adminView.getTxtCopyNumber();
        String bId = adminView.getTxtBranchId();
        String position = adminView.getCopyPosition();

        Publisher publisher = new Publisher(pubId, pubName, pubAddress);
        Document document = new Document(docId,docTitle,new Date(),pubId);
        Copy copy = new Copy(docId, copyNo, bId, position);
        PublisherDAO publisherDAO = new PublisherDAO();
        DocumentDAO documentDAO = new DocumentDAO();
        CopyDAO copyDAO = new CopyDAO();
        publisherDAO.addPublisher(publisher);
        documentDAO.addDocument(document);
        copyDAO.addCopy(copy);
    }

    private void addReaderDetail() throws SQLException {
        String rId = adminView.getTxtRId();
        String rName = adminView.getTxtRName();
        String rAddress = adminView.getTxtRAddress();
        int rPhone = Integer.parseInt(adminView.getTxtRPhone());
        String rType = adminView.getTxtRType();

        Reader reader = new Reader(rId,rName,rType,rAddress,rPhone);
        ReaderDAO readerDAO = new ReaderDAO();
        readerDAO.addReader(reader);
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
