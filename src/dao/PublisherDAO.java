package dao;

import model.Publisher;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PublisherDAO {

    public void addPublisher(Publisher publisher) throws SQLException {
        String sql = "INSERT INTO PUBLISHERS (PUBLISHERID, PUBNAME, ADDRESS) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, publisher.getPublisherId());
            pstmt.setString(2, publisher.getPubName());
            pstmt.setString(3, publisher.getAddress());
            pstmt.executeUpdate();
        }
    }

    public List<Publisher> getAllPublishers() throws SQLException {
        List<Publisher> publishers = new ArrayList<>();
        String sql = "SELECT * FROM PUBLISHERS";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                publishers.add(new Publisher(rs.getString("PUBLISHERID"), rs.getString("PUBNAME"), rs.getString("ADDRESS")));
            }
        } catch (SQLException e) {
            System.err.println("Error accessing database: " + e.getMessage());
        }
        return publishers;
    }
}
