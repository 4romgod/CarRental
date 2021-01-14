package com.fromgod.pojo;

import java.io.Serializable;


public class Rental implements Serializable{
    
    int rentalNumber;
    String dateRental;
    String dateReturned;
    double pricePerDay;
    double totalRental;
    int custNumber;
    int vehNumber;
    
    public Rental(){
        rentalNumber = 0;
        dateRental = "";
        dateReturned = "";
        pricePerDay = 0;
        totalRental = 0;
        custNumber = 0;
        vehNumber = 0;
    }

    public Rental(int rentalNumber, String dateRental, String dateReturned, double totalRental, int custNumber, int vehNumber) {
        this.rentalNumber = rentalNumber;
        this.dateRental = dateRental;
        this.dateReturned = dateReturned;
        this.totalRental = totalRental;
        this.custNumber = custNumber;
        this.vehNumber = vehNumber;
    }
    
    public Rental(String dateRental, int custNumber, int vehNumber){
        this.dateRental = dateRental;
        this.custNumber = custNumber;
        this.vehNumber = vehNumber;
    }
        
    public Rental(int renNumber, String dateRental, int custNumber, int vehNumber){
        this.rentalNumber = renNumber;
        this.dateRental = dateRental;
        this.custNumber = custNumber;
        this.vehNumber = vehNumber;
    }

    public int getRentalNumber() {
        return rentalNumber;
    }

    public void setRentalNumber(int rentalNumber) {
        this.rentalNumber = rentalNumber;
    }

    public String getDateRental() {
        return dateRental;
    }

    public void setDateRental(String dateRental) {
        this.dateRental = dateRental;
    }

    public String getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(String dateReturned) {
        this.dateReturned = dateReturned;
    }


    public double getTotalRental() {
        return totalRental;
    }

    public void setTotalRental(double totalRental) {
        this.totalRental = totalRental;
    }

    public int getCustNumber() {
        return custNumber;
    }

    public void setCustNumber(int custNumber) {
        this.custNumber = custNumber;
    }

    public int getVehNumber() {
        return vehNumber;
    }

    public void setVehNumber(int vehNumber) {
        this.vehNumber = vehNumber;
    }

    @Override
    public String toString() {
        return "Rental{" + "rentalNumber=" + rentalNumber + ", dateRental=" + dateRental + ", dateReturned=" + dateReturned + ", pricePerDay=" + pricePerDay + ", totalRental=" + totalRental + ", custNumber=" + custNumber + ", vehNumber=" + vehNumber + '}';
    }
    
    
    
}       //end class
