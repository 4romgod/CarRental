package com.fromgod.server;

import com.fromgod.client.CreateCustomerSer;

public class RunCreateCustomerSer
{
    public static void main(String [] args)
    {
        CreateCustomerSer runCustSer = new CreateCustomerSer();
        runCustSer.openFile();
        runCustSer.writeObjects();
        runCustSer.closeFile();
    }
}