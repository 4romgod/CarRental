package com.fromgod.server.interfaces;

import com.fromgod.pojo.Customer;
import com.fromgod.pojo.Rental;
import com.fromgod.pojo.Vehicle;

import java.util.ArrayList;

public interface CrudRental {

    void createRental(Rental ren);

    ArrayList read();

    void updateRental(Rental rental);

    void deleteRental(Rental rental);
}
