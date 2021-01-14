package com.fromgod.server;

import com.fromgod.pojo.Customer;
import com.fromgod.pojo.Rental;
import com.fromgod.pojo.Vehicle;
import com.sun.org.apache.bcel.internal.generic.RET;

import java.sql.*;
import java.util.ArrayList;


public class DatabaseHelper{
    
    Connection con;
    String dbURL = "jdbc:ucanaccess://publisher.mdb";
    
    Statement stmt;
    PreparedStatement pstmt;
    
    private String tableNameVehicle = "VEHICLE_TABLE";
    private String tableNameCustomer = "CUSTOMER_TABLE";
    private String tableNameRental = "RENTAL_TABLE";
    
    //COLUMNS FOR CUSTOMER TABLE
    String cusNum = "CUSTOMER_NUMBER";
    String cusName = "FIRST_NAME";
    String cusSurname = "SURNAME";
    String cusIdNum = "ID_NUMBER";
    String cusPhone = "PHONE_NUMBER";
    String cusCanRent = "CAN_RENT";
    
    //COLUMNS FOR VEHICLE TABLE
    String vehNum = "VEHICLE_NUMBER";
    String vehMake = "MAKE";
    String vehCat = "CATEGORY";
    String vehRenPri = "RENTAL_PRICE";
    String vehAvailable = "AVAILABLE_FOR_RENT";
    
    //COLUMNS FOR RENTAL TABLE
    String renNum = "RENTAL_NUMBER";
    String renDate = "DATE_RENTAL";
    String renDateReturn = "DATE_RETURNED";
    String renPrice = "PRICE_PER_DAY";
    String renTot = "TOTAL_RENTAL";
    String renCusNum = "CUSTOMER_NUMBER";
    String renVehNum = "VEHICLE_NUMBER";


    private static DatabaseHelper myDatabase = new DatabaseHelper();


    public static DatabaseHelper getDatabaseINSTANCE(){
        return myDatabase;
    }

    //**********************************************************SETUP*****************************************************
    //checks if a table already exists in the database
    public Boolean checkTableExist(String name){
        try{
            ResultSet rs = con.getMetaData().getTables(null, null, "%", null);    
            while(rs.next()){
                if(rs.getString(3).equalsIgnoreCase(name)){
                    return true;
                }
            }
        }
        catch(Exception ex){
            System.out.println("checkTableExist(): "+ex.getMessage());
        }
        
        return false;
    }       //end checkTableExist()
    
    
    //create database connection
    public void connectionDB(){   
        try{
            System.out.println("About to connect to DB...");
            con = DriverManager.getConnection(dbURL);
            con.setAutoCommit(true);
            System.out.println("Connection to DB successful");
            
            stmt = con.createStatement();
            System.out.println("Statement initialized successfully");
        }
        catch(Exception ex){
            System.out.println("connectionDB(): "+ex.getMessage());
        }
    }        //end connectionDB()

    //close connection to the database
    public void closeDB(){
        try{
            System.out.println("About to close Statement and con....");
            stmt.close(); // close the Statement to let the database know we're done with it
            con.close(); // close the Connection to let the database know we're done with it
            System.out.println("Statement and Con closed successfully....");
        }
        catch(Exception ex){
            ex.getMessage();
        }       //end catch
    }       //end closeDB
    
    
    //method to create a customer table
    public void createTables(){
//        
//        String dropCustomerTable = "drop table "+tableNameCustomer;
//        String dropVehicleTable = "drop table "+tableNameVehicle;
//        String dropRentalTable = "drop table "+tableNameRental;

        String create_customer_table = "create table "+tableNameCustomer+ " ("+
                cusNum +" AUTOINCREMENT PRIMARY KEY, "+ cusName +" TEXT, "+ cusSurname +" TEXT, "+
                cusIdNum +" TEXT, "+ cusPhone +" TEXT, "+ cusCanRent +" BOOLEAN)";
        
        String create_vehicle_table = "create table "+tableNameVehicle+ " ("+
                vehNum +" AUTOINCREMENT PRIMARY KEY, "+ vehMake +" TEXT, "+ vehCat +" TEXT, "+
                vehRenPri +" FLOAT, "+ vehAvailable +" BOOLEAN)";
        
        String create_rental_table = "create table "+tableNameRental+ " ("+
                renNum +" AUTOINCREMENT PRIMARY KEY, "+ renDate +" TEXT, "+ renDateReturn +" TEXT  , "+
                renPrice +" FLOAT, "+ renTot +" FLOAT, "+ renCusNum +" INTEGER, "+ renVehNum +" INTEGER)";
        
        try{
            if(!checkTableExist(tableNameCustomer)){
                stmt.execute(create_customer_table);
                System.out.println(tableNameCustomer+" Created");
            }
            else{
                System.out.println(tableNameCustomer + " Already Exists");
            }
            
            if(!checkTableExist(tableNameVehicle)){
                stmt.execute(create_vehicle_table);
                System.out.println(tableNameVehicle+" Created");

            }            
            else{
                System.out.println(tableNameVehicle + " Already Exists");
            }
            
            if(!checkTableExist(tableNameRental)){
                stmt.execute(create_rental_table);
                System.out.println(tableNameRental+" Created");
            }            
            else{
                System.out.println(tableNameRental + " Already Exists");
            }
        }
        catch(Exception ex){
            System.out.println("createTables(): "+ex.getMessage());
        }
        
    }      //end createCusTable
    


    //********************************************************************OPERATIONS************************************************
    //method to insert customer into the table
    public void insertCustomer(Customer cus){
        String insert_stmt = "insert into "+tableNameCustomer+ "("+ cusNum +", "+ cusName +", "+ cusSurname +", "+ cusIdNum +", "+ cusPhone +", "+ cusCanRent +") values (?,?,?,?,?,?)";
        
        String search_stmt = "select "+ cusIdNum +" from "+tableNameCustomer;
        
        try{
            ResultSet rs = stmt.executeQuery(search_stmt);
            
            while(rs.next()){
                if(rs.getString(1).equalsIgnoreCase(cus.getIdNum())){
                    return;
                }
            }
            
            pstmt = con.prepareStatement(insert_stmt);
            pstmt.setInt(1, 1);
            pstmt.setString(2, cus.getName());
            pstmt.setString(3, cus.getSurname());
            pstmt.setString(4, cus.getIdNum());
            pstmt.setString(5, cus.getPhoneNum());
            pstmt.setBoolean(6, cus.canRent());
            pstmt.execute();
        }
        catch(Exception ex){
            System.out.println("insertCustomer(): "+ex.getMessage());
        }
    
    }
    
    //method to insert vehicle into the table
    public void insertVehicle(Vehicle veh){
        String insert_stmt = "insert into "+tableNameVehicle+ "("+ vehNum +", "+ vehMake +", "+ vehCat +", "+ vehRenPri +", "+ vehAvailable +") values (?,?,?,?,?)";
        String search_stmt = "select "+ vehMake +" from "+tableNameVehicle;
        
        try{        
            ResultSet rs = stmt.executeQuery(search_stmt);

            while(rs.next()){
                if(rs.getString(1).equalsIgnoreCase(veh.getMake())){
                    return;
                }
            }       //end while loop
            
            pstmt = con.prepareStatement(insert_stmt);
            pstmt.setInt(1, 1);
            pstmt.setString(2, veh.getMake());
            pstmt.setString(3, veh.getCategory());
            pstmt.setDouble(4, veh.getRentalPrice());
            pstmt.setBoolean(5, veh.isAvailableForRent());
            pstmt.execute();
        }
        catch(Exception ex){
            System.out.println("insertVehicle(): "+ex.getMessage());
        }
    
    }
    
        //method to insert rental into the table
    public void insertRental(Rental ren){
        String insert_stmt = "insert into "+tableNameRental+ "("+ renNum +", "+ renDate +", "+ renCusNum +", "+ renVehNum +") values (?,?,?,?)";
        
        try{            
            System.out.println("about to insert rental");
            pstmt = con.prepareStatement(insert_stmt);
            pstmt.setInt(1, 1);
            pstmt.setString(2, ren.getDateRental());
//            pstmt.setString(3, "kdjfhnjk"); //ren.getDateReturned());
//            pstmt.setDouble(4, 10); //ren.getPricePerDay());
//            pstmt.setDouble(5, 50); //ren.getTotalRental());
            pstmt.setInt(3, ren.getCustNumber());
            pstmt.setInt(4, ren.getVehNumber());
            pstmt.execute();
            System.out.println("rental inserted successfully!");
        }
        catch(Exception ex){
            System.out.println("insertRental(): "+ex.getMessage());
        }
    
    }
    
    
    //update a customer
    public void updateCustomer(Customer cus){
        String update_stmt = "update "+tableNameCustomer+" set "+ cusCanRent +"=? where "+ cusNum +"=?";
        
        Boolean canRent;
        if(cus.canRent()==true){
            canRent=false;
        }
        else{
            canRent=true;
        }
        
        try{
            pstmt = con.prepareStatement(update_stmt);
            pstmt.setBoolean(1, canRent);
            pstmt.setInt(2, cus.getCustomerNumber());
            pstmt.executeUpdate();
            System.out.println("Customer updated!");
        }
        catch(Exception ex){
            System.out.println("updateCustomer(): "+ex.getMessage());
        }
    
    }       //end updateCustomer()
    
        //update a customer
    public void updateVehicle(Vehicle veh){
        String update_stmt = "update "+tableNameVehicle+" set "+ vehAvailable +"=? where "+ vehNum +"=?";
        
        Boolean available;
        if(veh.isAvailableForRent()==true){available=false;}
        else{available=true;}
        
        try{
            pstmt = con.prepareStatement(update_stmt);
            pstmt.setBoolean(1, available);
            pstmt.setInt(2, veh.getVehicleNumber());
            pstmt.executeUpdate();
            System.out.println("Vehicle updated!");
        }
        catch(Exception ex){
            System.out.println("updateCustomer(): "+ex.getMessage());
        }
    
    }       //end updateCustomer()
    

    //returns List all customers
    public ArrayList getListCus(){
        ArrayList<Customer> listCus = new ArrayList();
        String all_customers = "select * from "+tableNameCustomer;
        try{
            ResultSet rs = stmt.executeQuery(all_customers);
            if(rs != null){
                while(rs.next()){
                    int cusNum = rs.getInt(1);
                    String fName = rs.getString(2);
                    String sName = rs.getString(3);
                    String idNum = rs.getString(4);
                    String phoneNum = rs.getString(5);
                    Boolean canRent = rs.getBoolean(6);
                    
                    Customer cus = new Customer(cusNum, fName, sName, idNum, phoneNum, canRent);
                    listCus.add(cus);
                }
                return listCus;
            }
        }
        catch(Exception ex){
            System.out.println("allCustomers(): "+ex.getMessage());
        }
        return listCus;
    }
    
    //return list all Vehicle of selected category
    public ArrayList getListVeh(){
        ArrayList<Vehicle> listVeh = new ArrayList();
        String all_vehicles = "select * from "+tableNameVehicle;
        
        try{
            ResultSet rs = stmt.executeQuery(all_vehicles);
//            pstmt.setString(1, cat);
//            pstmt.execute();
            if(rs != null){
                while(rs.next()){
                    int vehNum = rs.getInt(1);
                    
                    String vehMake = rs.getString(2);
                    
                    int vehCat;
                    if(rs.getString(3).equalsIgnoreCase("suv")){
                         vehCat=2;
                    }
                    else{vehCat = 1;}
                    
                    double rentPrice = rs.getDouble(4);
                    Boolean available = rs.getBoolean(5);

                    Vehicle veh = new Vehicle(vehNum, vehMake, vehCat, rentPrice, available);
                    
                    listVeh.add(veh);
                }       //end while
                
                return listVeh;
            }       //end if
        }       //end try
        catch(Exception ex){
            System.out.println("getListVeh(): "+ex.getMessage());
        }
        
        return listVeh;
    }      //end getListCat()
    
    
        //return list all Vehicle of selected category
    public ArrayList getListRen(){
        ArrayList<Rental> listRen = new ArrayList();
        String all_rentals = "select * from "+tableNameRental;
        
        try{
            ResultSet rs = stmt.executeQuery(all_rentals);

            if(rs != null){
                while(rs.next()){
                    int renNum = rs.getInt(1);
                    
                    String renDate = rs.getString(2);                   
                    String renReturn = rs.getString(3);
                    
                    double renPrice = rs.getDouble(4);
                    double totalPrice = rs.getDouble(5);
                    
                    int renCusNum = rs.getInt(6);
                    int renVehNum = rs.getInt(7);
                    Vehicle veh = getVeh(renVehNum);

                    Rental ren = new Rental();
                    
                    ren = new Rental(renNum, renDate, "", 0, renCusNum, renVehNum);
                    
                    listRen.add(ren);
                }       //end while
                
                return listRen;
            }       //end if
        }       //end try       //end try
        catch(Exception ex){
            System.out.println("getListVeh(): "+ex.getMessage());
        }
        
        return listRen;
    }      //end getListCat()
    
    
    
    //returns  customers for Id
    public Customer getCus(int cusNum){
        String cus_stmt = "select * from "+tableNameCustomer;
        
        Customer cus = new Customer();
        try{
            ResultSet rs = stmt.executeQuery(cus_stmt);
            
            if(rs != null){
                while(rs.next()){
                    int cusNumber = rs.getInt(1);
                    String fName = rs.getString(2);
                    String sName = rs.getString(3);
                    String idNum = rs.getString(4);
                    String phoneNum = rs.getString(5);
                    Boolean canRent = rs.getBoolean(6);

                    cus = new Customer(fName, sName, idNum, phoneNum, canRent);
                    if(cusNum==cusNumber){
                        return cus;
                    }
                }       //end while
            }
        }
        catch(Exception ex){
            System.out.println("getCus(): "+ex.getMessage());
        }
        
        return cus;
    }
    
    
    //returns  customers for Id
    public Vehicle getVeh(int vehNum){
        String veh_stmt = "select * from "+tableNameVehicle;
        
        Vehicle veh = new Vehicle();
        try{
            ResultSet rs = stmt.executeQuery(veh_stmt);
            
            if(rs != null){
                while(rs.next()){
                    int vehNumber = rs.getInt(1);
                    String make = rs.getString(2);

                    String cat = rs.getString(3);

                    int category=1;
                    if(cat.equalsIgnoreCase("sedan")){
                        category = 1;
                    }
                    else if(cat.equalsIgnoreCase("suv")){
                        category = 2;
                    }

                    String price = rs.getString(4);
                    Boolean available = rs.getBoolean(5);

                    veh = new Vehicle(make, category, vehNum, available);

                    if(vehNum==vehNumber){
                        return veh;
                    }
                }       //end while
            }       //end if
        }
        catch(Exception ex){
            System.out.println("getVeh(): "+ex.getMessage());
        }
        
        return veh;
    }

    
}       //END CLASS
