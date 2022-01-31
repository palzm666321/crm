package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.*;

import java.util.List;
import java.util.Map;

public interface TranService{


 List<Activity> getActivityLikeList(String name);

 List<Activity> getContactsLikeList(String name);

    List<String> getCustomerNameList(String name);

    boolean insert(Tran t,String customerName);

    Tran getTranById(String id);

    List<TranHistory> getTranHistoryByIdList(String tranId);

    Map<String, Object> changStage(Tran t);

    Map<String, Object> echarts();
}
