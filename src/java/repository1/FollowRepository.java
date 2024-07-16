package repository1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import config.DBConnect;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FollowRepository {

    /**
     * Adds a follow record to the database.
     * 
     * @param userID the user ID of the follower
     * @param brandID the brand ID that is being followed
     * @throws SQLException if there is any issue when executing the SQL
     */
    public static void addFollow(String userID, String brandID) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO tblUserFollows (UserID, BrandID) VALUES (?, ?)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userID);
            pstmt.setString(2, brandID);
            pstmt.executeUpdate();
        }
    }
     public static boolean followExists(String userID, String brandID) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM tblUserFollows WHERE UserID = ? AND BrandID = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userID);
            pstmt.setString(2, brandID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
       public static void removeFollow(String userID, String brandID) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM tblUserFollows WHERE UserID = ? AND BrandID = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userID);
            pstmt.setString(2, brandID);
            pstmt.executeUpdate();
        }
    }  public static boolean isFollowing(String userID, String brandID) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM tblUserFollows WHERE UserID = ? AND BrandID = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userID);
            pstmt.setString(2, brandID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
public static List<String> getEmailsByBrandID(String brandID) throws SQLException, ClassNotFoundException {
        List<String> emails = new ArrayList<>();
        String sql = "SELECT Email FROM tblAccount WHERE UserID IN (SELECT UserID FROM tblUserFollows WHERE BrandID = ?)";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, brandID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                emails.add(rs.getString("Email"));
            }
        }
        return emails;
    }
    public static void main(String[] args) {
        try {
            String userID = "qr123";  // Giả sử userID này đã tồn tại trong cơ sở dữ liệu
            String brandID = "nd456";      // Giả sử brandID này cũng đã tồn tại trong cơ sở dữ liệu

            // Thử thêm bản ghi theo dõi vào cơ sở dữ liệu
            FollowRepository.addFollow(userID, brandID);
            System.out.println("Follow added successfully!");
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
