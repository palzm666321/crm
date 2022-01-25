package com.bjpowernode.crm.test;

import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.service.ClueService;
import com.bjpowernode.crm.workbench.service.impl.ClueServiceImpl;

public class test1 {

    public static void main(String[] args) {
        ClueService dvs= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Clue c=new Clue();
        c.setId(UUIDUtil.getUUID());
        System.out.println(dvs.insertClue(c));

    }

}
