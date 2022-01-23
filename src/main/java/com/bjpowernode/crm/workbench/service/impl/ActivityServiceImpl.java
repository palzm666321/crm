package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityListVo;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {

    private ActivityDao activityDao= SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao= SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);


    @Override
    public boolean add(Activity act) {
        return activityDao.add(act) == 1 ? true:false;
    }

    @Override
    public ActivityListVo<Activity> pageList(Map<String ,Object> map) {

        ActivityListVo vo=new ActivityListVo();

        vo.setList(activityDao.pageList(map));

        vo.setTotal(activityDao.count(map));

        return vo;
    }

    @Override
    public int count(Map<String ,Object> map) {
        return activityDao.count(map);
    }

    @Override
    public boolean delete(String[] ids) {

        boolean flag=true;

        //查询需要删除数据的总数量
        int count1=activityRemarkDao.deleteCount(ids);

        //执行删除activityDao_remark表数据操作
        int count2=activityRemarkDao.deleteByIds(ids);

        if (count1 != count2){
            flag=false;
        }

        int count3=activityDao.delete(ids);

        if (count3 != ids.length){
            flag=false;
        }

        //执行删除activity表数据操作
        return flag ;
    }

    @Override
    public Activity selectBy(String id) {
        return activityDao.selectBy(id);
    }

    @Override
    public boolean update(Activity act) {
        return activityDao.updateById(act) == 1;
    }

    @Override
    public Activity selectById(String id) {
        return activityDao.selectById(id);
    }

    @Override
    public List<ActivityRemark> getRemarkList() {
        return activityRemarkDao.getRemarkList();
    }

    @Override
    public boolean deleteRemark(String id) {
        return activityRemarkDao.deleteRemark(id) == 1 ;
    }

    @Override
    public boolean insertRemark(ActivityRemark ar) {
        return activityRemarkDao.insertRemark(ar) == 1;
    }

    @Override
    public ActivityRemark selectRemarkById(String id) {
        return activityRemarkDao.selectById(id);
    }

    @Override
    public Map<String, Object> insertRemarkAndRemarkById(ActivityRemark ar) {
        Map<String,Object> map=new HashMap<>();
        map.put("success",activityRemarkDao.insertRemark(ar) == 1);
        map.put("ar",activityRemarkDao.selectById(ar.getId()));
        return map;
    }

    @Override
    public Map<String, Object> updateRemark(ActivityRemark ar) {

        Map<String,Object> map=new HashMap<>();
        map.put("success",activityRemarkDao.updateRemark(ar) == 1);
        map.put("ar",activityRemarkDao.selectById(ar.getId()));
        return map;
    }


}
