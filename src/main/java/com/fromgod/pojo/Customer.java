package com.fromgod.pojo;

import java.io.Serializable;

public class Customer implements Serializable{
    
    private int customerNumber;
    
    private String firstName;
    private String surname;
    private String idNum;
    private String phoneNum;
    private boolean canRent;
    
    
    public Customer(){
        customerNumber = 0;
        firstName = "";
        surname = "";
        idNum = "";
        phoneNum = "";
        canRent = false;
    }
    
    public Customer(String fName, String lName, String idNum, String phone, boolean can){
        setName(fName);
        setSurname(lName);
        setPhoneNum(phone);
        setIdNum(idNum);
        setCanRent(can);
    }
    
    public Customer(int cusNum, String fName, String lName, String idNum, String phone, boolean can){
        setCustomerNumber(cusNum);
        setName(fName);
        setSurname(lName);
        setPhoneNum(phone);
        setIdNum(idNum);
        setCanRent(can);
    }
    
    public Customer(String fName, String lName, String idNum, String phone){
        setName(fName);
        setSurname(lName);
        setPhoneNum(phone);
        setIdNum(idNum);
        setCanRent(false);
    }
    
    public void setCustomerNumber(int cusNo){
        this.customerNumber = cusNo;
    }
    
    
    public void setName(String sFName){
        firstName = sFName;
    }
    
    public void setSurname(String sSName){
        surname = sSName;
    }
    
    public void setPhoneNum(String sPhone){
        phoneNum = sPhone;
    }
    public void setIdNum(String id){
        idNum = id;
    }

    public void setCanRent(boolean c){
        canRent = c;
    }
    
    public int getCustomerNumber(){
        return customerNumber;
    }
    
    
    public String getName(){
        return firstName;
    }
    
    public String getSurname(){
        return surname;
    }
    
    public String getPhoneNum(){
        return phoneNum;
    }
    public String getIdNum(){
        return idNum;
    }

    public boolean canRent(){
        return canRent;
    }
    
    public String toString(){
        return String.format("%-15s\t%-15s\t%-15s\t%-15s\t%-10s", getName(), getSurname(),
                getIdNum(),getPhoneNum(), new Boolean(canRent()).toString());
    }      
    
}       //end class