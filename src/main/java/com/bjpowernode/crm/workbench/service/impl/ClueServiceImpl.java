package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ClueDao;
import com.bjpowernode.crm.workbench.dao.DicTypeDao;
import com.bjpowernode.crm.workbench.dao.DicValueDao;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.service.ClueService;
import com.bjpowernode.crm.workbench.service.DicValueService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClueServiceImpl implements ClueService {

    private ClueDao clueDao= SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);

    @Override
    public boolean insertClue(Clue c) {
        return clueDao.insertClue(c) == 1;
    }

    @Override
    public Clue getClueById(String id) {
        return clueDao.getClueById(id);
    }

    @Override
    public String getActivityById(String id) {
        return null;
    }

    @Override
    public List<ClueActivityRelationVo> getActivityAndClueRelationList(String id) {
        return clueDao.getActivityAndClueRelation(id);
    }

    @Override
    public boolean deleteActivityAndClueRelationById(String id) {
        return clueDao.deleteActivityAndClueRelationById(id) == 1 ;
    }
}
