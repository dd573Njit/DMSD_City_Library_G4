package dao;

import model.Publisher;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
