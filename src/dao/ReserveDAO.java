package dao;

import model.Reservation;
import model.Reserves;
import util.DatabaseConnection;

import java.sql.*;

public class ReserveDAO {
    public int getReservationCount() {
        String sql = "SELECT COUNT(*) AS count FROM RESERVE";
        int count = 0;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving reservation count: " + e.getMessage());
            e.printStackTrace();
        }

        return count;
    }

    public void reserveNumberAndDate(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO RESERVATION (RES_NO, DTIME) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reservation.getResNo());
            pstmt.setTimestamp(2, new Timestamp(reservation.getDTime().getTime()));
            pstmt.executeUpdate();
        }
    }

    public void reserveReaderDetail(Reserves reserves) throws SQLException {
        String sql = "INSERT INTO RESERVES (RID, RESERVATION_NO, DOCID, COPYNO, BID) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, reserves.getRId());
            pstmt.setInt(2, reserves.getReservationNo());
            pstmt.setString(3, reserves.getDocId());
            pstmt.executeUpdate();
        }
    }
}
