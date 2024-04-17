package dao;

import model.Copy;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CopyDAO {
    public List<Copy> getAllCopies(){
        List<Copy> copies = new ArrayList<>();
        String sql = "SELECT * FROM COPY";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                copies.add(new Copy(rs.getString("DOCID"), rs.getString("COPYNO"), rs.getString("BID"), rs.getString("POSITION")));
            }
        }
        catch (SQLException e) {
            System.err.println("Error accessing database" + e.getMessage());
        }
        return copies;
    }

    public void addCopy(Copy copy) throws SQLException {
        String sql = "INSERT INTO COPIES (DOCID, COPYNO, BID, POSITION) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, copy.getDocId());
            pstmt.setString(2, copy.getCopyNo());
            pstmt.setString(3, copy.getBId());
            pstmt.setString(4, copy.getPosition());
            pstmt.executeUpdate();
        }
    }
}
