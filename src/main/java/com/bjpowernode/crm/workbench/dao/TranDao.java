package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface TranDao {

    int insert(Tran t);

    Tran selectById(String id);

    int updateChangStage(Tran t);

    int getCount();

    List<Map<String, Object>> getCountMap();
}
