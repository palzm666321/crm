package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranHistoryDao {

    int insert(TranHistory t);

    List<TranHistory> getTranHistoryByIdList(String tranId);
}
