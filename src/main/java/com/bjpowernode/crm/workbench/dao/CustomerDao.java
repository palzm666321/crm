package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Customer;

import java.util.List;

public interface CustomerDao {

    Customer getCustomerByName(String commpany);

    int insert(Customer customer);

    List<String> getCustomerNameList(String name);
}
