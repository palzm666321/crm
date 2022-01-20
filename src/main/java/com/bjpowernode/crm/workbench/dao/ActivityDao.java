package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {

    int add(Activity act);

    List<Activity> pageList(Map<String ,Object> map);

    int count(Map<String ,Object> map);

}
