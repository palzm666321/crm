package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.DicTypeDao;
import com.bjpowernode.crm.workbench.dao.DicValueDao;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.DicType;
import com.bjpowernode.crm.workbench.domain.DicValue;
import com.bjpowernode.crm.workbench.service.DicValueService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DicValueServiceImpl implements DicValueService {

    private DicTypeDao dicTypeDao= SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao= SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);


    @Override
    public Map<String,List<DicValue>> getAll() {

        Map<String,List<DicValue>> map=new HashMap<>();

        List<DicType> list=dicTypeDao.getSelect();

        list.forEach(i -> map.put(i.getCode(),dicValueDao.getByCodeList(i.getCode())));

        return map;
    }



}
