package dao;
import model.Document;
import model.DocumentDetail;
import util.DatabaseConnection;
import util.SessionManager;

import java.sql.*;
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
        String sql = "SELECT d.DOCID, d.TITLE, COPYNO, BID FROM DOCUMENTS d, COPIES c WHERE d.TITLE LIKE ? OR d.DOCID LIKE ? OR PUBLISHERID IN (SELECT PUBLISHERID FROM PUBLISHERS WHERE PUBNAME LIKE ?) and d.DOCID = c.DOCID;";
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


    public List<DocumentDetail> getReturnableDocuments() {
        List<DocumentDetail> results = new ArrayList<>();
        String sql = "SELECT d.DOCID, d.TITLE, COPYNO, BID from DOCUMENTS d JOIN COPIES c on d.DOCID = c.DOCID JOIN BORROWS b on d.DOCID = b.DOCID where b.RID = ?;";  // Adjust the SQL based on your schema
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, SessionManager.getInstance().getCurrentReaderCardNumber());
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
}

