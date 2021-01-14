package com.fromgod.pojo;

import java.io.Serializable;
public class Vehicle implements Serializable{
    
    private int vehicleNumber;
    
    private String make;
    private String category;
    private double rentalPrice;
    private boolean availableForRent;
    
    //default constructor
    public Vehicle(){
        vehicleNumber = 0;
        make = "";
        category = "";
        rentalPrice = 0;
        availableForRent = false;   
    }
    
    //constructor that takes 4 arguments to initialize the instance variables
    public Vehicle(String m, int cat, double amt, boolean avail){
        setMake(m);
        setCategory(cat);
        setAvailableForRent(avail);
        setRentalPrice(amt);
    }
    
    
    public Vehicle(int vehNum, String m, int cat, double price, boolean avail){
        setVehicleNumber(vehNum);
        setMake(m);
        setCategory(cat);
        setRentalPrice(price);
        setAvailableForRent(avail);
    }
    
    
    public Vehicle(String m, int cat){
        setMake(m);
        setCategory(cat);
        setAvailableForRent(true);
    }
    
    
    public void setVehicleNumber(int vehNum){
        this.vehicleNumber = vehNum;
    }
    
    // set methods
    public void setMake(String m){
        make = m;
    }
    
    public void setRentalPrice(double amt){
        rentalPrice = amt;
    }
    
    public void setCategory(int sCategory){
        switch(sCategory){
            case 1:
                category = "Sedan";
                rentalPrice = 450;
                break;
            case 2:
                category = "SUV";
                rentalPrice = 500;
                break;
        }       //end switch
    }
    
    public void setAvailableForRent(boolean a){
        availableForRent = a;
    }
    
    
    public int getVehicleNumber(){
        return vehicleNumber;
    }
    
    //get methods
    public String getMake(){
        return make;
    }
    
    public String getCategory(){
        return category;
    }
    
    public double getRentalPrice(){
        return rentalPrice;
    }
      
    //checks if the vehicle is available
    public boolean isAvailableForRent(){
        return availableForRent;
    }
       
    public String toString(){
        return String.format("%-15s\t%-15s\t%.2f\t\t%-15s",getMake(),getCategory(), getRentalPrice(), new Boolean(isAvailableForRent()).toString());
    }
    
}       //end class