package com.fromgod.server.services;

import com.fromgod.pojo.Customer;
import com.fromgod.server.CrudCustomerImpl;

import java.io.ObjectOutputStream;

public class ServiceCustomer {

    CrudCustomerImpl crudCustomer = CrudCustomerImpl.getCrudCustomerInstance();

    public void insertCustomer(Customer cus, ObjectOutputStream output) throws Exception {
        crudCustomer.createCustomer(cus);
        output.writeObject("Server: customer object received!");
        output.flush();
    }

    public void writeListCustomers(ObjectOutputStream output) throws Exception {
        output.writeObject(crudCustomer.getListCustomers());
        output.flush();
    }


}
