package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelationVo;

import java.util.List;

public interface ClueDao {
    
    int insertClue(Clue c);

    Clue getClueById(String id);

    List<ClueActivityRelationVo> getActivityAndClueRelation(String id);

    int deleteActivityAndClueRelationById(String id);
}
