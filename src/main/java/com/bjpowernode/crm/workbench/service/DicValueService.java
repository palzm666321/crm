package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.DicValue;

import java.util.List;
import java.util.Map;

public interface DicValueService {

    Map<String, List<DicValue>> getAll();

}
