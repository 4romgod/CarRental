package com.fromgod.server.interfaces;

import com.fromgod.pojo.Customer;
import com.fromgod.pojo.Rental;
import com.fromgod.pojo.Vehicle;

import java.util.ArrayList;

public interface CrudVehicle {

    void createVehicle(Vehicle veh);

    ArrayList getListVehicles();

    void updateVehicle(Vehicle veh);

    void deleteVehicle(Vehicle vehicle);
}
