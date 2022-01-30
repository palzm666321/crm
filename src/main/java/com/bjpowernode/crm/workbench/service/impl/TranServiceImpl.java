package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.dao.*;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.service.ClueService;
import com.bjpowernode.crm.workbench.service.TranService;

import java.util.List;
import java.util.Map;

public class TranServiceImpl implements TranService {


    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ContactsDao contactsDao = SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);


    @Override
    public List<Activity> getActivityLikeList(String name) {
        return activityDao.getActivityLikeList(name);
    }

    @Override
    public List<Activity> getContactsLikeList(String name) {
        return contactsDao.getContactsLikeList(name);
    }

    @Override
    public List<String> getCustomerNameList(String name) {
        return customerDao.getCustomerNameList(name);
    }

    @Override
    public boolean insert(Tran t,String customerName) {

        Customer customer=customerDao.getCustomerByName(customerName);

        if (customer == null){
            customer=new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setOwner(t.getOwner());
            customer.setCreateBy(t.getCreateBy());
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setNextContactTime(t.getNextContactTime());
            customer.setDescription(t.getDescription());
            customer.setContactSummary(t.getContactSummary());
            customer.setName(t.getName());
            if (customerDao.insert(customer) != 1){
                return false;
            }
       }

        t.setCustomerId(customer.getId());

        return tranDao.insert(t) == 1;
    }
}