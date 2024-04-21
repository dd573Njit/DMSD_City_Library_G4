package dao;

import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class BorrowsDAO {

    public void returnBorrowedDocuments(String rId, Date returnDate, float fine) throws SQLException {
        String sql = "UPDATE BORROWING\n" +
                "SET RDTIME = ?, FINE = ?\n" +
                "WHERE BOR_NO = (SELECT DISTINCT BOR_NO from BORROWS\n" +
                "WHERE RID = ?);";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, returnDate);
            pstmt.setFloat(2, fine);
            pstmt.setString(3, rId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
