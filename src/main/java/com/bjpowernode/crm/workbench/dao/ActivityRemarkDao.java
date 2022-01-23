package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {

    int deleteCount(String ids[]);

    int deleteByIds(String ids[]);

    List<ActivityRemark> getRemarkList();

    int deleteRemark(String id);

    int insertRemark(ActivityRemark ar);

    ActivityRemark selectById(String id);

    int updateRemark(ActivityRemark ar);
}
