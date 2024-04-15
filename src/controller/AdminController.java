package controller;

import dao.DocumentDAO;
import dao.PublisherDAO;
import dao.ReaderDAO;
import model.Document;
import model.Publisher;
import model.Reader;
import util.SessionManager;
import view.AdminView;

import java.sql.SQLException;
import java.util.Date;

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

        Publisher publisher = new Publisher(pubId, pubName, pubAddress);
        Document document = new Document(docId,docTitle,new Date(),pubId);
        PublisherDAO publisherDAO = new PublisherDAO();
        DocumentDAO documentDAO = new DocumentDAO();
        publisherDAO.addPublisher(publisher);
        documentDAO.addDocument(document);
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
}
