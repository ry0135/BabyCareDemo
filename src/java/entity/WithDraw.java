/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Date;

public class WithDraw {
    private int withdrawID;
    private Date requestDate;
    private String brandID;
    private double amount;
    private int status;
     private String brandName;
    private String bankName;
    private String accountNumber;
    public WithDraw() {}

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public WithDraw(int withdrawID, Date requestDate, String brandID, double amount, int status, String brandName, String bankName, String accountNumber) {
        this.withdrawID = withdrawID;
        this.requestDate = requestDate;
        this.brandID = brandID;
        this.amount = amount;
        this.status = status;
        this.brandName = brandName;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
    }
    
    
    
    public WithDraw(Date requestDate, String brandID, double amount, int status) {
        this.requestDate = requestDate;
        this.brandID = brandID;
        this.amount = amount;
        this.status = status;
    }

    public WithDraw(int withdrawID, Date requestDate, String brandID, double amount, int status) {
        this.withdrawID = withdrawID;
        this.requestDate = requestDate;
        this.brandID = brandID;
        this.amount = amount;
        this.status = status;
    }


   

    public void setAmount(double amount) {
        this.amount = amount;
    }
    public int getWithdrawID() {
        return withdrawID;
    }

    public void setWithdrawID(int withdrawID) {
        this.withdrawID = withdrawID;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getBrandID() {
        return brandID;
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }

    public double getAmount() {
        return amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "WithDraw{" + "withdrawID=" + withdrawID + ", requestDate=" + requestDate + ", brandID=" + brandID + ", amount=" + amount + ", status=" + status + ", brandName=" + brandName + ", bankName=" + bankName + ", accountNumber=" + accountNumber + '}';
    }

   
    
}
   
