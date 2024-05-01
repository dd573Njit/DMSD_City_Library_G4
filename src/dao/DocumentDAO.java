package dao;
import model.Document;
import model.DocumentDetail;
import model.DocumentStatus;
import model.ReturnableDocument;
import util.CalendarUtil;
import util.DatabaseConnection;
import util.SessionManager;

import java.sql.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class DocumentDAO {
    public List<Document> getAllDocuments() {
        List<Document> documents = new ArrayList<>();
        String sql = "SELECT * FROM DOCUMENTS";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                documents.add(new Document(rs.getString("DOCID"), rs.getString("TITLE"), rs.getDate("PDATE"), rs.getString("PUBLISHERID")));
            }
        } catch (SQLException e) {
            System.err.println("Error accessing database: " + e.getMessage());
        }
        return documents;
    }

    public List<DocumentDetail> searchDocuments(String query) {
        List<DocumentDetail> results = new ArrayList<>();
//        String sql = "SELECT d.DOCID, d.TITLE, c.COPYNO, c.BID FROM DOCUMENTS d, COPIES c WHERE d.TITLE LIKE ? OR d.DOCID LIKE ? OR PUBLISHERID IN (SELECT PUBLISHERID FROM PUBLISHERS WHERE PUBNAME LIKE ?) and d.DOCID = c.DOCID;";
        String sql = "SELECT d.DOCID, d.TITLE, c.COPYNO, c.BID FROM DOCUMENTS d " +
                "JOIN COPIES c ON d.DOCID = c.DOCID " +
                "LEFT JOIN BORROWS b ON b.DOCID = d.DOCID " +
                "AND b.COPYNO = c.COPYNO AND b.BID = c.BID " +
                "LEFT JOIN RESERVES r ON r.DOCID = d.DOCID " +
                "AND r.COPYNO = c.COPYNO AND r.BID = c.BID " +
                "WHERE (d.TITLE LIKE ? OR d.DOCID LIKE ? " +
                "OR d.PUBLISHERID IN (SELECT PUBLISHERID FROM PUBLISHERS WHERE PUBNAME LIKE ?)) " +
                "AND b.BOR_NO IS NULL AND r.RESERVATION_NO IS NULL";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + query + "%");
            pstmt.setString(2, "%" + query + "%");
            pstmt.setString(3, "%" + query + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    results.add(new DocumentDetail(rs.getString("DOCID"), rs.getString("TITLE"), rs.getString("COPYNO"), rs.getString("BID")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during database query: " + e.getMessage());
            e.printStackTrace();
        }
        return results;
    }


    public List<ReturnableDocument> getReturnableDocuments(String rId) {
        List<ReturnableDocument> results = new ArrayList<>();
        String sql = "SELECT d.DOCID, d.TITLE, c.COPYNO, bg.BDTIME \n" +
                "FROM DOCUMENTS d \n" +
                "JOIN COPIES c ON d.DOCID = c.DOCID \n" +
                "JOIN BORROWS b ON c.DOCID = b.DOCID AND c.COPYNO = b.COPYNO \n" +
                "JOIN BORROWING bg ON bg.BOR_NO = b.BOR_NO \n" +
                "WHERE b.RID = ? AND bg.RDTIME IS NULL;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, rId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Date date = rs.getDate("BDTIME");
                    Date returnDate = CalendarUtil.get20DaysAfterDate(date);
                    results.add(new ReturnableDocument(rs.getString("DOCID"), rs.getString("TITLE"), rs.getString("COPYNO"), date,returnDate));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during database query: " + e.getMessage());
            e.printStackTrace();
        }
        return results;
    }

    public void addDocument(Document document) throws SQLException {
        String sql = "INSERT INTO DOCUMENTS (DOCID, TITLE, PDATE, PUBLISHERID) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, document.getDocId());
            pstmt.setString(2, document.getTitle());
            pstmt.setTimestamp(3, new Timestamp(document.getPDate().getTime()));
            pstmt.setString(4, document.getPublisherId());
            pstmt.executeUpdate();
        }
    }

    public List<DocumentDetail> getDocumentsForPublisher(String pubName) throws SQLException {
        List<DocumentDetail> results = new ArrayList<>();
        String sql = "SELECT d.DOCID, d.TITLE, c.COPYNO, c.BID\n" +
                "FROM DOCUMENTS d\n" +
                "JOIN COPIES c ON c.DOCID = d.DOCID\n" +
                "JOIN PUBLISHERS p ON p.PUBLISHERID = d.PUBLISHERID\n" +
                "AND p.PUBNAME = ?;";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pubName);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    results.add(new DocumentDetail(rs.getString("DOCID"), rs.getString("TITLE"), rs.getString("COPYNO"), rs.getString("BID")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during database query: " + e.getMessage());
            e.printStackTrace();
        }
        return results;
    }

    public List<DocumentDetail> getReservedDocuments(String rId) throws SQLException {
        List<DocumentDetail> results = new ArrayList<>();
        String sql = "SELECT d.DOCID, d.TITLE, r.COPYNO, r.BID \n" +
                "FROM DOCUMENTS d \n" +
                "JOIN COPIES c ON d.DOCID = c.DOCID\n" +
                "JOIN RESERVES r ON c.DOCID = r.DOCID AND c.COPYNO = r.COPYNO\n" +
                "WHERE r.RID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, rId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    results.add(new DocumentDetail(rs.getString("DOCID"), rs.getString("TITLE"), rs.getString("COPYNO"), rs.getString("BID")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during database query: " + e.getMessage());
            e.printStackTrace();
        }
        return results;
    }

    public List<DocumentStatus> getDocumentStatuses(String query) throws SQLException {
        List<DocumentStatus> results = new ArrayList<>();
        String sql = "SELECT  d.DOCID, d.TITLE, c.COPYNO, r.DTIME, b.BDTIME, b.RDTIME \n" +
                "FROM DOCUMENTS d\n" +
                "JOIN COPIES c ON d.DOCID = c.DOCID \n" +
                "LEFT JOIN RESERVES rs ON c.DOCID = rs.DOCID AND c.COPYNO = rs.COPYNO \n" +
                "LEFT JOIN BORROWS bs ON c.DOCID = bs.DOCID AND c.COPYNO = bs.COPYNO \n" +
                "LEFT JOIN RESERVATION r ON r.RES_NO = rs.RESERVATION_NO \n" +
                "LEFT JOIN BORROWING b ON b.BOR_NO = bs.BOR_NO \n" +
                "WHERE d.TITLE LIKE ? OR d.DOCID LIKE ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, query);
            pstmt.setString(2, query);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    results.add(new DocumentStatus(rs.getString("DOCID"), rs.getString("TITLE"), rs.getString("COPYNO"), rs.getDate("DTIME"), rs.getDate("BDTIME"), rs.getDate("RDTIME")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during database query: " + e.getMessage());
            e.printStackTrace();
        }
        return results;
    }
}

