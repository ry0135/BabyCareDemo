/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository1;

import config.DBConnect;
import entity.Product;
import entity.Revenue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class StatisticsResponsitory {

    public static int getUserAmount() {

        try {
            Connection con = DBConnect.getConnection();
            String query = "SELECT COUNT(1)\n"
                    + "FROM tblAccount\n"
                    + "WHERE Role = '3'";

            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("loi acceptBill() servicerespository");
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    public static int getCTVAmount() {

        try {
            Connection con = DBConnect.getConnection();
            String query = "SELECT COUNT(1)\n"
                    + "FROM tblAccount\n"
                    + "WHERE Role = '4'";

            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("loi acceptBill() servicerespository");
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    public static int getNumberOfOrdersToConfirm() {

        try {
            Connection con = DBConnect.getConnection();
            String query = "select count(1) from tblBill t where  t.StatusBill=N'Đang xử lý-COD' ";

            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("loi acceptBill() servicerespository");
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    public static int getPendingOrdersCountCTV(String CTVID) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            con = DBConnect.getConnection();
            String query = "SELECT COUNT(1) FROM tblBill t WHERE t.StatusBill = N'Đang xử lý-COD' AND t.CTVID = ?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, CTVID);
            resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error in getPendingOrdersCountCTV() method");
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static int getNumberOfProductLeft() {

        try {
            Connection con = DBConnect.getConnection();
            String query = "select Count(1) from tblProduct where StatusProduct=1";

            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("loi acceptBill() servicerespository");
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    public static double getOrderRevenue() {
        double totalRevenue = 0;

        try {
            Connection con = DBConnect.getConnection();
            String query = "SELECT COALESCE(SUM(od.PriceAtPuchase), 0)  AS ten_percent_total_price\n"
                    + "FROM [tblOrderDetails] od\n"
                    + "JOIN [tblBill] b ON b.BillID = od.BillID\n"
                    + "LEFT JOIN [tblPreferential] p ON b.PreferentialID = p.Preferential\n"
                    + "WHERE (b.StatusBill = N'Đã hoàn thành' OR b.StatusBill = N'Đã đánh giá')\n"
                    + "AND YEAR(b.DateCreate) = 2024";

            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                totalRevenue = resultSet.getDouble("ten_percent_total_price");
            }
        } catch (Exception e) {
            System.out.println("Error in getOrderRevenue method");
            e.printStackTrace();
        }

        return totalRevenue;
    }
    public static double getOrderRevenueByYear(int year) {
    double totalRevenue = 0;

    try {
        Connection con = DBConnect.getConnection();
        String query = "SELECT COALESCE(SUM(od.PriceAtPuchase), 0) AS ten_percent_total_price\n"
                + "FROM [tblOrderDetails] od\n"
                + "JOIN [tblBill] b ON b.BillID = od.BillID\n"
                + "LEFT JOIN [tblPreferential] p ON b.PreferentialID = p.Preferential\n"
                + "WHERE (b.StatusBill = N'Đã hoàn thành' OR b.StatusBill = N'Đã đánh giá')\n"
                + "AND YEAR(b.DateCreate) = ?";

        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, year); // Thiết lập tham số năm vào câu truy vấn
        ResultSet resultSet = stmt.executeQuery();
        if (resultSet.next()) {
            totalRevenue = resultSet.getDouble("ten_percent_total_price");
        }
    } catch (Exception e) {
        System.out.println("Error in getOrderRevenue method");
        e.printStackTrace();
    }

    return totalRevenue;
}


    public static ArrayList<Product> getListRankOfProduct() {
        ArrayList<Product> listProduct = null;
        try {
            Connection con = DBConnect.getConnection();
            String query = "SELECT \n"
                    + "    x.ProductID,\n"
                    + "    p.ProductName,\n"
                    + "    x.AmountProduct\n"
                    + "FROM (\n"
                    + "    SELECT \n"
                    + "        o.ProductID,\n"
                    + "        SUM(o.AmountProduct) AS AmountProduct  \n"
                    + "    FROM \n"
                    + "        tblBill b\n"
                    + "    JOIN \n"
                    + "        tblOrderDetails o ON o.BillID = b.BillID\n"
                    + "    WHERE \n"
                    + "        (b.StatusBill = N'Đã hoàn thành' \n"
                    + "        OR b.StatusBill = N'Đã đánh giá')\n"
                    + "        AND YEAR(b.DateCreate) = 2024\n"
                    + "    GROUP BY \n"
                    + "        o.ProductID\n"
                    + ") AS x\n"
                    + "JOIN \n"
                    + "    tblProduct p ON x.ProductID = p.ProductID\n"
                    + "ORDER BY \n"
                    + "    x.AmountProduct DESC;";

            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            listProduct = new ArrayList<>();
            while (resultSet.next()) {
                String ProductId = resultSet.getString(1);
                String ProductName = resultSet.getString(2);
                int ProductAmount = resultSet.getInt(3);
                Product product = new Product();
                product.setProductId(ProductId);
                product.setProductName(ProductName);
                product.setProductAmount(ProductAmount);
                listProduct.add(product);
            }

        } catch (Exception e) {
            System.out.println("Error in getListRankOfProduct()");
            e.printStackTrace();
            return null;
        }
        return listProduct;
    }

    public static ArrayList<Product> getListRankOfProductByCTVID(String CTVID) {
        ArrayList<Product> listProduct = null;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            con = DBConnect.getConnection();
            String query = "SELECT \n"
                    + "    x.ProductID,\n"
                    + "    p.ProductName,\n"
                    + "    x.AmountProduct\n"
                    + "FROM (\n"
                    + "    SELECT \n"
                    + "        o.ProductID,\n"
                    + "        SUM(o.AmountProduct) AS AmountProduct  \n"
                    + "    FROM \n"
                    + "        tblBill b\n"
                    + "    JOIN \n"
                    + "        tblOrderDetails o ON o.BillID = b.BillID\n"
                    + "    WHERE \n"
                    + "        (b.StatusBill = N'Đã hoàn thành' \n"
                    + "        OR b.StatusBill = N'Đã đánh giá')\n"
                    + "        AND YEAR(b.DateCreate) = 2024\n"
                    + "        AND b.CTVID = ?\n" // Added condition for CTVID
                    + "    GROUP BY \n"
                    + "        o.ProductID\n"
                    + ") AS x\n"
                    + "JOIN \n"
                    + "    tblProduct p ON x.ProductID = p.ProductID\n"
                    + "ORDER BY \n"
                    + "    x.AmountProduct DESC;";

            stmt = con.prepareStatement(query);
            stmt.setString(1, CTVID);
            resultSet = stmt.executeQuery();

            listProduct = new ArrayList<>();
            while (resultSet.next()) {
                String ProductId = resultSet.getString(1);
                String ProductName = resultSet.getString(2);
                int ProductAmount = resultSet.getInt(3);
                Product product = new Product();
                product.setProductId(ProductId);
                product.setProductName(ProductName);
                product.setProductAmount(ProductAmount);
                listProduct.add(product);
            }

        } catch (Exception e) {
            System.out.println("Error in getListRankOfProduct()");
            e.printStackTrace();
            return null;
        } finally {
            // Clean up resources
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("SQLException while closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return listProduct;
    }

//public static ArrayList<Revenue> getOrderRevenueEachMonths() {
//    ArrayList<Revenue> listRevenues = new ArrayList<>();
//    for (int i = 1; i <= 12; i++) {
//        listRevenues.add(new Revenue(i + "", 0));
//    }
//
//    String query = "SELECT *\n"
//            + "FROM INFORMATION_SCHEMA.COLUMNS\n"
//            + "WHERE TABLE_NAME = 'tblPreferential';\n"
//            + "DECLARE @Month INT;\n"
//            + "SET @Month = 6; \n"
//            + "\n"
//            + "SELECT SUM(od.PriceAtPuchase)\n"
//            + "FROM tblOrderDetails od\n"
//            + "JOIN tblBill b ON b.BillID = od.BillID\n"
//            + "WHERE b.StatusBill = N'Đã thanh toán'\n"
//            + "  AND YEAR(b.DateCreate) = 2024\n"
//            + "  AND MONTH(b.DateCreate) = @Month;";
//
//    for (Revenue r : listRevenues) {
//        try (Connection con = DBConnect.getConnection();
//             PreparedStatement stmt = con.prepareStatement(query)) {
//
//            stmt.setString(1, r.getMonth());
//            try (ResultSet resultSet = stmt.executeQuery()) {
//                if (resultSet.next()) {
//                    r.setMoney(resultSet.getDouble(1));
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("Error in getOrderRevenueEachMonths() for month: " + r.getMonth());
//            e.printStackTrace();
//        }
//    }
//
//    return listRevenues;
//}
    public static double getOrderRevenuebyCTV(String ctvId) {
        double totalRevenue = 0;
        try {
            Connection con = DBConnect.getConnection();
            String query = "SELECT SUM(PriceAtPuchase) * 0.9 AS ten_percent_total_price "
                    + "FROM [tblOrderDetails] od "
                    + "JOIN [tblBill] b ON b.BillID = od.BillID "
                    + "LEFT JOIN [tblPreferential] p ON b.PreferentialID = p.Preferential "
                    + "WHERE (b.StatusBill = N'Đã hoàn thành' OR b.StatusBill = N'Đã đánh giá') "
                    + "AND b.CTVID = ? "
                    + "AND YEAR(b.DateCreate) = 2024";

            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, ctvId);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                totalRevenue = resultSet.getDouble(1);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error in getOrderRevenuebyCTV method");
            e.printStackTrace();
        }
        return totalRevenue;
    }

    public static double getMonthlyRevenueByCTV(String ctvId, int month, int year) {
        double totalRevenue = 0;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            con = DBConnect.getConnection();
            String query = "SELECT SUM(od.PriceAtPuchase) * 0.9 AS Revenue "
                    + "FROM tblOrderDetails od "
                    + "JOIN tblBill b ON b.BillID = od.BillID "
                    + "WHERE (b.StatusBill = N'Đã hoàn thành' OR b.StatusBill = N'Đã đánh giá') "
                    + "AND b.CTVID = ? "
                    + "AND MONTH(b.DateCreate) = ? "
                    + "AND YEAR(b.DateCreate) = ? ";

            stmt = con.prepareStatement(query);
            stmt.setString(1, ctvId);
            stmt.setInt(2, month);
            stmt.setInt(3, year);

            resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                totalRevenue = resultSet.getDouble("Revenue");
            }
        } catch (Exception e) {
            System.out.println("Error in getMonthlyRevenueByCTV method: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error in closing database resources: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return totalRevenue;
    }
    public static double getMonthlyRevenueByAdmin(int month, int year) {
        double totalRevenue = 0;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            con = DBConnect.getConnection();
            String query = "SELECT SUM(od.PriceAtPuchase) AS Revenue "
                    + "FROM tblOrderDetails od "
                    + "JOIN tblBill b ON b.BillID = od.BillID "
                    + "WHERE (b.StatusBill = N'Đã hoàn thành' OR b.StatusBill = N'Đã đánh giá') "
                    + "AND MONTH(b.DateCreate) = ? "
                    + "AND YEAR(b.DateCreate) = ? ";

            stmt = con.prepareStatement(query);
    
            stmt.setInt(1, month);
            stmt.setInt(2, year);

            resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                totalRevenue = resultSet.getDouble("Revenue");
            }
        } catch (Exception e) {
            System.out.println("Error in getMonthlyRevenueByCTV method: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error in closing database resources: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return totalRevenue;
    }

    public static boolean checkRevenueExists(String CTVID, int month, int year) throws ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            conn = DBConnect.getConnection();
            String sql = "SELECT 1 FROM tblRevenue WHERE CTVID = ? AND Month = ? AND Year = ? AND Status = 0";
            ps = conn.prepareStatement(sql);
            ps.setString(1, CTVID);
            ps.setInt(2, month);
            ps.setInt(3, year);
            rs = ps.executeQuery();
            exists = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return exists;
    }

    public static int getStatusRevenue(String CTVID, int month, int year) throws ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int status = -1;

        try {
            conn = DBConnect.getConnection();
            String sql = "SELECT Status FROM tblRevenue WHERE CTVID = ? AND Month = ? AND Year = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, CTVID);
            ps.setInt(2, month);
            ps.setInt(3, year);
            rs = ps.executeQuery();
            if (rs.next()) {
                status = rs.getInt("Status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return status;
    }

    public static boolean insertRevenue(String brandID, String ctvID, double revenue, int month, int year) throws ClassNotFoundException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnect.getConnection();
            String sql = "INSERT INTO tblRevenue (BrandID, CTVID, Revenue, Month, Year, Status) VALUES (?, ?, ?, ?, ?, 1)";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, brandID);
            pstmt.setString(2, ctvID);
            pstmt.setDouble(3, revenue);
            pstmt.setInt(4, month);
            pstmt.setInt(5, year);

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static double getMonthlyRevenueByCTV( int month, int year, String ctvid) {
        double totalRevenue = 0;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            con = DBConnect.getConnection();
             String query = "SELECT SUM(od.PriceAtPuchase)*0.9 AS Revenue "
                    + "FROM tblOrderDetails od "
                    + "JOIN tblBill b ON b.BillID = od.BillID "
                    + "WHERE (b.StatusBill = N'Đã hoàn thành' OR b.StatusBill = N'Đã đánh giá') "
                    + "AND b.CTVID = ?\n"
                    + "AND MONTH(b.DateCreate) = ? "
                    + "AND YEAR(b.DateCreate) = ? ";

            stmt = con.prepareStatement(query);
            stmt.setString(1, ctvid);
            stmt.setInt(2, month);
            stmt.setInt(3, year);
            

            resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                totalRevenue = resultSet.getDouble("Revenue");
            }
        } catch (Exception e) {
            System.out.println("Error in getMonthlyRevenueByAdmin method: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error in closing database resources: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return totalRevenue;
    }
    public static double getOrderRevenueByYearCTV(int year, String ctvId) {
    double totalRevenue = 0;

    try {
        Connection con = DBConnect.getConnection();
        String query = "SELECT COALESCE(SUM(od.PriceAtPuchase)*0.9, 0) AS ten_percent_total_price\n"
                    + "FROM [tblOrderDetails] od\n"
                    + "JOIN [tblBill] b ON b.BillID = od.BillID\n"
                    + "LEFT JOIN [tblPreferential] p ON b.PreferentialID = p.Preferential\n"
                    + "WHERE (b.StatusBill = N'Đã hoàn thành' OR b.StatusBill = N'Đã đánh giá')\n"
                    + "AND b.CTVID = ?\n"
                    + "AND YEAR(b.DateCreate) = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, ctvId);
        stmt.setInt(2, year);// Thiết lập tham số năm vào câu truy vấn
        ResultSet resultSet = stmt.executeQuery();
        if (resultSet.next()) {
            totalRevenue = resultSet.getDouble("ten_percent_total_price");
        }
    } catch (Exception e) {
        System.out.println("Error in getOrderRevenue method");
        e.printStackTrace();
    }

    return totalRevenue;
}
    public static void main(String[] args) {
//                 double totalRevenue = getOrderRevenue();
//        System.out.println("Total Revenue for paid orders in 2024: " + totalRevenue);
////                int CTVAmount = getCTVAmount();
////        System.out.println("Number of users with role '4': " + CTVAmount);

//        String CTVID = "01797";
//
//    // Call the method getListRankOfProduct to get the list of ranked products
//    ArrayList<Product> rankedProducts = getListRankOfProductByCTVID(CTVID);
//
//    if (rankedProducts != null) {
//        System.out.println("Ranked Products for CTVID " + CTVID + ":");
//        for (Product product : rankedProducts) {
//            System.out.println("Product ID: " + product.getProductId() + ", Product Name: " + product.getProductName() +
//                               ", Amount Sold: " + product.getProductAmount());
//        }
//    } else {
//        System.out.println("Failed to retrieve ranked products.");
//    }
       
        int year = 2024;

        double monthlyRevenue = getOrderRevenueByYear( year);
        System.out.println( " in " +  "Monthly revenue for CTV ID "  + "/" + year + ": " + monthlyRevenue);
    }

}
