package entity;

import java.text.DecimalFormat;

public class Revenue { 
    
    private int revenueID;
    private String brandID;
    private String CTVID;
    private double revenue;
    private int month;
    private int year;
    private String status;

    public Revenue(int revenueID, String brandID, String CTVID, double revenue, int month, int year, String status) {
        this.revenueID = revenueID;
        this.brandID = brandID;
        this.CTVID = CTVID;
        this.revenue = revenue;
        this.month = month;
        this.year = year;
        this.status = status;
    }

    public int getRevenueID() {
        return revenueID;
    }

    public void setRevenueID(int revenueID) {
        this.revenueID = revenueID;
    }

    public String getBrandID() {
        return brandID;
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }

    public String getCTVID() {
        return CTVID;
    }

    public void setCTVID(String CTVID) {
        this.CTVID = CTVID;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
   

   
}
