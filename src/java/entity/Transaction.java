/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author Admin
 */
public class Transaction {
   
    private String revenueId;
    private String ctvId;
    private Double revenue;
    private String status;

    // Getters and Setters

    public Transaction(String revenueId,  String ctvId, Double revenue, String status) {
        this.revenueId = revenueId;
  
        this.ctvId = ctvId;
        this.revenue = revenue;
        this.status = status;
    }

public String getRevenueId() {
        return revenueId;
    }

    public void setRevenueId(String revenueId) {
        this.revenueId = revenueId;
    }


    public String getCtvId() {
        return ctvId;
    }

    public void setCtvId(String ctvId) {
        this.ctvId = ctvId;
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}