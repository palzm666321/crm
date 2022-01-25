package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelationVo;
import com.bjpowernode.crm.workbench.domain.DicValue;

import java.util.List;
import java.util.Map;

public interface ClueService {

   boolean insertClue(Clue c);

    Clue getClueById(String id);

    String getActivityById(String id);

    List<ClueActivityRelationVo> getActivityAndClueRelationList(String id);

    boolean deleteActivityAndClueRelationById(String id);
}
