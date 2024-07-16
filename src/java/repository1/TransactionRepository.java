package repository1;

import config.DBConnect;
import entity.OrderAccept;
import entity.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
                String brandId = results.getString("BrandID");
                String ctvId = results.getString("CTVID");
                double revenue = results.getDouble("Revenue");
               
                String status = results.getString("Status");
                Transaction transaction = new Transaction(revenueId, brandId, ctvId, revenue, status);
                listTransaction.add(transaction);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi khi lấy danh sách sản phẩm theo CTVID: " + e.getMessage());
        }
        return listTransaction;
    }
  
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM tblRevenue")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getString("RevenueID"),
                        rs.getString("BrandID"),
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

    public static void main(String[] args) {
        TransactionRepository transactionRepository = new TransactionRepository();

        // Kiểm tra phương thức lấy danh sách giao dịch theo CTVID
        String CTVID = "01797"; // giả sử đây là mã của CTV bạn muốn kiểm tra
        ArrayList<Transaction> transactionsByCTVID = transactionRepository.getListTransactionByCTVID(CTVID);
        System.out.println("Danh sách giao dịch theo CTVID:");
        for (Transaction transaction : transactionsByCTVID) {
            System.out.println(transaction);
        }

        // Kiểm tra phương thức lấy tất cả các giao dịch
        List<Transaction> allTransactions = transactionRepository.getAllTransactions();
        System.out.println("Danh sách tất cả các giao dịch:");
        for (Transaction transaction : allTransactions) {
            System.out.println(transaction);
        }

        // Kiểm tra phương thức tính tổng số tiền
        double totalAmount = transactionRepository.getTotalAmount();
        System.out.println("Tổng số tiền của tất cả giao dịch: " + totalAmount);
    }
}
