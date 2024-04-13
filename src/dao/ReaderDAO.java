package dao;

import util.DatabaseConnection;

import java.sql.*;

public class ReaderDAO {
    public boolean isValidReader(String cardNumber) {
        String query = "SELECT RID FROM READERS WHERE RID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, cardNumber);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

