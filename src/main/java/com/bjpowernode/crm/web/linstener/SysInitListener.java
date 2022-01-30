package com.bjpowernode.crm.web.linstener;

import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.workbench.domain.DicValue;
import com.bjpowernode.crm.workbench.service.DicValueService;
import com.bjpowernode.crm.workbench.service.impl.DicValueServiceImpl;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

import static java.util.ResourceBundle.getBundle;


public class SysInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        System.out.println("数据字典开始");

        ServletContext application=sce.getServletContext();

        DicValueService dicValueService= (DicValueService) ServiceFactory.getService(new DicValueServiceImpl());

        Map<String, List<DicValue>> map=dicValueService.getAll();

        Set<String> key=map.keySet();

        key.forEach(i -> application.setAttribute(i,map.get(i)));

        System.out.println("数据字典结束");


        Map<String,String> pMap=new HashMap<String, String>();

        ResourceBundle rb= ResourceBundle.getBundle("Stage2Possibility");

        Enumeration<String> key1=rb.getKeys();

        while (key1.hasMoreElements()){
            String key2=key1.nextElement();
            String value=rb.getString(key2);
            pMap.put(key2,value);
        }
        application.setAttribute("pMap",pMap);


    }


}
