package com.bjpowernode.crm.test;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.MD5Util;
import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test {

    public static void main(String[] args) {

        UserDao userDao= SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
        ActivityService as=(ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        System.out.println(userDao.array());
        List<User> list=userDao.array();
        list.forEach(i -> System.out.println(i.getName()));
        Map<String,Object> map=new HashMap<>();
        System.out.println(as.pageList(map));

    }

}
