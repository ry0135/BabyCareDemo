package repository1;

import config.DBConnect;
import static config.DBConnect.getConnection;
import entity.OrderAccept;
import entity.Transaction;
import entity.WithDraw;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import static repository1.UserRepository.isValidEmail;

public class TransactionRepository {

    public static ArrayList<Transaction> getListTransactionByCTVID(String CTVID) {
        ArrayList<Transaction> listTransaction = new ArrayList<>();
        try {
            String query = "SELECT * FROM tblRevenue WHERE CTVID = ?";
            Connection con = DBConnect.getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, CTVID);
            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                String revenueId = results.getString("RevenueID");

                String ctvId = results.getString("CTVID");
                double revenue = results.getDouble("Revenue");
                String status = results.getString("Status");
                Transaction transaction = new Transaction(revenueId, ctvId, revenue, status);
                listTransaction.add(transaction);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi khi lấy danh sách sản phẩm theo CTVID: " + e.getMessage());
        }
        return listTransaction;
    }

    public static double getRevenueByCTVID(String CTVID) throws ClassNotFoundException {
        double totalRevenue = 0;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet results = null;

        try {
            String query = "SELECT SUM(Revenue) as TotalRevenue FROM tblRevenue WHERE CTVID = ?";
            con = DBConnect.getConnection();
            stmt = con.prepareStatement(query);
            stmt.setString(1, CTVID);
            results = stmt.executeQuery();

            if (results.next()) {
                totalRevenue = results.getDouble("TotalRevenue");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi khi lấy doanh thu theo CTVID: " + e.getMessage());
        } finally {
            try {
                if (results != null) {
                    results.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return totalRevenue;
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM tblRevenue")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getString("RevenueID"),
                        rs.getString("CTVID"),
                        rs.getDouble("Revenue"),
                        rs.getString("Status")
                );
                transactions.add(transaction);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public double getTotalAmount() {
        double total = 0;
        try (Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT SUM(Revenue) AS TotalRevenue FROM tblRevenue")) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("TotalRevenue");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }
    private static final Logger logger = Logger.getLogger(TransactionRepository.class.getName());

    public static boolean addWithdraw(String brandID, double amount) throws SQLException, ClassNotFoundException {
        boolean result = false;
        String sql = "INSERT INTO tblWithdraw (RequestDate, BrandID, Amount, Status) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            ps.setString(2, brandID);
            ps.setDouble(3, amount);
            ps.setInt(4, 0);

            logger.info("Executing query: " + ps.toString());

            result = ps.executeUpdate() > 0;
            if (result) {
                logger.info("Withdraw record added successfully.");
            } else {
                logger.warning("Failed to add withdraw record.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error adding withdraw record", e);
            throw e;
        }
        return result;
    }
    
     public static List<WithDraw> listWithdraw(String BrandID) throws SQLException, ClassNotFoundException {
    List<WithDraw> withdraws = new ArrayList<>();
    String sql = "SELECT * FROM tblWithdraw WHERE BrandID = ?";  // Ensure the table name is correct

    try (Connection con = DBConnect.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, BrandID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            WithDraw withdraw = new WithDraw(
                rs.getInt("WithdrawID"),      // Ensure the column names match the table schema
                rs.getDate("RequestDate"),
                rs.getString("BrandID"),
                rs.getDouble("Amount"),
                rs.getInt("Status")
            );
            withdraws.add(withdraw);  // Add the withdraw object to the list
        }
    } catch (SQLException e) {
        // Log the exception for better debugging and monitoring
        System.err.println("SQL exception occurred while retrieving withdrawals: " + e.getMessage());
        throw e;  // Rethrow the exception to be handled by the caller
    }
    return withdraws;
}
     
      public static List<WithDraw> getWithdraws() throws SQLException, ClassNotFoundException {
        List<WithDraw> withdraws = new ArrayList<>();
        String query = "SELECT w.WithdrawID, w.RequestDate, w.BrandID, w.Amount, w.Status, " +
                       "b.BrandName,  b.BrandLogo, b.BrandAddress, b.BankName, b.AcountNumber " +
                       "FROM tblWithdraw w " +
                       "JOIN tblBrand b ON w.BrandID = b.BrandID ";
                     

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                WithDraw withdraw = new WithDraw();
                withdraw.setWithdrawID(rs.getInt("WithdrawID"));
                withdraw.setRequestDate(rs.getDate("RequestDate"));
                withdraw.setBrandID(rs.getString("BrandID"));
                withdraw.setAmount(rs.getDouble("Amount"));
                withdraw.setStatus(rs.getInt("Status"));
                withdraw.setBrandName(rs.getString("BrandName"));       
                withdraw.setBankName(rs.getString("BankName"));
                withdraw.setAccountNumber(rs.getString("AcountNumber"));
                withdraws.add(withdraw);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Hoặc xử lý ngoại lệ theo cách khác
        }
        return withdraws;
    }
      
      public static boolean updatewithdrawStatus(int withDrawID) {
        String query = "UPDATE tblWithdraw SET Status = 1 WHERE WithdrawID = ?";
        int rowsAffected = 0;

        try (Connection con = DBConnect.getConnection();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, withDrawID);
            rowsAffected = stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("SQLException in updateRefundStatus: " + e.getMessage());
            e.printStackTrace(); // Optional: This provides more details about the exception.
            return false;
        } catch (Exception e) {
            System.err.println("Exception in updateRefundStatus: " + e.getMessage());
            e.printStackTrace(); // Optional: This provides more details about the exception.
            return false;
        }

        return rowsAffected > 0;
    }
      
      public static String getEmailByBrandID(String brandId) {
    // Thay đổi truy vấn SQL dựa trên cấu trúc bảng và mối quan hệ giữa các bảng
    String query = "SELECT a.Email "
                 + "FROM tblBrand b "
                 + "JOIN tblAccount a ON b.CTVID = a.UserID " // Thay đổi liên kết dựa trên cấu trúc bảng của bạn
                 + "WHERE b.BrandID = ?";
    String email = null;

    try (Connection con = DBConnect.getConnection();
         PreparedStatement ps = con.prepareStatement(query)) {
        ps.setString(1, brandId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                email = rs.getString("Email");
            }
        }
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
    }

    return email;
}
 public static void sendCodeToEmailWithDraw(String email, double amount) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email address: " + email);
        }

        final String username = "phuongnam121103@gmail.com"; // Thay bằng email của bạn
        final String password = "eqna xeml exop mnzc"; // Thay bằng mật khẩu email của bạn

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Yêu cầu rút tiền thành công");

            String htmlContent = "<html><body>"
                    + "<p>Xin chào,</p>"
                    + "<p>Yêu cầu rút tiền của bạn đã được xử lý thành công.</p>"
                    + "<p>Số tiền: " + amount + " VND</p>"
                    + "<p>Trân trọng,<br>BabyCare Company</p>"
                    + "</body></html>";

            message.setContent(htmlContent, "text/html; charset=UTF-8");

            Transport.send(message);
            System.out.println("Code sent successfully.");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isValidEmail(String email) {
        // Add your email validation logic here
        return email != null && email.contains("@") && email.contains(".");
    }

   

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        TransactionRepository transactionRepository = new TransactionRepository();
//
//        // Kiểm tra phương thức lấy danh sách giao dịch theo CTVID
//        String CTVID = "01797"; // giả sử đây là mã của CTV bạn muốn kiểm tra
//        ArrayList<Transaction> transactionsByCTVID = transactionRepository.getListTransactionByCTVID(CTVID);
//        System.out.println("Danh sách giao dịch theo CTVID:");
//        for (Transaction transaction : transactionsByCTVID) {
//            System.out.println(transaction.getRevenue());
//        }

//        // Kiểm tra phương thức lấy tất cả các giao dịch
//        List<Transaction> allTransactions = transactionRepository.getAllTransactions();
//        System.out.println("Danh sách tất cả các giao dịch:");
//        for (Transaction transaction : allTransactions) {
//            System.out.println(transaction);
//        }
//
//        // Kiểm tra phương thức tính tổng số tiền
//        double totalAmount = transactionRepository.getTotalAmount();
//        System.out.println("Tổng số tiền của tất cả giao dịch: " + totalAmount);
//            TransactionRepository withdrawDAO = new TransactionRepository();
        
//            String brandid = "B5681";
//            List<WithDraw> withdrawList = transactionRepository.getWithdraws();
//            
//            // Print the results
//            for (WithDraw withdraw : withdrawList) {
//                System.out.println(withdraw);
//            }
        String email = transactionRepository.getEmailByBrandID("B5681");
        System.out.println(email);
    }
    
}
