package repository1;

import config.DBConnect;

import entity.Service;
import entity.ServiceBill;
import entity.ServiceBooked;

import entity.Customer;
import entity.Booking;
import entity.CustomerRefund;
import entity.Feedback;
import entity.MonthlyRevenue;
import entity.ServiceType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ServiceRespository {

    private static ResultSet rs;

    public static ArrayList<Service> getALLService() {
        ArrayList<Service> listService = new ArrayList<>();
        try {
            String query = "SELECT * FROM tblService";
            Connection con = DBConnect.getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                int serviceId = results.getInt(1);
                String serviceName = results.getString(2);
                double servicePrice = results.getDouble(3);
                String serviceDescription = results.getString(4);
                String listImage = results.getString(5);

                Service service = new Service(serviceId, serviceName, servicePrice, listImage, serviceDescription);
                listService.add(service);
            }
        } catch (Exception e) {
            System.err.println("Error in database method getALLService");
            e.printStackTrace(); // Optional: This provides more details about the exception.
        }
        return listService;
    }

    public static Service getService(String id) {
        try {
            String query = "SELECT * FROM tblService  where ServiceID =?";
            Connection con = DBConnect.getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, id);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                int serviceId = results.getInt(1);
                String serviceName = results.getString(2);
                double servicePrice = results.getDouble(3);
                String serviceDescription = results.getString(4);
                String listImage = results.getString(5);

                Service service = new Service(serviceId, serviceName, servicePrice, listImage, serviceDescription);
                return service;
            }
        } catch (Exception e) {
            System.out.println("Loi method GetService(id) trong ProductRepository.java ");
            e.printStackTrace();
        }
        return null;
    }

    public static String getServiceName(String serviceID) {
        String serviceName = null;
        try {
            Connection con = DBConnect.getConnection();
            String query = " select ServiceName from tblService where ServiceID=?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, serviceID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                serviceName = rs.getString(1);
            }

        } catch (Exception e) {
            System.out.println("loi getServiceName() servicerespository");
            e.printStackTrace();
        }
        return serviceName;
    }

    public static int getHandleNumberOfSlot(String serviceID, String shiftID, String setDay) {
        int handleNum = 0;
        try {
            Connection con = DBConnect.getConnection();
            String query = "select NumberOfResponses from tblCalendar where ShiftID=? and ServiceID=? and SetDay=?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, shiftID);
            stmt.setString(2, serviceID);
            stmt.setString(3, setDay);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                handleNum = rs.getInt(1);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("===========>Loi getHandleNumberOfSlot trong ServiceRepository");
            e.printStackTrace();
        }
        return handleNum;
    }

    public static int getBookedNumberOfSlot(String serviceID, String shiftID, String setDay) {
        int bookedNum = 0;
        try {
            Connection con = DBConnect.getConnection();
            String query = "select ShiftID,ServiceID,SetDay,SUM(Amount) as Amount from tblServiceBill\n"
                    + "    where StatusBill='1' or StatusBill = '2'\n"
                    + "     group by ShiftID,ServiceID,SetDay \n"
                    + "  having  (ShiftID=? and ServiceID=? and SetDay=?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, shiftID);
            stmt.setString(2, serviceID);
            stmt.setString(3, setDay);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookedNum = rs.getInt(4);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("===========>Loi getBookedNumberOfSlot trong ServiceRepository");
            e.printStackTrace();
        }
        return bookedNum;
    }

    public static boolean createCheckout(String billID, String customerID, String dateCreate, String serviceID, String shiftID, String setDay, int ammount, String priceAtPurchase) {

        try {
            Connection con = DBConnect.getConnection();
            String query = "insert into  tblServiceBill(BillID, CustomerID,DateCreate,ServiceID,ShiftID,SetDay,StatusBill,Amount,PriceAtPurchase) values (?,?,?,?,?,?,?,?,?)";

//            String query = "values\n" +
//                    "(?,NULL,?,?,'SH001','S0001','2023-06-13',0,12)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, billID);
            stmt.setString(2, customerID);
            stmt.setString(3, dateCreate);
            stmt.setString(4, serviceID);
            stmt.setString(5, shiftID);
            stmt.setString(6, setDay);
            stmt.setInt(7, 0);
            stmt.setInt(8, ammount);

            stmt.setString(9, String.valueOf(Double.parseDouble(priceAtPurchase) * ammount));
            stmt.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println("===========>Loi createCheckout trong ServiceRepository");
            e.printStackTrace();
            return false;
        }
        return true;
    }

   
    

    public int insertBooking(Booking booking) {
        int bookingID = -1; // Default value to indicate failure
        try {
            // Establishing the connection
            Connection con = DBConnect.getConnection();

            // SQL statement with generated keys retrieval
            String sql = "INSERT INTO tblBooking (CustomerID, ServiceID, Name, PhoneNumber, Address, Sex, BookingDate, Slot, BookingStatus, Note, Price,Email,ServiceName) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Setting parameters
            pstmt.setString(1, booking.getCustomerID());
            pstmt.setString(2, booking.getServiceID()); // Assuming ServiceID is a String
            pstmt.setNString(3, booking.getName()); // Use setNString for NVARCHAR
            pstmt.setString(4, booking.getPhoneNumber());
            pstmt.setNString(5, booking.getAddress()); // Use setNString for NVARCHAR
            pstmt.setNString(6, booking.getSex()); // Use setNString for NVARCHAR
            pstmt.setString(7, booking.getBookingDate()); // Assuming BookingDate is a String in 'yyyy-MM-dd' format
            pstmt.setNString(8, booking.getSlot()); // Use setNString for NVARCHAR
            pstmt.setInt(9, booking.getBookingStatus());
            pstmt.setNString(10, booking.getNote()); // Use setNString for NVARCHAR
            pstmt.setDouble(11, booking.getPrice());
            pstmt.setNString(12, booking.getEmail());
            pstmt.setNString(13, booking.getServiceName());
            // Executing the update
            pstmt.executeUpdate();

            // Retrieving the generated booking ID
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                bookingID = rs.getInt(1);
            }

            // Closing resources
            rs.close();
            pstmt.close();
            con.close();

            System.out.println("Booking inserted successfully.");
        } catch (Exception e) {
            System.out.println("Error inserting booking: " + e.getMessage());
            e.printStackTrace();
        }
        return bookingID; // Return the generated booking ID or -1 if insertion failed
    }

//    public static ArrayList<ServiceBill> getAllServiceOrder() {
//        ArrayList<ServiceBill> listServiceBill = null;
//        try {
//            Connection con = DBConnect.getConnection();
//            String query = "select * from tblServiceBill where StatusBill=0";
//
//            PreparedStatement stmt = con.prepareStatement(query);
//            ResultSet rs = stmt.executeQuery();
//            listServiceBill = new ArrayList<>();
//            while (rs.next()) {
//                String billID = rs.getString(1);
//                String employeeID = rs.getString(2);
//                String customerID = rs.getString(3);
//                String dateCreate = rs.getString(4);
//                String shiftID = rs.getString(5);
//                String serviceID = rs.getString(6);
//                String day = rs.getString(7);
//                int status = rs.getInt(8);
//                int amount = rs.getInt(9);
//                ServiceBill serviceBill = new ServiceBill(billID, employeeID, customerID, dateCreate, shiftID, serviceID, day, status, amount);
//                listServiceBill.add(serviceBill);
//
//            }
//
//        } catch (Exception e) {
//            System.out.println("loi getAllServiceOrder() servicerespository");
//            e.printStackTrace();
//            return null;
//        }
//        return listServiceBill;
//    }
//    public static ArrayList<ServiceBill> getAllServiceAcceptedOrder() {
//        ArrayList<ServiceBill> listServiceBill = null;
//        try {
//            Connection con = DBConnect.getConnection();
//            String query = "select * from tblServiceBill where StatusBill=1";
//
//            PreparedStatement stmt = con.prepareStatement(query);
//            ResultSet rs = stmt.executeQuery();
//            listServiceBill = new ArrayList<>();
//            while (rs.next()) {
//                String billID = rs.getString(1);
//                String employeeID = rs.getString(2);
//                String customerID = rs.getString(3);
//                String dateCreate = rs.getString(4);
//                String shiftID = rs.getString(5);
//                String serviceID = rs.getString(6);
//                String day = rs.getString(7);
//                int status = rs.getInt(8);
//                int amount = rs.getInt(9);
//                ServiceBill serviceBill = new ServiceBill(billID, employeeID, customerID, dateCreate, shiftID, serviceID, day, status, amount);
//                listServiceBill.add(serviceBill);
//
//            }
//
//        } catch (Exception e) {
//            System.out.println("loi getAllServiceOrder() servicerespository");
//            e.printStackTrace();
//            return null;
//        }
//        return listServiceBill;
//    }
//    public static ArrayList<ServiceBill> getAllServiceCancelOrder() {
//        ArrayList<ServiceBill> listServiceBill = null;
//        try {
//            Connection con = DBConnect.getConnection();
//            String query = "select * from tblServiceBill where StatusBill=3";
//
//            PreparedStatement stmt = con.prepareStatement(query);
//            ResultSet rs = stmt.executeQuery();
//            listServiceBill = new ArrayList<>();
//            while (rs.next()) {
//                String billID = rs.getString(1);
//                String employeeID = rs.getString(2);
//                String customerID = rs.getString(3);
//                String dateCreate = rs.getString(4);
//                String shiftID = rs.getString(5);
//                String serviceID = rs.getString(6);
//                String day = rs.getString(7);
//                int status = rs.getInt(8);
//                int amount = rs.getInt(9);
//                ServiceBill serviceBill = new ServiceBill(billID, employeeID, customerID, dateCreate, shiftID, serviceID, day, status, amount);
//                listServiceBill.add(serviceBill);
//
//            }
//
//        } catch (Exception e) {
//            System.out.println("loi getAllServiceOrder() servicerespository");
//            e.printStackTrace();
//            return null;
//        }
//        return listServiceBill;
//    }
//    public static ArrayList<ServiceBill> getAllServicePaidOrder() {
//        ArrayList<ServiceBill> listServiceBill = null;
//        try {
//            Connection con = DBConnect.getConnection();
//            String query = "select * from tblServiceBill where StatusBill=2";
//
//            PreparedStatement stmt = con.prepareStatement(query);
//            ResultSet rs = stmt.executeQuery();
//            listServiceBill = new ArrayList<>();
//            while (rs.next()) {
//                String billID = rs.getString(1);
//                String employeeID = rs.getString(2);
//                String customerID = rs.getString(3);
//                String dateCreate = rs.getString(4);
//                String shiftID = rs.getString(5);
//                String serviceID = rs.getString(6);
//                String day = rs.getString(7);
//                int status = rs.getInt(8);
//                int amount = rs.getInt(9);
//                ServiceBill serviceBill = new ServiceBill(billID, employeeID, customerID, dateCreate, shiftID, serviceID, day, status, amount);
//                listServiceBill.add(serviceBill);
//
//            }
//
//        } catch (Exception e) {
//            System.out.println("loi getAllServiceOrder() servicerespository");
//            e.printStackTrace();
//            return null;
//        }
//        return listServiceBill;
//    }
    public static boolean acceptBill(String billID) {

        try {
            Connection con = DBConnect.getConnection();
            String query
                    = "update tblServiceBill\n"
                    + "set StatusBill=1\n"
                    + "where BillID=?";

            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, billID);

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("loi acceptBill() servicerespository");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean paidBill(String billID) {

        try {
            Connection con = DBConnect.getConnection();
            String query
                    = "update tblServiceBill\n"
                    + "set StatusBill=2\n"
                    + "where BillID=?";

            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, billID);

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("loi paidBill() servicerespository");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean cancelBill(String billID) {

        try {
            Connection con = DBConnect.getConnection();
            String query
                    = "update tblServiceBill\n"
                    + "set StatusBill=3\n"
                    + "where BillID=?";

            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, billID);

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("loi cancelBill() servicerespository");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static ArrayList<ServiceBooked> getAllServiceBookingCustomer(String customerID) {
        ArrayList<ServiceBooked> listServiceBooked = new ArrayList<>();
        try {
            Connection con = DBConnect.getConnection();
            String query = "SELECT B.BookingID, B.CustomerID, B.ServiceID, B.Name, B.PhoneNumber, B.Address, B.Sex, B.BookingDate, "
                    + "B.Slot, B.BookingStatus, B.Note,B.ServiceName, B.Price,B.Email, BS.BillID, BS.BillStatus "
                    + "FROM tblBooking B "
                    + "JOIN tblBillServiec BS ON B.BookingID = BS.BookingID "
                    + "WHERE B.CustomerID = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, customerID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ServiceBooked serviceBooked = new ServiceBooked();
                serviceBooked.setBookingID(rs.getInt("BookingID"));
                serviceBooked.setCustomerID(rs.getString("CustomerID"));
                serviceBooked.setServiceID(rs.getInt("ServiceID"));
                serviceBooked.setName(rs.getString("Name"));
                serviceBooked.setPhoneNumber(rs.getString("PhoneNumber"));
                serviceBooked.setAddress(rs.getString("Address"));
                serviceBooked.setSex(rs.getString("Sex"));
                serviceBooked.setBookingDate(rs.getString("BookingDate"));
                serviceBooked.setSlot(rs.getString("Slot"));
                serviceBooked.setBookingStatus(rs.getInt("BookingStatus"));
                serviceBooked.setNote(rs.getString("Note"));
                serviceBooked.setServiceName(rs.getString("ServiceName"));
                serviceBooked.setEmail(rs.getString("Email"));
                serviceBooked.setPrice(rs.getDouble("Price"));
                serviceBooked.setBillID(rs.getInt("BillID"));
                serviceBooked.setBillStatus(rs.getInt("BillStatus"));
                listServiceBooked.add(serviceBooked);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error in ServiceRespository.getAllServiceBooking");
            e.printStackTrace();
        }
        return listServiceBooked;
    }

    public static ArrayList<ServiceBooked> getAllServiceBooking() {
        ArrayList<ServiceBooked> listServiceBooked = new ArrayList<>();
        try {
            Connection con = DBConnect.getConnection();
            String query = "SELECT B.BookingID, B.CustomerID, B.ServiceID, B.Name, B.PhoneNumber, B.Address, B.Sex, B.BookingDate, \n"
                    + "                    B.Slot, B.BookingStatus, B.Note, B.Price, B.Email, B.ServiceName, BS.BillID, BS.BillStatus \n"
                    + "                    FROM tblBooking B \n"
                    + "                    JOIN tblBillServiec BS ON B.BookingID = BS.BookingID \n"
                    + "                    WHERE B.BookingStatus = 1";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ServiceBooked serviceBooked = new ServiceBooked();
                serviceBooked.setBookingID(rs.getInt("BookingID"));
                serviceBooked.setCustomerID(rs.getString("CustomerID"));
                serviceBooked.setServiceID(rs.getInt("ServiceID"));
                serviceBooked.setName(rs.getString("Name"));
                serviceBooked.setPhoneNumber(rs.getString("PhoneNumber"));
                serviceBooked.setAddress(rs.getString("Address"));
                serviceBooked.setSex(rs.getString("Sex"));
                serviceBooked.setBookingDate(rs.getString("BookingDate"));
                serviceBooked.setSlot(rs.getString("Slot"));
                serviceBooked.setBookingStatus(rs.getInt("BookingStatus"));
                serviceBooked.setNote(rs.getString("Note"));
                serviceBooked.setPrice(rs.getDouble("Price"));
                serviceBooked.setEmail(rs.getString("Email"));
                serviceBooked.setServiceName(rs.getString("ServiceName"));
                serviceBooked.setBillID(rs.getInt("BillID"));
                serviceBooked.setBillStatus(rs.getInt("BillStatus"));

                listServiceBooked.add(serviceBooked);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error in ServiceRepository.getAllServiceBooking");
            e.printStackTrace();
        }
        return listServiceBooked;
    }

    public static ArrayList<ServiceBooked> getAllServiceBookingStatus() {
        ArrayList<ServiceBooked> listServiceBooked = new ArrayList<>();
        try {
            Connection con = DBConnect.getConnection();
            String query = "SELECT B.BookingID, B.CustomerID, B.ServiceID, B.Name, B.PhoneNumber, B.Address, B.Sex, B.BookingDate, \n"
                    + "                    B.Slot, B.BookingStatus, B.Note, B.Price, B.Email, B.ServiceName, BS.BillID, BS.BillStatus \n"
                    + "                    FROM tblBooking B \n"
                    + "                    JOIN tblBillServiec BS ON B.BookingID = BS.BookingID \n"
                    + "                    WHERE B.BookingStatus = 3";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ServiceBooked serviceBooked = new ServiceBooked();
                serviceBooked.setBookingID(rs.getInt("BookingID"));
                serviceBooked.setCustomerID(rs.getString("CustomerID"));
                serviceBooked.setServiceID(rs.getInt("ServiceID"));
                serviceBooked.setName(rs.getString("Name"));
                serviceBooked.setPhoneNumber(rs.getString("PhoneNumber"));
                serviceBooked.setAddress(rs.getString("Address"));
                serviceBooked.setSex(rs.getString("Sex"));
                serviceBooked.setBookingDate(rs.getString("BookingDate"));
                serviceBooked.setSlot(rs.getString("Slot"));
                serviceBooked.setBookingStatus(rs.getInt("BookingStatus"));
                serviceBooked.setNote(rs.getString("Note"));
                serviceBooked.setPrice(rs.getDouble("Price"));
                serviceBooked.setEmail(rs.getString("Email"));
                serviceBooked.setServiceName(rs.getString("ServiceName"));
                serviceBooked.setBillID(rs.getInt("BillID"));
                serviceBooked.setBillStatus(rs.getInt("BillStatus"));

                listServiceBooked.add(serviceBooked);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error in ServiceRepository.getAllServiceBooking");
            e.printStackTrace();
        }
        return listServiceBooked;
    }

    public static ArrayList<ServiceBooked> getAllServiceBookingCancel() {
        ArrayList<ServiceBooked> listServiceBooked = new ArrayList<>();
        try {
            Connection con = DBConnect.getConnection();
            String query = "SELECT B.BookingID, B.CustomerID, B.ServiceID, B.Name, B.PhoneNumber, B.Address, B.Sex, B.BookingDate, "
                    + "B.Slot, B.BookingStatus, B.Note, B.Price, BS.BillID, BS.BillStatus "
                    + "FROM tblBooking B "
                    + "JOIN tblBillServiec BS ON B.BookingID = BS.BookingID "
                    + "WHERE B.BookingStatus = 0";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ServiceBooked serviceBooked = new ServiceBooked();
                serviceBooked.setBookingID(rs.getInt("BookingID"));
                serviceBooked.setCustomerID(rs.getString("CustomerID"));
                serviceBooked.setServiceID(rs.getInt("ServiceID"));
                serviceBooked.setName(rs.getString("Name"));
                serviceBooked.setPhoneNumber(rs.getString("PhoneNumber"));
                serviceBooked.setAddress(rs.getString("Address"));
                serviceBooked.setSex(rs.getString("Sex"));
                serviceBooked.setBookingDate(rs.getString("BookingDate"));
                serviceBooked.setSlot(rs.getString("Slot"));
                serviceBooked.setBookingStatus(rs.getInt("BookingStatus"));
                serviceBooked.setNote(rs.getString("Note"));
                serviceBooked.setPrice(rs.getDouble("Price"));
                serviceBooked.setBillID(rs.getInt("BillID"));
                serviceBooked.setBillStatus(rs.getInt("BillStatus"));
                listServiceBooked.add(serviceBooked);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error in ServiceRepository.getAllServiceBooking");
            e.printStackTrace();
        }
        return listServiceBooked;
    }

    public static ArrayList<ServiceBooked> getAllServiceBookingStatus2() {
        ArrayList<ServiceBooked> listServiceBooked = new ArrayList<>();
        try {
            Connection con = DBConnect.getConnection();
            String query = "SELECT B.BookingID, B.CustomerID, B.ServiceID, B.Name, B.PhoneNumber, B.Address, B.Sex, B.BookingDate, "
                    + "B.Slot, B.BookingStatus, B.Note, B.Price, BS.BillID, BS.BillStatus "
                    + "FROM tblBooking B "
                    + "JOIN tblBillServiec BS ON B.BookingID = BS.BookingID "
                    + "WHERE B.BookingStatus = 2 AND BS.BillStatus = 1";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ServiceBooked serviceBooked = new ServiceBooked();
                serviceBooked.setBookingID(rs.getInt("BookingID"));
                serviceBooked.setCustomerID(rs.getString("CustomerID"));
                serviceBooked.setServiceID(rs.getInt("ServiceID"));
                serviceBooked.setName(rs.getString("Name"));
                serviceBooked.setPhoneNumber(rs.getString("PhoneNumber"));
                serviceBooked.setAddress(rs.getString("Address"));
                serviceBooked.setSex(rs.getString("Sex"));
                serviceBooked.setBookingDate(rs.getString("BookingDate"));
                serviceBooked.setSlot(rs.getString("Slot"));
                serviceBooked.setBookingStatus(rs.getInt("BookingStatus"));
                serviceBooked.setNote(rs.getString("Note"));
                serviceBooked.setPrice(rs.getDouble("Price"));
                serviceBooked.setBillID(rs.getInt("BillID"));
                serviceBooked.setBillStatus(rs.getInt("BillStatus"));
                listServiceBooked.add(serviceBooked);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error in ServiceRepository.getAllServiceBooking");
            e.printStackTrace();
        }
        return listServiceBooked;
    }

    public static ArrayList<ServiceBooked> getAllServiceBookingPayment() {
        ArrayList<ServiceBooked> listServiceBooked = new ArrayList<>();
        try {
            Connection con = DBConnect.getConnection();
            String query = "SELECT B.BookingID, B.CustomerID, B.ServiceID, B.Name, B.PhoneNumber, B.Address, B.Sex, B.BookingDate, \n"
                    + "                    B.Slot, B.BookingStatus, B.Note, B.Price, B.Email, B.ServiceName, BS.BillID, BS.BillStatus \n"
                    + "                    FROM tblBooking B \n"
                    + "                    JOIN tblBillServiec BS ON B.BookingID = BS.BookingID \n"
                    + "                    WHERE BS.BillStatus  =3";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ServiceBooked serviceBooked = new ServiceBooked();
                serviceBooked.setBookingID(rs.getInt("BookingID"));
                serviceBooked.setCustomerID(rs.getString("CustomerID"));
                serviceBooked.setServiceID(rs.getInt("ServiceID"));
                serviceBooked.setName(rs.getString("Name"));
                serviceBooked.setPhoneNumber(rs.getString("PhoneNumber"));
                serviceBooked.setAddress(rs.getString("Address"));
                serviceBooked.setSex(rs.getString("Sex"));
                serviceBooked.setBookingDate(rs.getString("BookingDate"));
                serviceBooked.setSlot(rs.getString("Slot"));
                serviceBooked.setBookingStatus(rs.getInt("BookingStatus"));
                serviceBooked.setNote(rs.getString("Note"));
                serviceBooked.setPrice(rs.getDouble("Price"));
                serviceBooked.setBillID(rs.getInt("BillID"));
                serviceBooked.setBillStatus(rs.getInt("BillStatus"));
                listServiceBooked.add(serviceBooked);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error in ServiceRepository.getAllServiceBooking");
            e.printStackTrace();
        }
        return listServiceBooked;
    }

    public static ArrayList<Booking> getALLBooking() {
        ArrayList<Booking> listBooking = new ArrayList<>();
        try {
            String query = "SELECT * FROM tblBooking";
            Connection con = DBConnect.getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int bookingID = rs.getInt("bookingID");
                String customerID = rs.getString("customerID");
                String serviceID = rs.getString("serviceID");
                String name = rs.getString("name");
                String phoneNumber = rs.getString("phoneNumber");
                String address = rs.getString("address");
                String sex = rs.getString("sex");
                String bookingDate = rs.getString("bookingDate");
                String slot = rs.getString("slot");
                int bookingStatus = rs.getInt("bookingStatus");
                String note = rs.getString("note");
                double price = rs.getDouble("price");
                String email = rs.getString("email");
                String servicName = rs.getString("serviceName");
                Booking booking = new Booking(bookingID, customerID, serviceID, name, phoneNumber, address, sex, bookingDate, slot, bookingStatus, note, price, email, servicName);
                listBooking.add(booking);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.err.println("Error in database method getALLBooking");
            e.printStackTrace(); // Optional: This provides more details about the exception.
        }
        return listBooking;
    }

    public static ArrayList<ServiceBill> getAllBillService() {
        ArrayList<ServiceBill> billServices = new ArrayList<>();
        try {
            Connection con = DBConnect.getConnection();
            String query = "SELECT * FROM tblBillService";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int billID = rs.getInt("billID");
                int bookingID = rs.getInt("bookingID");
                String customerID = rs.getString("customerID");
                Date billDate = rs.getDate("billDate");
                double totalAmount = rs.getDouble("totalAmount");
                int billStatus = rs.getInt("billStatus");

                ServiceBill billService = new ServiceBill(billID, bookingID, customerID, billDate, totalAmount, billStatus);
                billServices.add(billService);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return billServices;
    }

    public static ArrayList<Booking> getAllBookingByCustomerID(String customerID) {
        ArrayList<Booking> listBooking = new ArrayList<>();
        try {
            String query = "SELECT * FROM tblBooking WHERE customerID = ?";
            Connection con = DBConnect.getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, customerID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int bookingID = rs.getInt("bookingID");
                String serviceID = rs.getString("serviceID");
                String name = rs.getString("name");
                String phoneNumber = rs.getString("phoneNumber");
                String address = rs.getString("address");
                String sex = rs.getString("sex");
                String bookingDate = rs.getString("bookingDate");
                String slot = rs.getString("slot");
                int bookingStatus = rs.getInt("bookingStatus");
                String note = rs.getString("note");
                double price = rs.getDouble("price");
                String email = rs.getString("email");
                String serviceName = rs.getString("serviceName");
                Booking booking = new Booking(bookingID, customerID, serviceID, name, phoneNumber, address, sex, bookingDate, slot, bookingStatus, note, price, email, serviceName);
                listBooking.add(booking);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.err.println("Error in database method getAllBookingByCustomerID");
            e.printStackTrace(); // Optional: This provides more details about the exception.
        }
        return listBooking;
    }

    public void deleteBooking(int bookingID) {
        String query = "DELETE FROM tblBooking WHERE bookingID = ?";
        try (Connection con = DBConnect.getConnection(); PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, bookingID);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteBillService(int billID) {
        String query = "DELETE FROM tblBillServiec WHERE billID = ?";
        try (Connection con = DBConnect.getConnection(); PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, billID);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateBooking(Booking booking) {
        try {
            Connection con = DBConnect.getConnection();
            String query = "UPDATE tblBooking SET name=?, address=?, sex=?, bookingDate=?, slot=?, note=? WHERE bookingID=?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, booking.getName());
            stmt.setString(2, booking.getAddress());
            stmt.setString(3, booking.getSex());
            stmt.setString(4, booking.getBookingDate());
            stmt.setString(5, booking.getSlot());
            stmt.setString(6, booking.getNote());
            stmt.setInt(7, booking.getBookingID());
            stmt.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Booking getBookingById(int bookingID) {
        Booking booking = null;
        try {
            Connection con = DBConnect.getConnection();
            String query = "SELECT * FROM tblBooking WHERE bookingID=?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, bookingID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                booking = new Booking();
                booking.setBookingID(rs.getInt("bookingID"));
                booking.setCustomerID(rs.getString("customerID"));
                booking.setServiceID(rs.getString("serviceID"));
                booking.setName(rs.getString("name"));
                booking.setPhoneNumber(rs.getString("phoneNumber"));
                booking.setAddress(rs.getString("address"));
                booking.setSex(rs.getString("sex"));
                booking.setBookingDate(rs.getString("bookingDate"));
                booking.setSlot(rs.getString("slot"));
                booking.setBookingStatus(rs.getInt("bookingStatus"));
                booking.setNote(rs.getString("note"));
                booking.setPrice(rs.getDouble("price"));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return booking;
    }

    public void updateBookingStatus(int bookingID, int bookingStatus) {
        try {
            Connection con = DBConnect.getConnection();
            String sql = "UPDATE tblBooking SET BookingStatus = ? WHERE BookingID = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, bookingStatus);
            pstmt.setInt(2, bookingID);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Booking status updated successfully.");
            } else {
                System.out.println("No booking found with the given ID.");
            }

            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error updating booking status: " + e.getMessage());
        }
    }

    public void updateBillStatus(int billID, int billStatus) {
        try {
            Connection con = DBConnect.getConnection();
            String sql = "UPDATE tblBillServiec SET BillStatus = ? WHERE BillID = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, billStatus);
            pstmt.setInt(2, billID);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Bill status updated successfully.");
            } else {
                System.out.println("No bill found with the given ID.");
            }

            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error updating bill status: " + e.getMessage());
        }
    }

    public int addBill(ServiceBill bill) {
        String sql = "INSERT INTO tblBillServiec (bookingID, customerID, billDate, totalAmount, billStatus) VALUES (?, ?, ?, ?, ?)";
        int billID = -1;

        try (Connection conn = DBConnect.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, bill.getBookingID());
            stmt.setString(2, bill.getCustomerID());
            stmt.setTimestamp(3, new java.sql.Timestamp(bill.getBillDate().getTime()));
            stmt.setDouble(4, bill.getTotalAmount());
            stmt.setInt(5, bill.getBillStatus());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating bill failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    billID = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating bill failed, no ID obtained.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Ideally, use a logger to log the error
        }

        return billID;
    }

    public static ArrayList<ServiceBill> getAllBillServiceByCustomerID(String customerID) {
        ArrayList<ServiceBill> listBillService = new ArrayList<>();
        try {
            String query = "SELECT * FROM tblBillServiec WHERE customerID = ?";
            Connection con = DBConnect.getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, customerID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int billID = rs.getInt("billID");
                int bookingID = rs.getInt("bookingID");
                Date billDate = rs.getDate("billDate");
                double totalAmount = rs.getDouble("totalAmount");
                int billStatus = rs.getInt("billStatus");

                ServiceBill billService = new ServiceBill(billID, bookingID, customerID, billDate, totalAmount, billStatus);
                listBillService.add(billService);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.err.println("Error in database method getAllBillServiceByCustomerID");
            e.printStackTrace(); // Optional: This provides more details about the exception.
        }
        return listBillService;
    }

    public static void sendEmail(String toEmail, String subject, String body) {
        final String fromEmail = "phuongnam121103@gmail.com"; // requires valid Gmail id
        final String password = "eqna xeml exop mnzc"; // correct password for Gmail id

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
        props.put("mail.smtp.port", "587"); // TLS Port
        props.put("mail.smtp.auth", "true"); // enable authentication
        props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

        // create Authenticator object to pass in Session.getInstance argument
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            MimeMessage msg = new MimeMessage(session);
            // set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(fromEmail, "NoReply-JD"));

            msg.setReplyTo(InternetAddress.parse(fromEmail, false));

            msg.setSubject(subject, "UTF-8");

            msg.setText(body, "UTF-8");

            msg.setSentDate(new java.util.Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            Transport.send(msg);

            System.out.println("Email Sent Successfully!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveFeedback(Feedback feedback) {
        String sql = "INSERT INTO feedback (CustomerID, ServiceID, Testimonial, experience_date, satisfaction_level, name) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        // Use try-with-resources to ensure resources are closed properly
        try (Connection con = DBConnect.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {

            // Set parameters
            stmt.setString(1, feedback.getCustomerID());
            stmt.setInt(2, feedback.getServiceID());
            stmt.setString(3, feedback.getTestimonial());
            stmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));
            stmt.setInt(5, feedback.getSatisfactionLevel());
            stmt.setString(6, feedback.getName());

            // Execute update
            stmt.executeUpdate();
            System.out.println("Feedback saved successfully!");

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error in database method saveFeedback");
            e.printStackTrace();
        }
    }

    public static boolean updateService(Service service) throws SQLException, ClassNotFoundException {
        try (Connection con = DBConnect.getConnection()) {
            String query = "UPDATE tblService SET ServiceName=?, ServicePrice=?, ServiceImage=?, Description=? WHERE ServiceID=?";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setString(1, service.getServiceName());
                stmt.setString(2, service.getServicePrice());
                stmt.setString(3, service.getListImg());
                stmt.setString(4, service.getDescription());
                stmt.setInt(5, service.getServiceID());
                int rowsUpdated = stmt.executeUpdate();
                return rowsUpdated > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error updating service");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteService(int serviceID) throws SQLException, ClassNotFoundException {
        try (Connection con = DBConnect.getConnection()) {
            String query = "DELETE FROM tblService WHERE ServiceID=?";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setInt(1, serviceID);
                int rowsDeleted = stmt.executeUpdate();
                return rowsDeleted > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error deleting service");
            e.printStackTrace();
            return false;
        }
    }

    public static List<Feedback> getAllFeedback() throws ClassNotFoundException {
        List<Feedback> feedbackList = new ArrayList<>();
        try {
            String query = "SELECT CustomerID, ServiceID, Testimonial, experience_date, satisfaction_level,name FROM feedback";

            try (Connection con = DBConnect.getConnection();
                    PreparedStatement stmt = con.prepareStatement(query);
                    ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    String customerID = rs.getString("CustomerID");
                    int serviceID = rs.getInt("ServiceID");
                    String testimonial = rs.getString("Testimonial");
                    java.sql.Date experienceDate = rs.getDate("experience_date");  // Updated column name
                    int satisfactionLevel = rs.getInt("satisfaction_level");       // Updated column name
                    String name = rs.getString("name");
                    Feedback feedback = new Feedback(customerID, serviceID, testimonial, experienceDate, satisfactionLevel, name);
                    feedbackList.add(feedback);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in database method getAllFeedback");
            e.printStackTrace(); // Optional: This provides more details about the exception.
        }
        return feedbackList;
    }

    public static ArrayList<Feedback> getFeedBackByServiceID(String serviceID) {
        ArrayList<Feedback> feedbackList = new ArrayList<>();
        try {
            String query = "SELECT * FROM [dbo].[feedback] WHERE ServiceID = ?";
            Connection con = DBConnect.getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, serviceID);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                int id = results.getInt("FeedbackID");
                String customerID = results.getString("CustomerID");
                int serviceId = results.getInt("ServiceID");
                String testimonial = results.getString("Testimonial");
                Date experienceDate = results.getDate("experience_date");
                int satisfactionLevel = results.getInt("satisfaction_level");
                String name = results.getString("name");
                Feedback feedback = new Feedback(customerID, serviceId, testimonial, experienceDate, satisfactionLevel, name);
                feedbackList.add(feedback);
            }
        } catch (Exception e) {
            System.err.println("Error in database method getFeedBackByServiceID");
            e.printStackTrace();
        }
        return feedbackList;
    }
    

    public static boolean addService(String serviceName, String serviceUrlImg, String description, double servicePrice) throws SQLException, ClassNotFoundException {
    String sql = "INSERT INTO tblService(servicename, serviceprice, description, serviceimage, TypeID) VALUES (?,?,?,?,?)"; // Chỉnh sửa câu lệnh SQL để có đủ tham số

    try (Connection con = DBConnect.getConnection();
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setString(1, serviceName);
        stmt.setDouble(2, servicePrice);
        stmt.setString(3, description);
        stmt.setString(4, serviceUrlImg);
        
        int typeID = 1; // Default value
        if (serviceName.contains("đầy tháng")) {
            typeID = 1;
        } else if (serviceName.contains("thôi nôi")) {
            typeID = 2;
        } else if (serviceName.contains("sinh nhật")) {
            typeID = 3;
        }

        stmt.setInt(5, typeID); // Đảm bảo rằng bạn truyền đủ tham số

        stmt.executeUpdate();
        System.out.println("Service added successfully.");

    } catch (SQLException e) {
        System.out.println("Error in addService");
        e.printStackTrace();
        return false; // Trả về false nếu có lỗi xảy ra
    }
    return true;
}


    public static HashMap<String, Integer> countServiceBookings() {
        HashMap<String, Integer> serviceCountMap = new HashMap<>();
        try {
            Connection con = DBConnect.getConnection();
            String query = "SELECT ServiceName, COUNT(*) AS count FROM tblBooking GROUP BY ServiceName;";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String serviceName = rs.getString("ServiceName");
                int count = rs.getInt("count");
                serviceCountMap.put(serviceName, count);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error in ServiceRepository.countServiceBookings");
            e.printStackTrace();
        }
        return serviceCountMap;
    }

    public static boolean updateStatusCustomerRefund(int refundID, int newRefundStatus) {
        boolean result = false;
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            String query = "UPDATE tblCustomerRefund SET RefundStatus = ? WHERE refundID = ?";
            con = DBConnect.getConnection();
            stmt = con.prepareStatement(query);
            stmt.setInt(1, newRefundStatus);
            stmt.setInt(2, refundID);

            int rowsAffected = stmt.executeUpdate();
            result = rowsAffected > 0;

        } catch (Exception e) {
            System.err.println("Error in database method updateRefundStatusCustomerRefund");
            e.printStackTrace(); // Optional: This provides more details about the exception.
        } finally {
            // Clean up resources
            try {
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

        return result;
    }

    public static ArrayList<ServiceBooked> getBillService() {
        ArrayList<ServiceBooked> listServiceBooked = new ArrayList<>();
        try {
            Connection con = DBConnect.getConnection();
            String query = "SELECT B.BookingID, B.CustomerID, B.Name, B.Price, B.ServiceName, BS.BillID, BS.BillStatus ,BS.BillDate\n"
                    + "                  FROM tblBooking B \n"
                    + "                  JOIN tblBillServiec BS ON B.BookingID = BS.BookingID\n"
                    + "WHERE BS.BillStatus = 3;";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ServiceBooked serviceBooked = new ServiceBooked();
                serviceBooked.setBookingID(rs.getInt("BookingID"));
                serviceBooked.setCustomerID(rs.getString("CustomerID"));
                serviceBooked.setName(rs.getString("Name"));
                serviceBooked.setPrice(rs.getDouble("Price"));
                serviceBooked.setServiceName(rs.getString("ServiceName"));
                serviceBooked.setBillID(rs.getInt("BillID"));
                serviceBooked.setBillStatus(rs.getInt("BillStatus"));
                serviceBooked.setBillDate(rs.getDate("BillDate"));

                listServiceBooked.add(serviceBooked);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error in ServiceRepository.getAllServiceBooking");
            e.printStackTrace();
        }
        return listServiceBooked;
    }

    public static List<CustomerRefund> getAllCustomerRefund() {
        List<CustomerRefund> customerRefunds = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM tblCustomerRefund";
            con = DBConnect.getConnection();
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                CustomerRefund customerRefund = new CustomerRefund();
                customerRefund.setRefundID(rs.getInt("RefundID"));
                customerRefund.setBookingID(rs.getInt("BookingID"));
                customerRefund.setCustomerID(rs.getString("CustomerID"));
                customerRefund.setName(rs.getString("Name"));
                customerRefund.setServiceName(rs.getString("ServiceName"));
                customerRefund.setAccountNumber(rs.getString("AccountNumber"));
                customerRefund.setBankName(rs.getString("BankName"));
                customerRefund.setRefundAmount(rs.getDouble("RefundAmount"));
                customerRefund.setRefundDate(rs.getString("RefundDate"));
                customerRefund.setRefundStatus(rs.getInt("RefundStatus"));
                customerRefund.setNote(rs.getString("Note"));
                customerRefund.setEmail(rs.getString("Email"));
                customerRefund.setAccountName(rs.getString("AccountName"));

                customerRefunds.add(customerRefund);
            }

        } catch (Exception e) {
            System.err.println("Error in database method getAllCustomerRefund");
            e.printStackTrace(); // Optional: This provides more details about the exception.
        } finally {
            // Clean up resources
            try {
                if (rs != null) {
                    rs.close();
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

        return customerRefunds;
    }

    public static List<CustomerRefund> getCustomerIDRefund(String customerID) {
        List<CustomerRefund> customerRefunds = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM tblCustomerRefund WHERE CustomerID = ?";
            con = DBConnect.getConnection();
            stmt = con.prepareStatement(query);
            stmt.setString(1, customerID);
            rs = stmt.executeQuery();

            while (rs.next()) {
                CustomerRefund customerRefund = new CustomerRefund();
                customerRefund.setBookingID(rs.getInt("BookingID"));
                customerRefund.setCustomerID(rs.getString("CustomerID"));
                customerRefund.setName(rs.getString("Name"));
                customerRefund.setServiceName(rs.getString("ServiceName"));
                customerRefund.setAccountNumber(rs.getString("AccountNumber"));
                customerRefund.setBankName(rs.getString("BankName"));
                customerRefund.setRefundAmount(rs.getDouble("RefundAmount"));
                customerRefund.setRefundDate(rs.getString("RefundDate"));
                customerRefund.setRefundStatus(rs.getInt("RefundStatus"));
                customerRefund.setNote(rs.getString("Note"));
                customerRefund.setAccountName(rs.getString("AccountName"));

                customerRefunds.add(customerRefund);
            }

        } catch (Exception e) {
            System.err.println("Error in database method getCustomerIDRefund");
            e.printStackTrace(); // Optional: This provides more details about the exception.
        } finally {
            // Clean up resources
            try {
                if (rs != null) {
                    rs.close();
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

        return customerRefunds;
    }

    public static boolean insertCustomerRefund(CustomerRefund customerRefund) {
        boolean result = false;
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            String query = "INSERT INTO tblCustomerRefund (BookingID, CustomerID, Name, ServiceName, AccountNumber, BankName, RefundAmount, RefundDate, RefundStatus, Note, AccountName,Email) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
            con = DBConnect.getConnection();
            stmt = con.prepareStatement(query);

            stmt.setInt(1, customerRefund.getBookingID());
            stmt.setString(2, customerRefund.getCustomerID());
            stmt.setString(3, customerRefund.getName());
            stmt.setString(4, customerRefund.getServiceName());
            stmt.setString(5, customerRefund.getAccountNumber());
            stmt.setString(6, customerRefund.getBankName());
            stmt.setDouble(7, customerRefund.getRefundAmount());
            stmt.setString(8, customerRefund.getRefundDate());
            stmt.setInt(9, customerRefund.getRefundStatus());
            stmt.setString(10, customerRefund.getNote());
            stmt.setString(11, customerRefund.getAccountName());
            stmt.setString(12, customerRefund.getEmail());

            int rowsAffected = stmt.executeUpdate();
            result = rowsAffected > 0;

        } catch (Exception e) {
            System.err.println("Error in database method insertCustomerRefund");
            e.printStackTrace(); // Optional: This provides more details about the exception.
        } finally {
            // Clean up resources
            try {
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

        return result;
    }

    public static int countBookingsForDateAndSlot(String bookingDate, String slot, String serviceID) throws ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;

        try {
            // Replace with your database connection logic
            conn = DBConnect.getConnection();

            String query = "SELECT COUNT(*) AS count FROM tblBooking WHERE BookingDate = ? AND Slot = ? AND ServiceID = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, bookingDate);
            ps.setString(2, slot);
            ps.setString(3, serviceID);
            rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException ex) {
            // Handle SQL exception
            ex.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return count;
    }

    public static ArrayList<ServiceType> getServiceTypeAll() {
        ArrayList<ServiceType> listService = new ArrayList<>();
        try {
            String query = "SELECT * FROM tblServiceType";
            Connection con = DBConnect.getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                int Type = results.getInt(1); // Use column names
                String TypeName = results.getString(2);
                ServiceType serviceType = new ServiceType(Type, TypeName);
                listService.add(serviceType);
            }
        } catch (Exception e) {
            System.err.println("Error in database method getServiceType");
            e.printStackTrace(); // This prints the stack trace to the console
        }
        return listService;
    }

    public static ArrayList<Service> getServiceType(String type) {
        ArrayList<Service> listService = new ArrayList<>();
        try {
            String query = "SELECT * FROM tblService  where TypeID =?";
            Connection con = DBConnect.getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, type);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                int serviceId = results.getInt(1);
                String serviceName = results.getString(2);
                double servicePrice = results.getDouble(3);
                String serviceDescription = results.getString(4);
                String listImage = results.getString(5);

                int Type = results.getInt(6);
                Service service = new Service(serviceId, serviceName, servicePrice, listImage, serviceDescription, Type);
                listService.add(service);
            }
        } catch (Exception e) {
            System.err.println("Error in database method getALLService");
            e.printStackTrace(); // Optional: This provides more details about the exception.
        }
        return listService;
    }

    public static ArrayList<Service> getSerachService(String txt) {
        ArrayList<Service> listService = new ArrayList<>();
        try {
            String query = "SELECT * FROM tblService WHERE ServiceName LIKE ?";
            Connection con = DBConnect.getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, "%" + txt + "%"); // Use wildcards for LIKE query
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                int serviceId = results.getInt(1);
                String serviceName = results.getString(2);
                double servicePrice = results.getDouble(3);
                String serviceDescription = results.getString(4);
                String listImage = results.getString(5);
                int type = results.getInt(6);

                Service service = new Service(serviceId, serviceName, servicePrice, listImage, serviceDescription, type);
                listService.add(service);
            }
        } catch (Exception e) {
            System.err.println("Error in searchServiceByName method");
            e.printStackTrace();
        }
        return listService;

    }

    public static ArrayList<MonthlyRevenue> getMonthlyRevenue(int year) {
        ArrayList<MonthlyRevenue> monthlyRevenueList = new ArrayList<>();
        try {
            Connection con = DBConnect.getConnection();
            String query = "SELECT MONTH(BS.BillDate) AS Month, SUM(B.Price) AS TotalRevenue "
                    + "FROM tblBooking B "
                    + "JOIN tblBillServiec BS ON B.BookingID = BS.BookingID "
                    + "WHERE BS.BillStatus = 3 AND YEAR(BS.BillDate) = ? "
                    + "GROUP BY MONTH(BS.BillDate) "
                    + "ORDER BY MONTH(BS.BillDate);";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, year);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MonthlyRevenue monthlyRevenue = new MonthlyRevenue();
                monthlyRevenue.setYear(year); // Set the year for each month
                monthlyRevenue.setMonth(rs.getInt("Month"));
                monthlyRevenue.setTotalRevenue(rs.getDouble("TotalRevenue"));

                monthlyRevenueList.add(monthlyRevenue);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error in ServiceRepository.getMonthlyRevenue");
            e.printStackTrace();
        }
        return monthlyRevenueList;
    }

    public static int getTotalEvaluateForService(String serviceID) throws SQLException, ClassNotFoundException {
        int totalNumberOfRatings = 0;
        String query = "SELECT COUNT(*) AS TotalNumberOfRatings FROM feedback WHERE ServiceID = ?";

        try (Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, serviceID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalNumberOfRatings = rs.getInt("TotalNumberOfRatings");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in getTotalNumberOfRatingsForService method: " + e.getMessage());
            e.printStackTrace();
        }

        return totalNumberOfRatings;
    }

    public static double getAverageRatingForService(String serviceID) throws SQLException, ClassNotFoundException {
        int totalRating = 0;
        int totalNumberOfRatings = 0;
        double averageRating = 0.0;

        String totalRatingQuery = "SELECT SUM(satisfaction_level) AS TotalRating FROM feedback WHERE ServiceID = ?";
        String totalNumberOfRatingsQuery = "SELECT COUNT(*) AS TotalNumberOfRatings FROM feedback WHERE ServiceID = ?";

        try (Connection con = DBConnect.getConnection()) {
            try (PreparedStatement psTotalRating = con.prepareStatement(totalRatingQuery);
                    PreparedStatement psTotalNumberOfRatings = con.prepareStatement(totalNumberOfRatingsQuery)) {

                psTotalRating.setString(1, serviceID);
                psTotalNumberOfRatings.setString(1, serviceID);

                try (ResultSet rsTotalRating = psTotalRating.executeQuery();
                        ResultSet rsTotalNumberOfRatings = psTotalNumberOfRatings.executeQuery()) {

                    if (rsTotalRating.next()) {
                        totalRating = rsTotalRating.getInt("TotalRating");
                    }

                    if (rsTotalNumberOfRatings.next()) {
                        totalNumberOfRatings = rsTotalNumberOfRatings.getInt("TotalNumberOfRatings");
                    }
                }
            }

            if (totalNumberOfRatings != 0) {
                averageRating = (double) totalRating / totalNumberOfRatings;
            }
        } catch (SQLException e) {
            System.err.println("Error in getAverageRatingForProduct method: " + e.getMessage());
            e.printStackTrace();
        }

        return averageRating;
    }

    public static int getTotalRatingForService(String serviceID) throws SQLException, ClassNotFoundException {
        int totalRating = 0;
        String query = "  SELECT SUM(satisfaction_level) AS TotalRating FROM feedback WHERE ServiceID = ?";

        try (Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, serviceID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalRating = rs.getInt("TotalRating");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in getTotalRatingForProduct method: " + e.getMessage());
            e.printStackTrace();
        }

        return totalRating;
    }

    public static void main(String[] args) {
//                         Feedback feedback = new Feedback();
//
//               feedback.setCustomerID("U8516");
//        feedback.setServiceID(1);
//        feedback.setTestimonial("quanquan");
//        feedback.setExperienceDate(new Date());
//        feedback.setSatisfactionLevel(3);
//        feedback.setName("pham trong khoi" );
//        
//        // Lưu phản hồi vào cơ sở dữ liệu
//       
//            saveFeedback(feedback);
//        System.out.println("Feedback saved successfully!");
//        ArrayList<CustomerRefund> services = (ArrayList<CustomerRefund>) getAllCustomerRefund();
//
//        for (CustomerRefund service : services) {
//            System.out.println(service);
//        }

 try {
            String serviceName = "Dịch vụ sinh nhật tuyệt vời";
            String serviceUrlImg = "https://example.com/image.jpg";
            String description = "Dịch vụ tổ chức sinh nhật cho trẻ em";
            double servicePrice = 200.00;

            boolean isAdded = addService(serviceName, serviceUrlImg, description, servicePrice);

            if (isAdded) {
                System.out.println("Service added successfully.");
            } else {
                System.out.println("Failed to add service.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
//   ArrayList<ServiceBooked> services = getAllServiceBookingPayment();
//
//        for (ServiceBooked service : services) {
//            System.out.println(service);
//        }
//    }

//       
//        Booking booking = new Booking();
//        booking.setCustomerID("U8360");
//        booking.setServiceID("1");
//        booking.setName("John Doe");
//        booking.setPhoneNumber("1234567890");
//        booking.setAddress("123 Main St, Anytown, USA");
//        booking.setSex("Male");
//        booking.setBookingDate("2024-05-27 10:00:00");
//        booking.setSlot("1");
//        booking.setBookingStatus(1);
//        booking.setNote("10 min");
//        booking.setPrice(10);
//        booking.setNote("asd");
//        booking.setEmail("àghđb");
//        booking.setServiceName("1 tháng");
//        ServiceRespository bookingRepository = new ServiceRespository();
//        bookingRepository.insertBooking(booking);
//
//    }
//     
//    }
//     ArrayList<Booking> services = getALLBooking();
//
//        for (Booking service : services) {
//            System.out.println(service);
//        }
//    }
//        String serviceId = "1";
//        
//        // Call the getService method
//        Service service = getService(serviceId);
//        
//        // Check if the service is not null and print its details
//        if (service != null) {
//            System.out.println("Service ID: " + service.getServiceID());
//            System.out.println("Service Name: " + service.getServiceName());
//            System.out.println("Service Price: " + service.getServicePrice());
//            
//            System.out.println("Service Description: " + service.getDescription());
//            System.out.println("Service Image List: " + service.getListImg());
//        } else {
//            System.out.println("No service found with ID: " + serviceId);
//        }
//    }
//        getBookingHistory("C6862").toString();
//        System.out.println("ccccc");
//        for (ServiceBooked b: getBookingHistory("C6862")) {
//            System.out.println(b.toString());
//        }
//        for (Shift s : getAllShiftByDay("S0001", "2023-06-20")) {
//            System.out.println(s);
//        }
//        for (Calendar c:getCalendarByMonthYear("S0001",6,2023)
//             ) {
//            System.out.println(c);
//        }
//        System.out.println(getServiceName("S0001"));
//        x
//        ArrayList<Shift> listShift= getAllShiftByDay("S0001","2023-06-01");
//        for (Shift s : listShift) {
//            System.out.println(s);
//        }
//        listShift=updateAmountShift(listShift);
//        for (Shift s : listShift) {
//            System.out.println(s);
//        }
//        String query = "insert into tblServiceBill\n" +
//                    "values\n" +
//                    "('WFQGC',NULL\t,'C0001','2023-06-01','SH001','S0001','2023-06-13',0,\t12)"
//        createCheckout("WFQ2GC","C0001","2023-06-20","S0001","SH001","2023-06-13");
//        ArrayList<Shift> listShift = getAllShiftByDay("S0001", "2023-06-01");
//        for (Shift s : listShift) {
//            System.out.println(s);
//        }
//        listShift = updateAmountShift(listShift);
//        for (Shift s : listShift) {
//            System.out.println(s);
//        }
//        for (ServiceBill s: getAllServiceOrder()
//             ) {
//            System.out.println(s);
//
//        String bookingDate = "2024-07-17"; // Replace with your desired booking date
//
//        // Call your method to get booking counts
//        Map<String, Integer> slotCounts = getBookingCountsByDate(bookingDate);
//
//        // Print the results
//        System.out.println("Booking counts for date: " + bookingDate);
//        for (Map.Entry<String, Integer> entry : slotCounts.entrySet()) {
//            {
//                System.out.println("Slot: " + entry.getKey() + ", Count: " + entry.getValue());
////                return null;
//            }
//
//        }
//    }
// String bookingDate = "2024-07-17";
//        String slot = "Slot 1 (7:00 - 9:00)";
//        String serviceID = "1"; // Replace with your actual service ID
//
//        try {
//            int count = countBookingsForDateAndSlot(bookingDate, slot, serviceID);
//            System.out.println("Number of bookings for date " + bookingDate + ", slot " + slot + " and service ID " + serviceID + ": " + count);
//        } catch (ClassNotFoundException ex) {
//            ex.printStackTrace();
//        }
}
