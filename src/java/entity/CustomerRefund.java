/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author ACER
 */
public class CustomerRefund {
    private int refundID;
    private int bookingID;
    private String customerID;
    private String name;
    private String serviceName;
    private String accountNumber;
    private String bankName;
    private double refundAmount;
    private String refundDate;
    private int refundStatus;
    private String note;
    private String accountName;
    private String email;

    public CustomerRefund(int refundID, int bookingID, String customerID, String name, String serviceName, String accountNumber, String bankName, double refundAmount, String refundDate, int refundStatus, String note, String accountName, String email) {
        this.refundID = refundID;
        this.bookingID = bookingID;
        this.customerID = customerID;
        this.name = name;
        this.serviceName = serviceName;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.refundAmount = refundAmount;
        this.refundDate = refundDate;
        this.refundStatus = refundStatus;
        this.note = note;
        this.accountName = accountName;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    

   

    public CustomerRefund() {
    }

    public CustomerRefund(int refundID, int bookingID, String customerID, String name, String serviceName, String accountNumber, String bankName, double refundAmount, String refundDate, int refundStatus, String note, String accountName) {
        this.refundID = refundID;
        this.bookingID = bookingID;
        this.customerID = customerID;
        this.name = name;
        this.serviceName = serviceName;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.refundAmount = refundAmount;
        this.refundDate = refundDate;
        this.refundStatus = refundStatus;
        this.note = note;
        this.accountName = accountName;
    }

    
 public int getRefundID() {
        return refundID;
    }
    public void setRefundID(int refundID) {
        this.refundID = refundID;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(String refundDate) {
        this.refundDate = refundDate;
    }

    public int getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(int refundStatus) {
        this.refundStatus = refundStatus;
    }

   

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Override
    public String toString() {
        return "CustomerRefund{" + "refundID=" + refundID + ", bookingID=" + bookingID + ", customerID=" + customerID + ", name=" + name + ", serviceName=" + serviceName + ", accountNumber=" + accountNumber + ", bankName=" + bankName + ", refundAmount=" + refundAmount + ", refundDate=" + refundDate + ", refundStatus=" + refundStatus + ", note=" + note + ", accountName=" + accountName + ", email=" + email + '}';
    }

    

   
    
}