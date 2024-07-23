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
    }

    public static boolean isFollowing(String userID, String brandID) throws SQLException, ClassNotFoundException {
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

    public static String getCTVIDByBrandID(String brandID) throws SQLException, ClassNotFoundException {
        String sql = "SELECT CTVID FROM tblBrand WHERE BrandID = ?";
        String CTVID = null;

        try (Connection connection = DBConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, brandID);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    CTVID = rs.getString("CTVID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return CTVID;
    }

    public static List<String> getUserIDsByBrandID(String brandID) throws SQLException, ClassNotFoundException {
        List<String> userIDs = new ArrayList<>();
        String sql = "SELECT UserID FROM tblUserFollows WHERE BrandID = ?";

        try (Connection conn = DBConnect.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, brandID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                userIDs.add(rs.getString("UserID"));
            }
        }
        return userIDs;
    }
    public static List<String> getEmailsByUserIDs(List<String> userIDs) throws SQLException, ClassNotFoundException {
    List<String> emails = new ArrayList<>();
    String sql = "SELECT Email FROM tblAccount WHERE UserID = ?";

    try (Connection conn = DBConnect.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        for (String userID : userIDs) {
            pstmt.setString(1, userID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    emails.add(rs.getString("Email"));
                }
            }
        }
    }
    return emails;
}
public static List<String> getEmailsByBrandID(String brandID) throws SQLException, ClassNotFoundException {
    // Lấy CTVID từ BrandID
    String CTVID = getCTVIDByBrandID(brandID);
    if (CTVID == null) {
        return new ArrayList<>(); // Hoặc xử lý khi không tìm thấy CTVID
    }

    // Lấy danh sách UserID từ BrandID
    List<String> userIDs = getUserIDsByBrandID(brandID);
    if (userIDs.isEmpty()) {
        return new ArrayList<>(); // Hoặc xử lý khi không có UserID
    }

    // Lấy danh sách email từ danh sách UserID
    return getEmailsByUserIDs(userIDs);
}


    public static List<String> getEmailsByCTVID(String CTVID) throws SQLException, ClassNotFoundException {
        List<String> emails = new ArrayList<>();
        String sql = "SELECT Email FROM tblAccount WHERE UserID = ?";
        try (Connection conn = DBConnect.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, CTVID);
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
