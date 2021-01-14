package com.fromgod.server;

import com.fromgod.pojo.Vehicle;
import com.fromgod.server.interfaces.CrudVehicle;

import java.util.ArrayList;

public class CrudVehicleImpl implements CrudVehicle {


    DatabaseHelper myDatabase = DatabaseHelper.getDatabaseINSTANCE();

    private static CrudVehicleImpl crudVehicle = new CrudVehicleImpl();

    public static CrudVehicleImpl getCrudVehicleInstance(){
        return crudVehicle;
    }

    @Override
    public void createVehicle(Vehicle veh) {
        myDatabase.insertVehicle(veh);
    }

    @Override
    public ArrayList getListVehicles() {
        return myDatabase.getListVeh();
    }

    @Override
    public void updateVehicle(Vehicle veh) {

    }

    @Override
    public void deleteVehicle(Vehicle vehicle) {

    }
}
