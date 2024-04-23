package dao;

import model.DocumentDetail;
import model.Reservation;
import model.Reserves;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReserveDAO {
    public int getReservationCount() {
        String sql = "SELECT COUNT(*) AS count FROM RESERVATION;";
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

    public void reserveNumberAndDate(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO RESERVATION (RES_NO, DTIME) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, reservation.getResNo());
            pstmt.setTimestamp(2, new Timestamp(reservation.getDTime().getTime()));
            pstmt.executeUpdate();
        }
    }

    public void reserveReaderDetail(Reserves reserves) throws SQLException {
        String sql = "INSERT INTO RESERVES (RID, RESERVATION_NO, DOCID, COPYNO, BID) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, reserves.getRId());
            pstmt.setString(2, reserves.getReservationNo());
            pstmt.setString(3, reserves.getDocId());
            pstmt.setString(4, reserves.getCopyNo());
            pstmt.setString(5, reserves.getBId());
            pstmt.executeUpdate();
        }
    }

    public List<DocumentDetail> getReservedDocId(String rId) throws SQLException {
        String sql = "SELECT d.DOCID, d.TITLE, r.COPYNO, r.BID FROM RESERVES r JOIN DOCUMENTS d ON d.DOCID = r.DOCID WHERE RID = ?";
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

    public void removeReservedDocs(String rId) throws SQLException {
        String sql = "DELETE FROM RESERVES WHERE RID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, rId);
            int affectedRows = pstmt.executeUpdate();
            System.out.println("Deleted " + affectedRows + " rows.");
        } catch (SQLException e) {
            System.err.println("Error deleting reserved documents: " + e.getMessage());
            throw e;
        }
    }

}
