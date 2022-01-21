package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.*;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityListVo;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.AlgorithmConstraints;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class ActivityController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path=req.getServletPath();

        if ("/addActivity.do".equals(path)){
            add(req,resp);
        }else if ("/userlist.do".equals(path)){
            getUserList(req,resp);
        }else if ("/pageList.do".equals(path)){
            getPageList(req,resp);
        }else if ("/deleteActivity.do".equals(path)){
            delete(req,resp);
        }else if ("/selectActivity.do".equals(path)){
            getActivityById(req,resp);
        }else if ("/updateActivity.do".equals(path)){
            UpdateActivityById(req,resp);
        }


    }

    private void UpdateActivityById(HttpServletRequest req, HttpServletResponse resp) {

        ActivityService as=(ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Activity act=new Activity();

        act.setId(req.getParameter("id"));
        act.setOwner(req.getParameter("owner"));
        act.setName(req.getParameter("name"));
        act.setStartDate(req.getParameter("startDate"));
        act.setEndDate(req.getParameter("endDate"));
        act.setCost(req.getParameter("cost"));
        act.setDescription(req.getParameter("describe"));
        act.setEditBy(((User)req.getSession().getAttribute("user")).getName());
        act.setEditTime(DateTimeUtil.getSysTime());

        PrintJson.printJsonFlag(resp,as.update(act));



    }

    private void getActivityById(HttpServletRequest req, HttpServletResponse resp) {

        ActivityService as=(ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Activity act=as.selectBy(req.getParameter("id"));

        PrintJson.printJsonObj(resp,act);


    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) {

        ActivityService as=(ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        String[] ids=req.getParameterValues("id");

        PrintJson.printJsonFlag(resp,as.delete(ids));



    }

    protected void getUserList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserService us=(UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> ulist=us.array();

        PrintJson.printJsonObj(resp,ulist);


    }

    protected void getPageList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        Map<String,Object> map=new HashMap<>();
        map.put("owner",req.getParameter("owner"));
        map.put("name",req.getParameter("name"));
        map.put("startDate",req.getParameter("startDate"));
        map.put("endDate",req.getParameter("endDate"));
        map.put("pageNo",Integer.parseInt(req.getParameter("pageNo")));
        map.put("pageSize",Integer.parseInt(req.getParameter("pageSize")));

        ActivityService as=(ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        ActivityListVo<Activity> vo=as.pageList(map);
        PrintJson.printJsonObj(resp,vo);

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
