package com.bjpowernode.crm.web.linstener;

import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.workbench.domain.DicValue;
import com.bjpowernode.crm.workbench.service.DicValueService;
import com.bjpowernode.crm.workbench.service.impl.DicValueServiceImpl;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    }


}
