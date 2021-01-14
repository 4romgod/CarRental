package com.fromgod.server.services;

import com.fromgod.pojo.Customer;
import com.fromgod.pojo.Vehicle;
import com.fromgod.server.CrudCustomerImpl;
import com.fromgod.server.CrudVehicleImpl;

import java.io.ObjectOutputStream;

public class ServiceVehicle {

    CrudVehicleImpl crudVehicle = CrudVehicleImpl.getCrudVehicleInstance();

    public void insertVehicle(Vehicle veh, ObjectOutputStream output) throws Exception {
        crudVehicle.createVehicle(veh);
        output.writeObject("Server: vehicle object received!");
        output.flush();
    }

    public void writeListVehicles(ObjectOutputStream output) throws Exception {
        output.writeObject(crudVehicle.getListVehicles());
        output.flush();
    }


}
