package com.bjpowernode.crm.test;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class test {

    public static void main(String[] args) {

        UserDao userDao= SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
        ActivityService as=(ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        /*
        //查询表
        List<User> list=userDao.array();
        list.forEach(i -> System.out.println(i.getName()));
        */


/*

        //分页查询
        Map<String,Object> map=new HashMap<>();
        map.put("pageNo",1);
        map.put("pageSize",2);
        System.out.println(as.pageList(map));
*/


/*

        //删除操作
        ActivityRemarkDao actdaoRemarkDao=SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);

        String ids[]={"e91b69da47f24b9599c78caa283e2742","efc066b605174d54bbcaec7230463271"};
        System.out.println(actdaoRemarkDao.deleteCount(ids));
        System.out.println(as.delete(ids));
*/


       // System.out.println(as.selectBy("16689caa68134f2a8691f602b9187029"));

        //修改操作
   /*     Activity ac=new Activity();
        ac.setId("348c0e214990482ba7122fb8bdbe8454");
        ac.setName("hello");
        ac.setOwner("王五");

        System.out.println(as.update(ac));
*/

    }

}
