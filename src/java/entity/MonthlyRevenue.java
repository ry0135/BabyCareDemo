/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

public class MonthlyRevenue {
    private int year;
    private int month;
    private double totalRevenue;

    public MonthlyRevenue() {
    }

    public MonthlyRevenue(int year, int month, double totalRevenue) {
        this.year = year;
        this.month = month;
        this.totalRevenue = totalRevenue;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

  

    

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    // Method to format total revenue to string with comma as thousands separator
    public String getFormattedTotalRevenue() {
        return String.format("%,.2f", totalRevenue);
    }
}