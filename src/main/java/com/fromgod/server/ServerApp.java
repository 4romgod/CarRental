package com.fromgod.server;

import com.fromgod.client.CreateCustomerSer;
import com.fromgod.client.CreateVehicleSer;
import com.fromgod.pojo.Customer;
import com.fromgod.pojo.Rental;
import com.fromgod.pojo.Vehicle;
import com.fromgod.server.services.ServiceCustomer;
import com.fromgod.server.services.ServiceVehicle;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerApp {
        
    private ServerSocket server;
    Socket client;
            
    int portNumber = 54321;
    int numCon = 10;
    
    //communication streams for the client and server
    ObjectOutputStream output;
    ObjectInputStream input;
    
    //communication streams for the serialized files
    ObjectInputStream inputSer;
    
    //name of the serialized file
    String fileCusSer = "Customers.ser";
    String fileVehSer = "Vehicles.ser";
    
    String classObj;
    
    //database instance
    DatabaseHelper myDB = DatabaseHelper.getDatabaseINSTANCE();

    ServiceCustomer serviceCustomer = new ServiceCustomer();
    ServiceVehicle serviceVehicle = new ServiceVehicle();
    //CrudCustomerImpl crudCustomer = new CrudCustomerImpl();
    
    
    //method to open server
    public void openServer(){
        try{
            System.out.println("About to open the server...");
            server = new ServerSocket(portNumber, numCon);
            System.out.println("Server is opened successfully.");
            
            myDB.connectionDB();
            myDB.createTables();
            
            //writing from the customer.ser file to the Customer Table
            openSerFiles(fileCusSer);
            readSerObjectsToDB(openSerFiles(fileCusSer));
            
            //writing from the Vehicle.ser file to the Vehicle Table
            openSerFiles(fileVehSer);
            readSerObjectsToDB(openSerFiles(fileVehSer));
            
            System.out.println("Done initializing the tables");
        }
        catch(IOException ioe){
            System.out.println("openServer(): "+ioe.getMessage());
        }
    }      //end openServer
    
    
    //method to listen for client connection
    public void listen(){
        try{            
            System.out.println("Listening for client to connect...");            

            client = server.accept();
            System.out.println("Client is connected. Moving on to process Client...");
            
            processClient();

        }
        catch(IOException ioe){
            System.out.println("listen(): "+ioe.getMessage());
        }
    }      //end listen
    
    //method to process a client
    public void processClient(){
        
        while(true){
            try{
                output = new ObjectOutputStream(client.getOutputStream());
                output.flush();
                input = new ObjectInputStream(client.getInputStream());

                Object obj = input.readObject();                                //receive an object from the client

                if(obj instanceof Customer){
                    Customer cus = (Customer)obj;                               //make our received object a customer object
                    serviceCustomer.insertCustomer(cus, output);
                }
                else if(obj instanceof Vehicle){
                    Vehicle veh = (Vehicle)obj;                                 //make our received object a vehicle object
                    myDB.insertVehicle(veh);
                    output.writeObject("Server: Vehicle object received!");
                    output.flush();
                }
                else if(obj instanceof Rental){
                    Rental ren = (Rental)obj;                                   //make our received object a rental object
                    System.out.println("server: received rental object");
                    myDB.insertRental(ren);                                     //insert it into the database
                    
                    Customer cusUpd = (Customer) myDB.getCus(ren.getCustNumber());         //customer to update
                    Vehicle vehUpd = (Vehicle) myDB.getVeh(ren.getVehNumber());           //vehicle to update
                    
                    myDB.updateCustomer(cusUpd);                                //update the customer table
                    myDB.updateVehicle(vehUpd);                                 //update the vehicle table
                    
                    output.writeObject("Server: Rental object received!");      //confirm object receival
                    output.flush();
                }
                else if(obj instanceof String){                                 //string reads the request and gives a response
                    String req = obj.toString();
                    
                    if(req.equalsIgnoreCase("get customers")){                  //client asks for the list of customers
                        serviceCustomer.writeListCustomers(output);
                    }
                    else if(req.equalsIgnoreCase("get vehicles suv")){              //client asks for the list of vehicles
                        serviceVehicle.writeListVehicles(output);
                    }
                    else if(req.equalsIgnoreCase("get vehicles sedan")){
                        serviceVehicle.writeListVehicles(output);
                    }  
                    else if(req.equalsIgnoreCase("get rentals")){
                        output.writeObject(myDB.getListRen());
                        output.flush();
                    }
                    else if(req.equalsIgnoreCase("exit")){
                        break;
                    }
                }       //end else if()

            }       //end try()
            catch(Exception ex){
                System.out.println("processClient(): "+ex.getMessage());
            }

        }       //end while()
        
    }       //end processClient
    
    
    //method to close server
    public void closeConnection(){
        try{
            System.out.println("Closing Communication Streams...");
            output.close();
            input.close();
            System.out.println("Strams Closed. Closing client connection...");
            client.close();
            myDB.closeDB();
        }
        catch(IOException ioe){
            System.out.println("closeConnection(): "+ioe.getMessage());
        }
    }     //end closeServer
    
    
    
    //-------------------------------READING FROM THE SERIALIZED FILES------------------------------------------
    //method to create the serialized files
    public static void createSerFiles(){
        CreateCustomerSer cusSer = new CreateCustomerSer();
        CreateVehicleSer vehSer = new CreateVehicleSer();
        
        cusSer.openFile();
        cusSer.writeObjects();
        cusSer.closeFile();
    
        vehSer.openFile();
        vehSer.writeObjects();
        vehSer.closeFile();
    }       //end createSerFiles()
    
    
    //method to read the serialized files
    public ObjectInputStream openSerFiles(String fileName){ 
        int fileNameLen = fileName.length();
        classObj = fileName.substring(0, fileNameLen-5);
        
        try{
            inputSer = new ObjectInputStream(new FileInputStream(fileName));
            return inputSer;
        }
        catch(FileNotFoundException fnfe){
            System.out.println("openSerFiles(): "+fnfe.getMessage());
        }
        catch(IOException ioe){
            System.out.println("openSerFiles(): "+ioe.getMessage());
        }
        
        return null;
    }        //end readSerFiles()
    
    //read ser objects
    public void readSerObjectsToDB(ObjectInputStream input){           
        try{
            while(true){
                if(classObj.equalsIgnoreCase("customer")){
                    Customer cus = (Customer) input.readObject();
                    myDB.insertCustomer(cus);
                }
                else if(classObj.equalsIgnoreCase("vehicle")){
                    Vehicle veh = (Vehicle) input.readObject();
                    myDB.insertVehicle(veh);
                }
                
            }       //end while()
            
        }       //end try       //end try
        catch(IOException ioe){
            System.out.println("readSerObjects(): "+ioe.getMessage());
            closeCusSerFiles();
        }
        catch(ClassNotFoundException cnfe){
            System.out.println("readSerObjects(): "+cnfe.getMessage());
        }
    }  //end readSerObjects()
    

    public void closeCusSerFiles(){
        try{
            inputSer.close();
        }
        catch(IOException ioe){
            System.out.println("closeCusSerFiles(): "+ioe.getMessage());
        }
    }       //end closeCusSerFiles()

    //----------------------------------END SERIALIZED FILES------------------------------------------------
            
    
    public static void main(String[] args){
        ServerApp server = new ServerApp();
        createSerFiles();       //creates the serialized files
        server.openServer();       //1.opens server: 2.connect to DB: 3.create Tables: 4.init Tables
        server.listen();        
        System.exit(0);

    }       //end main()
    
    
}       //end class
