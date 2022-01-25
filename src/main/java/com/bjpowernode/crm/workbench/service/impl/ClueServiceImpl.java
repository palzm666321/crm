package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
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
    public List<ClueActivityRelationVo> getActivityAndClueRelationList(String id) {
        return clueDao.getActivityAndClueRelation(id);
    }

    @Override
    public boolean deleteActivityAndClueRelationById(String id) {
        return clueDao.deleteActivityAndClueRelationById(id) == 1 ;
    }

    @Override
    public List<ClueActivityRelationVo> getActivityRelationLikeList(Map<String,Object> map) {
        return clueDao.getActivityRelationLikeList(map);
    }

    @Override
    public boolean insertRelation(String cid,String[] arr) {

        ClueActivityRelation car=new ClueActivityRelation();
        boolean flag=false;
        for(int i=0;i<arr.length;i++){
            car.setId(UUIDUtil.getUUID());
            car.setClueId(cid);
            car.setActivityId(arr[i]);
            flag=clueDao.insertRelation(car) == 1;
        }

        return flag;
    }
}
