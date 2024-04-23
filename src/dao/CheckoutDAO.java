package dao;

import model.Borrowing;
import model.Borrows;
import model.DocumentDetail;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CheckoutDAO {
    public int getBorrowingCount() {
        String sql = "SELECT COUNT(*) AS count FROM BORROWING";
        int count = 0;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery(); {

                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        }catch (SQLException e) {
            System.err.println("Error retrieving reservation count: " + e.getMessage());
            e.printStackTrace();
        }

        return count;
    }

    public void addBorrowingDate(Borrowing borrowing) throws SQLException {
        String sql = "INSERT INTO BORROWING (BOR_NO, BDTIME,RDTIME) VALUES (?, ?, null)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, borrowing.getBorNo());
            pstmt.setTimestamp(2, new Timestamp(borrowing.getBDTime().getTime()));
            pstmt.executeUpdate();
        }
    }

    public void addBorrowsDetail(Borrows borrows) throws SQLException {
        String sql = "INSERT INTO BORROWS (BOR_NO, DOCID, COPYNO, BID, RID) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, borrows.getBorNo());
            pstmt.setString(2, borrows.getDocId());
            pstmt.setString(3, borrows.getCopyNo());
            pstmt.setString(4, borrows.getBId());
            pstmt.setString(5, borrows.getRId());
            pstmt.executeUpdate();
        }
    }

    public List<DocumentDetail> getBorrowedDocId(String rId) throws SQLException {
        String sql = "SELECT d.DOCID, d.TITLE, r.COPYNO, r.BID FROM BORROWS r JOIN DOCUMENTS d ON d.DOCID = r.DOCID WHERE RID = ?";
        List<DocumentDetail> documents = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, rId); // Set the parameter before executing the query
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                documents.add(new DocumentDetail(rs.getString("DOCID"), rs.getString("TITLE"), rs.getString("COPYNO"), rs.getString("BID")));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving reservation count: " + e.getMessage());
            e.printStackTrace();
        }
        return documents;
    }
}
