package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityListVo;
import com.bjpowernode.crm.workbench.service.ActivityService;

import java.util.Map;

public class ActivityServiceImpl implements ActivityService {

    private ActivityDao actdao= SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao= SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);


    @Override
    public boolean add(Activity act) {
        return actdao.add(act) == 1 ? true:false;
    }

    @Override
    public ActivityListVo<Activity> pageList(Map<String ,Object> map) {

        ActivityListVo vo=new ActivityListVo();

        vo.setList(actdao.pageList(map));

        vo.setTotal(actdao.count(map));

        return vo;
    }

    @Override
    public int count(Map<String ,Object> map) {
        return actdao.count(map);
    }

    @Override
    public boolean delete(String[] ids) {

        boolean flag=true;

        //查询需要删除数据的总数量
        int count1=activityRemarkDao.deleteCount(ids);

        //执行删除actdao_remark表数据操作
        int count2=activityRemarkDao.deleteByIds(ids);

        if (count1 != count2){
            flag=false;
        }

        int count3=actdao.delete(ids);

        if (count3 != ids.length){
            flag=false;
        }

        //执行删除activity表数据操作
        return flag ;
    }

    @Override
    public Activity selectBy(String id) {
        return actdao.selectById(id);
    }

    @Override
    public boolean update(Activity act) {
        return actdao.updateById(act) == 1;
    }


}
