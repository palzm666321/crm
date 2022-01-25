package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelationVo;

import java.util.List;
import java.util.Map;

public interface ClueDao {
    
    int insertClue(Clue c);

    Clue getClueById(String id);

    List<ClueActivityRelationVo> getActivityAndClueRelation(String id);

    int deleteActivityAndClueRelationById(String id);

    List<ClueActivityRelationVo> getActivityRelationLikeList(Map<String,Object> map);

    int insertRelation(ClueActivityRelation car);
}
