package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.dao.*;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.service.ClueService;
import com.bjpowernode.crm.workbench.service.TranService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranServiceImpl implements TranService {


    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ContactsDao contactsDao = SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);


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

    @Override
    public Tran getTranById(String id) {
        return tranDao.selectById(id);
    }

    @Override
    public List<TranHistory> getTranHistoryByIdList(String tranId) {
        return tranHistoryDao.getTranHistoryByIdList(tranId);
    }

    @Override
    public Map<String, Object> changStage(Tran t) {


        int count =tranDao.updateChangStage(t);
        if (count != 1){
            return null;
        }


        Tran t1=tranDao.selectById(t.getId());

        t1.setEditBy(t.getEditBy());
        t1.setEditTime(t.getEditTime());
        t1.setPossibility(t.getPossibility());

        TranHistory th=new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setCreateTime(DateTimeUtil.getSysTime());
        th.setStage(t1.getStage());
        th.setCreateBy(t1.getCreateBy());
        th.setPossibility(t1.getPossibility());
        th.setTranId(t1.getId());
        th.setExpectedDate(t1.getExpectedDate());
        th.setMoney(t1.getMoney());

        boolean flag = tranHistoryDao.insert(th) == 1;

        Map<String,Object> map=new HashMap<>();
        map.put("success",flag);
        map.put("t",t1);

        return map;
    }
}