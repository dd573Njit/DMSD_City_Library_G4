package dao;

import model.Reader;
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

    public void addReader(Reader reader) throws SQLException {
        String sql = "INSERT INTO READERS (RID, RTYPE, RNAME, RADDRESS, PHONE_NO) VALUES (?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, reader.getRID());
            pstmt.setString(2, reader.getRType());
            pstmt.setString(3, reader.getRName());
            pstmt.setString(4, reader.getRAddress());
            pstmt.setInt(5, reader.getRPhone_No());
            pstmt.executeUpdate();
        }
    }
}

