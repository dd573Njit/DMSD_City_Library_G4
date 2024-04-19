package dao;

import model.*;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FrequentDocDAO {
    public List<Reader> getNFreqBorrowers(Integer n, String query) {
        List<Reader> readers = new ArrayList<>();
        String sql = "SELECT TOP(?), b.RID, r.RNAME, COUNT(*) AS BORROWEDBOOKS FROM BORROWING b JOIN READERS r ON b.RID = r.RID WHERE b.RID LIKE ? GROUP BY b.RID, r.RNAME ORDER BY BORROWEDBOOKS DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, n);
            pstmt.setString(2, "%" + query + "%");
        }
        catch (SQLException e) {
            System.err.println("Error accessing database: " + e.getMessage());
        }
        return readers;
    }

    public List<Document> getNFreqBorrowedBooks(Integer n, String query) {
        List<Document> books = new ArrayList<>();
        String sql = "SELECT TOP(?) b.DOCID, d.TITLE, COUNT(*) AS TOTBORROWCOUNT FROM BORROWS b JOIN DOCUMENTS d ON b.DOCID = d.DOCID WHERE b.BID LIKE ? GROUP BY b.DOCID, d.TITLE ORDER BY TOTBORROWCOUNT DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, n);
            pstmt.setString(2, "%" + query + "%");
        }
        catch (SQLException e) {
            System.err.println("Error accessing database: " + e.getMessage());
        }
        return books;
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