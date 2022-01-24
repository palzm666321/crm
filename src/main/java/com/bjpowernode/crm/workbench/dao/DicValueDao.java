package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.DicType;
import com.bjpowernode.crm.workbench.domain.DicValue;

import java.util.List;

public interface DicValueDao {

    List<DicValue> getByCodeList(String code);
}
