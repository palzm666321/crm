package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityListVo;
import com.bjpowernode.crm.workbench.service.ActivityService;

import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {

    private ActivityDao actdao= SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);

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




}
