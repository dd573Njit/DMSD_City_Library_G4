package dao;

import model.*;
import util.DatabaseConnection;
import util.SessionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FrequentDocDAO {
    public List<Reader> getNFreqBorrowers(int n) {
        List<Reader> readers = new ArrayList<>();
        String sql = "SELECT r.RID, r.RTYPE, r.RNAME, r.RADDRESS, r.PHONE_NO, COUNT(b.DOCID) AS DocumentCount\n" +
                "FROM READERS r\n" +
                "JOIN BORROWS b ON r.RID = b.RID\n" +
                "WHERE r.RID IN (\n" +
                "    SELECT RID FROM BORROWS GROUP BY RID\n" +
                ")\n" +
                "GROUP BY r.RID, r.RNAME\n" +
                "ORDER BY r.RID DESC\n" +
                "Limit ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, n);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    readers.add(new Reader(rs.getString("RID"), rs.getString("RTYPE"), rs.getString("RNAME"), rs.getString("RADDRESS"), rs.getInt("PHONE_NO")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during database query: " + e.getMessage());
            e.printStackTrace();
        }
        return readers;
    }

    public List<Reader> getNFreqBorrowers(int n, String bId) {
        List<Reader> readers = new ArrayList<>();
        String sql = "SELECT r.RID, r.RTYPE, r.RNAME, r.RADDRESS, r.PHONE_NO, COUNT(b.DOCID) AS DocumentCount\n" +
                "FROM READERS r\n" +
                "JOIN BORROWS b ON r.RID = b.RID\n" +
                "WHERE r.RID IN (\n" +
                "    SELECT RID FROM BORROWS GROUP BY RID\n" +
                ")\n" +
                "AND BID = ?"+
                "GROUP BY r.RID, r.RNAME\n" +
                "ORDER BY r.RID DESC\n" +
                "Limit ?;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bId);
            pstmt.setInt(2, n);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    readers.add(new Reader(rs.getString("RID"), rs.getString("RTYPE"), rs.getString("RNAME"), rs.getString("RADDRESS"), rs.getInt("PHONE_NO")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during database query: " + e.getMessage());
            e.printStackTrace();
        }
        return readers;
    }

    public List<DocumentDetail> getNFreqBorrowedDocuments(int n) {
        List<DocumentDetail> documentDetails = new ArrayList<>();
        String sql = "SELECT b.DOCID, d.TITLE, c.COPYNO, c.BID, COUNT(b.DOCID) AS DocumentCount\n" +
                "FROM COPIES c\n" +
                "JOIN BORROWS b ON c.DOCID = b.DOCID AND c.BID = b.BID\n" +
                "JOIN DOCUMENTS d ON d.DOCID = b.DOCID\n" +
                "GROUP BY b.DOCID, d.TITLE, c.COPYNO, c.BID\n" +
                "ORDER BY DocumentCount DESC\n" +
                "LIMIT ?;";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, n);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    documentDetails.add(new DocumentDetail(rs.getString("DOCID"), rs.getString("TITLE"), rs.getString("COPYNO"), rs.getString("BID")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during database query: " + e.getMessage());
            e.printStackTrace();
        }
        return documentDetails;
    }

    public List<DocumentDetail> getNFreqBorrowedDocuments(int n, String bId) {
        List<DocumentDetail> documentDetails = new ArrayList<>();
        String sql = "SELECT b.DOCID, d.TITLE, c.COPYNO, c.BID, COUNT(b.DOCID) AS DocumentCount\n" +
                "FROM COPIES c\n" +
                "JOIN BORROWS b ON c.DOCID = b.DOCID AND c.BID = b.BID\n" +
                "JOIN DOCUMENTS d ON d.DOCID = b.DOCID\n" +
                "WHERE b.BID = ?\n" +
                "GROUP BY b.DOCID, d.TITLE, c.COPYNO, c.BID\n" +
                "ORDER BY DocumentCount DESC\n" +
                "LIMIT ?; ";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bId);
            pstmt.setInt(2, n);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    documentDetails.add(new DocumentDetail(rs.getString("DOCID"), rs.getString("TITLE"), rs.getString("COPYNO"), rs.getString("BID")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during database query: " + e.getMessage());
            e.printStackTrace();
        }
        return documentDetails;
    }

    public List<Document> getNPopularBooks(String query) {
        List<Document> books = new ArrayList<>();
        String sql = "SELECT TOP(10) b.DOCID, d.TITLE, COUNT(*) AS TOTBORROWCOUNT FROM BORROWS b JOIN DOCUMENTS d ON b.DOCID = d.DOCID WHERE YEAR(d.PDATE) LIKE ? GROUP BY b.DOCID, d.TITLE ORDER BY TOTBORROWCOUNT DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + query + "%");
        }
        catch (SQLException e) {
            System.err.println("Error accessing database: " + e.getMessage());
        }
        return books;
    }

    public List<Reader> getAvgFinePaid(String stdDate, String endDate) {
        List<Reader> readers = new ArrayList<>();
        String sql = "SELECT br.BID, br.LNAME, AVG(bow.FINE) AS AVGUSERFINE FROM BORROWS bo JOIN BORROWING bow JOIN BRANCH br ON bo.BID = br.BID AND bow.BOR_NO = bo.BOR_NO WHERE bow.BDTIME BETWEEN ? AND ? GROUP BY br.BID, br.LNAME ORDER BY AVGUSERFINE DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(stdDate));
            pstmt.setDate(2, Date.valueOf(endDate));
        }
        catch (SQLException e) {
            System.err.println("Error accessing database: " + e.getMessage());
        }
        return readers;
    }
}