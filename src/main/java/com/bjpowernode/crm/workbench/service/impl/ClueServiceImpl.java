package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.dao.*;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.service.ClueService;

import java.util.List;
import java.util.Map;

public class ClueServiceImpl implements ClueService {

    private ClueDao clueDao= SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    private TranDao tranDao= SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private ContactsDao contactsDao= SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    private CustomerDao customerDao= SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    private ClueRemarkDao clueRemarkDao= SqlSessionUtil.getSqlSession().getMapper(ClueRemarkDao.class);
    private TranHistoryDao tranHistoryDao= SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);
    private CustomerRemarkDao customerRemarkDao= SqlSessionUtil.getSqlSession().getMapper(CustomerRemarkDao.class);
    private ContactsRemarkDao contactsRemarkDao= SqlSessionUtil.getSqlSession().getMapper(ContactsRemarkDao.class);
    private ClueActivityRelationDao clueActivityRelationDao= SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);
    private ContactsActivityRelationDao contactsActivityRelationDao= SqlSessionUtil.getSqlSession().getMapper(ContactsActivityRelationDao.class);


    @Override
    public boolean insertClue(Clue c) {
        return clueDao.insertClue(c) == 1;
    }

    @Override
    public Clue getClueById(String id) {
        return clueDao.getClueById(id);
    }

    @Override
    public List<ClueActivityRelationVo> getActivityAndClueRelationList(String id) {
        return clueDao.getActivityAndClueRelation(id);
    }

    @Override
    public boolean deleteActivityAndClueRelationById(String id) {
        return clueDao.deleteActivityAndClueRelationById(id) == 1 ;
    }

    @Override
    public List<ClueActivityRelationVo> getActivityRelationLikeList(Map<String,Object> map) {
        return clueDao.getActivityRelationLikeList(map);
    }

    @Override
    public boolean insertRelation(String cid,String[] arr) {

        ClueActivityRelation car=new ClueActivityRelation();
        boolean flag=false;
        for(int i=0;i<arr.length;i++){
            car.setId(UUIDUtil.getUUID());
            car.setClueId(cid);
            car.setActivityId(arr[i]);
            flag=clueDao.insertRelation(car) == 1;
        }

        return flag;
    }

    @Override
    public boolean insertContact(String clueId, Tran t, String createBy) {

        boolean flag=true;
        //(1) 获取到线索id，通过线索id获取线索对象（线索对象当中封装了线索的信息）
        Clue c=clueDao.getClueId(clueId);

        String commpany=c.getCompany();
        //(2) 通过线索对象提取客户信息，当该客户不存在的时候，新建客户（根据公司的名称精确匹配，判断该客户是否存在！）

        Customer customer=customerDao.getCustomerByName(commpany);

        if (customer==null){
            customer=new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setCreateBy(createBy);
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setAddress(c.getAddress());
            customer.setWebsite(c.getWebsite());
            customer.setPhone(c.getPhone());
            customer.setContactSummary(c.getContactSummary());
            customer.setDescription(c.getDescription());
            customer.setNextContactTime(c.getNextContactTime());
            customer.setOwner(c.getOwner());
            customer.setName(commpany);

            int count=customerDao.insert(customer);
            if (count!=1){
                flag=false;
            }

        }


        //(3) 通过线索对象提取联系人信息，保存联系人

        Contacts contacts=new Contacts();
        contacts.setId(UUIDUtil.getUUID());
        contacts.setFullname(c.getFullname());
        contacts.setCreateBy(createBy);
        contacts.setCreateTime(DateTimeUtil.getSysTime());
        contacts.setAddress(c.getAddress());
        contacts.setContactSummary(c.getContactSummary());
        contacts.setDescription(c.getDescription());
        contacts.setNextContactTime(c.getNextContactTime());
        contacts.setOwner(c.getOwner());
        contacts.setAppellation(c.getAppellation());
        contacts.setEmail(c.getEmail());
        contacts.setJob(c.getJob());
        contacts.setSource(c.getSource());
        contacts.setMphone(c.getMphone());
        contacts.setCustomerId(customer.getId());

        int count1=contactsDao.insert(contacts);
        if (count1!=1){
            flag=false;
        }


        //(4) 线索备注转换到客户备注以及联系人备注

        List<ClueRemark> clueRemarkList= clueRemarkDao.selectByClueId(clueId);

        for (ClueRemark clueRemark:clueRemarkList){

            String note=clueRemark.getNoteContent();

            CustomerRemark customerRemark=new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(DateTimeUtil.getSysTime());
            customerRemark.setCustomerId(customer.getId());
            customerRemark.setNoteContent(note);
            customerRemark.setEditFlag("0");
            int count2=customerRemarkDao.insert(customerRemark);
            if (count2!=1){
                flag=false;
            }

            ContactsRemark contactsRemark=new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(DateTimeUtil.getSysTime());
            contactsRemark.setContactsId(contacts.getId());
            contactsRemark.setNoteContent(note);
            contactsRemark.setEditFlag("0");
            int count3=contactsRemarkDao.insert(customerRemark);
            if (count3!=1){
                flag=false;
            }

        }


        //(5) “线索和市场活动”的关系转换到“联系人和市场活动”的关系
        List<ClueActivityRelation> clueActivityRelationList=clueActivityRelationDao.selectByClueId(clueId);

        for (ClueActivityRelation clueActivityRelation:clueActivityRelationList){

            String activityId=clueActivityRelation.getActivityId();

            ContactsActivityRelation contactsActivityRelation=new ContactsActivityRelation();
            contactsActivityRelation.setActivityId(activityId);
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setContactsId(contacts.getId());
            int count4=contactsActivityRelationDao.insert(contactsActivityRelation);
            if (count4!=1){
                flag=false;
            }

        }

        //(6) 如果有创建交易需求，创建一条交易

        if (t!=null){

            t.setSource(c.getSource());
            t.setOwner(c.getOwner());
            t.setNextContactTime(c.getNextContactTime());
            t.setCustomerId(contacts.getId());
            t.setContactSummary(c.getContactSummary());
            t.setContactsId(contacts.getId());
            int count5=tranDao.insert(t);
            if (count5!=1){
                flag=false;
            }
            //(7) 如果创建了交易，则创建一条该交易下的交易历史
            TranHistory th=new TranHistory();
            th.setId(UUIDUtil.getUUID());
            th.setCreateBy(createBy);
            th.setCreateTime(DateTimeUtil.getSysTime());
            th.setExpectedDate(t.getExpectedDate());
            th.setMoney(t.getMoney());
            th.setTranId(t.getId());
            th.setStage(t.getStage());

            int count6=tranHistoryDao.insert(th);
            if (count6!=1){
                flag=false;
            }

        }


        //(8) 删除线索备注

        for (ClueRemark clueRemark:clueRemarkList){

            int count7=clueRemarkDao.delete(clueRemark);
            if (count7!=1){
                flag=false;
            }

        }

        //(9)  删除线索和市场活动的关系
        for (ClueActivityRelation clueActivityRelation:clueActivityRelationList){

            System.out.println(clueActivityRelation);
            int count8=clueActivityRelationDao.delete(clueActivityRelation);
            if (count8!=1){
                flag=false;
            }

        }


        //(10) 删除线索

        int count9=clueDao.delete(clueId);
        if (count9!=1){
            flag=false;
        }


        return flag;
    }

}
