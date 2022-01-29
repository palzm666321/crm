package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.*;

import java.util.List;
import java.util.Map;

public interface ClueService {

 /**
  * 添加操作
  * @param c 线索表
  * @return
  */
 boolean insertClue(Clue c);

 /**
  * 根据id查询clue表操作
  * @param id
  * @return
  */
 Clue getClueById(String id);

 /**
  * 根据指定id查询相关市场活动关联的遍历
  * @param id
  * @return
  */
 List<ClueActivityRelationVo> getActivityAndClueRelationList(String id);

 /**
  * 根据指定id删除tbl_clue_activity_relation关联表
  * @param id
  * @return
  */
    boolean deleteActivityAndClueRelationById(String id);

 /**
  * 根据指定id和模糊查询name进行与tbl_clue_activity_relation关联表对应关联的查询
  * @param map id:指定id，name：模糊查询
  * @return 返回模糊查询出与指定id未进行关联的遍历数据
  */
 List<ClueActivityRelationVo> getActivityRelationLikeList(Map<String,Object> map);

 /**
  * 添加关联操作
  * @param cid 线索表id
  * @param arr 市场表id[]
  * @return true，false
  */
 boolean insertRelation(String cid,String[] arr);


 boolean insertContact(String clueId, Tran t,String createBy);
}
