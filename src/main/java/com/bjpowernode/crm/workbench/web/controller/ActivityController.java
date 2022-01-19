package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.*;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class ActivityController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=req.getServletPath();

        if ("/userlist.do".equals(path)){
            getUserList(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path=req.getServletPath();

        if ("/activity.do".equals(path)){
            add(req,resp);
        }


    }

    protected void getUserList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserService us=(UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> ulist=us.array();

        PrintJson.printJsonObj(resp,ulist);


    }

        protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Activity act=new Activity();
        act.setId(UUIDUtil.getUUID());
        act.setOwner(req.getParameter("owner"));
        act.setName(req.getParameter("name"));
        act.setStartDate(req.getParameter("startDate"));
        act.setEndDate(req.getParameter("endDate"));
        act.setCost(req.getParameter("cost"));
        act.setDescription(req.getParameter("description"));
        act.setCreateTime(DateTimeUtil.getSysTime());
        act.setCreateBy(((User)req.getSession().getAttribute("user")).getName());
        ActivityService as=(ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.add(act);
        PrintJson.printJsonFlag(resp,flag);






    }



}
