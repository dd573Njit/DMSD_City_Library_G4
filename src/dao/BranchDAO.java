package dao;

import model.Branch;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BranchDAO {
    public List<Branch> getAllBranches() {
        List<Branch> branches = new ArrayList<>();
        String sql = "SELECT * FROM BRANCHES";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                branches.add(new Branch(rs.getString("BID"), rs.getString("LNAME"), rs.getString("LOCATION")));
            }
        } catch (SQLException e) {
            System.err.println("Error accessing database: " + e.getMessage());
        }
        return branches;
    }

    public List<String> getBranchNumbers() {
        List<String> branchNumbers = new ArrayList<>();
        String sql = "SELECT BID FROM BRANCHES";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                branchNumbers.add(rs.getString("BID"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching branch numbers: " + e.getMessage());
        }
        return branchNumbers;
    }
}