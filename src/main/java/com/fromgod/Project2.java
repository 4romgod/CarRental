package com.fromgod;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.fromgod.client.CreateCustomerSer;
import com.fromgod.client.CreateVehicleSer;

/**
 *
 * @author 217301827
 */
public class Project2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CreateCustomerSer cusSer = new CreateCustomerSer();
        CreateVehicleSer vehSer = new CreateVehicleSer();
        
        cusSer.openFile();
        cusSer.writeObjects();
        cusSer.closeFile();
        
        vehSer.openFile();
        vehSer.writeObjects();
        vehSer.closeFile();

    }
    
}
