package dao;

import model.*;
import util.DatabaseConnection;

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

    public List<DocumentDetail> getNPopularBooks(int year) {
        List<DocumentDetail> documents = new ArrayList<>();
        String sql = "SELECT d.DOCID, d.TITLE, c.COPYNO, c.BID, COUNT(b.BOR_NO) AS BorrowCount\n" +
                "FROM DOCUMENTS d\n" +
                "JOIN COPIES c ON c.DOCID = d.DOCID\n" +
                "JOIN BORROWS b ON d.DOCID = b.DOCID\n" +
                "JOIN BORROWING bg ON bg.BOR_NO = b.BOR_NO\n" +
                "WHERE YEAR(d.PDATE) = ?\n" +
                "GROUP BY d.DOCID, d.TITLE, c.COPYNO, c.BID\n" +
                "ORDER BY BorrowCount DESC\n" +
                "LIMIT 10;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, year);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    documents.add(new DocumentDetail(rs.getString("DOCID"), rs.getString("TITLE"), rs.getString("COPYNO"), rs.getString("BID")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during database query: " + e.getMessage());
            e.printStackTrace();
        }
        return documents;
    }

    public List<BranchFineInfo> getAvgFinePaid(Date startDate, Date endDate) {
        List<BranchFineInfo> branchFineInfoList = new ArrayList<>();
        String sql = "SELECT b.BID, b.LNAME, AVG(bg.FINE) AS average_fine\n" +
                "FROM BRANCHES b\n" +
                "JOIN BORROWS br ON b.BID = br.BID\n" +
                "JOIN BORROWING bg On bg.BOR_NO = br.BOR_NO\n" +
                "WHERE bg.BDTIME >= ? AND bg.RDTIME <= ?\n" +
                "GROUP BY b.BID, b.LNAME\n" +
                "ORDER BY average_fine,b.BID,b.LNAME;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, startDate);
            pstmt.setDate(2, endDate);
            System.out.println(pstmt);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    branchFineInfoList.add(new BranchFineInfo(rs.getString("BID"), rs.getString("LNAME"), rs.getDouble("average_fine")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during database query: " + e.getMessage());
            e.printStackTrace();
        }
        return branchFineInfoList;
    }
}