package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityListVo;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {

    boolean add(Activity act);

    ActivityListVo<Activity> pageList(Map<String ,Object> map);

    int count(Map<String ,Object> map);

    boolean delete(String[] ids);

    Activity selectBy(String id);

    boolean update(Activity act);

    Activity selectById(String id);

    List<ActivityRemark> getRemarkList();

    boolean deleteRemark(String id);

    boolean insertRemark(ActivityRemark ar);

    ActivityRemark selectRemarkById(String id);

    Map<String, Object> insertRemarkAndRemarkById(ActivityRemark ar);

    Map<String, Object> updateRemark(ActivityRemark ar);
}
