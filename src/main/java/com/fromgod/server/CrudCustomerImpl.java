package com.fromgod.server;

import com.fromgod.pojo.Customer;
import com.fromgod.server.interfaces.CrudCustomer;

import java.util.ArrayList;

public class CrudCustomerImpl implements CrudCustomer {

    DatabaseHelper myDatabase = DatabaseHelper.getDatabaseINSTANCE();

    private static CrudCustomerImpl crudCustomer = new CrudCustomerImpl();

    public static CrudCustomerImpl getCrudCustomerInstance(){
        return crudCustomer;
    }

    @Override
    public void createCustomer(Customer cus) {
        myDatabase.insertCustomer(cus);
    }

    @Override
    public ArrayList getListCustomers() {
        return myDatabase.getListCus();
    }

    @Override
    public void updateCustomer(Customer cus) {
        myDatabase.updateCustomer(cus);
    }

    @Override
    public void deleteCustomer(Customer object) {
    }

}
