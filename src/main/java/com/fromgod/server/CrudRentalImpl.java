package com.fromgod.server;

import com.fromgod.pojo.Rental;
import com.fromgod.server.interfaces.CrudRental;

import java.util.ArrayList;

public class CrudRentalImpl implements CrudRental {

    DatabaseHelper myDatabase = DatabaseHelper.getDatabaseINSTANCE();

    private static CrudRentalImpl crudRental = new CrudRentalImpl();

    public static CrudRentalImpl getCrudRentalInstance(){
        return crudRental;
    }

    @Override
    public void createRental(Rental ren) {
        myDatabase.insertRental(ren);
    }

    @Override
    public ArrayList read() {
        return myDatabase.getListRen();
    }

    @Override
    public void updateRental(Rental rental) {

    }

    @Override
    public void deleteRental(Rental rental) {

    }
}
