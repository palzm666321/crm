package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Customer;

public interface CustomerDao {

    Customer getCustomerByName(String commpany);

    int insert(Customer customer);
}
