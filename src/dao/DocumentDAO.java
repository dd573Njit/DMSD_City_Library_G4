package dao;
import model.Document;
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

    public List<Document> searchDocuments(String query) {
        List<Document> results = new ArrayList<>();
        String sql = "SELECT DOCID, TITLE, PDATE, PUBLISHERID FROM DOCUMENTS WHERE TITLE LIKE ? OR DOCID LIKE ? OR PUBLISHERID IN (SELECT PUBLISHERID FROM PUBLISHERS WHERE PUBNAME LIKE ?);";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + query + "%");
            pstmt.setString(2, "%" + query + "%");
            pstmt.setString(3, "%" + query + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    results.add(new Document(rs.getString("DOCID"), rs.getString("TITLE"), rs.getDate("PDATE"), rs.getString("PUBLISHERID")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during database query: " + e.getMessage());
            e.printStackTrace();
        }
        return results;
    }


    public List<Document> getReturnableDocuments() {
        List<Document> results = new ArrayList<>();
        String sql = "SELECT DOCID, TITLE, PDATE, PUBLISHERID from DOCUMENTS d JOIN BORROWS b on d.DOCID = b.DOCID where b.RID = ?;";  // Adjust the SQL based on your schema
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, SessionManager.getInstance().getCurrentReaderCardNumber());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    results.add(new Document(rs.getString("DOCID"), rs.getString("TITLE"), rs.getDate("PDATE"), rs.getString("PUBLISHERID")));
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

