package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityListVo;

import java.util.List;
import java.util.Map;

public interface ActivityService {

    boolean add(Activity act);

    ActivityListVo<Activity> pageList(Map<String ,Object> map);

    int count(Map<String ,Object> map);

}
