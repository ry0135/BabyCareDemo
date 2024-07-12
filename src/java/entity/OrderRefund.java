
package entity;

import java.util.Date;

public class OrderRefund {
    private int refundID;
    private String billID;
    private String userID;
    private String name;
    private String accountNumber;
    private String bankName;
    private double refundAmount;
    private Date refundDate;
    private int refundStatus;
    private String note;
    private String fullName; 

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public OrderRefund() {
    }

    public OrderRefund(String billID, String userID, String name, String accountNumber, String bankName, double refundAmount, String note) {
        this.billID = billID;
        this.userID = userID;
        this.name = name;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.refundAmount = refundAmount;
        this.note = note;
    }
    

    public int getRefundID() {
        return refundID;
    }

    public void setRefundID(int refundID) {
        this.refundID = refundID;
    }

    public String getBillID() {
        return billID;
    }

    public void setBillID(String billID) {
        this.billID = billID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(Date refundDate) {
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
}
