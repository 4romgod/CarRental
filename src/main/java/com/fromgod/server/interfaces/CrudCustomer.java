package com.fromgod.server.interfaces;

import com.fromgod.pojo.Customer;
import com.fromgod.pojo.Rental;
import com.fromgod.pojo.Vehicle;

import java.util.ArrayList;

public interface CrudCustomer {

    void createCustomer(Customer cus);

    ArrayList getListCustomers();

    void updateCustomer(Customer cus);

    void deleteCustomer(Customer object);

}
