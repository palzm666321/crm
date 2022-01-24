package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.service.ClueService;
import com.bjpowernode.crm.workbench.service.DicValueService;
import com.bjpowernode.crm.workbench.service.impl.ClueServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.DicValueServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ClueController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path=req.getServletPath();

        if ("/workbench/clue/insertClue.do".equals(path)){
            insertClue(req,resp);
        }else if ("/workbench/clue/userList.do".equals(path)){
            userList(req,resp);
        }

    }

    private void userList(HttpServletRequest req, HttpServletResponse resp) {

        UserService us=(UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> list=us.array();

        PrintJson.printJsonObj(resp,list);

    }

    private void insertClue(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        System.out.println("添加控制器");

        ClueService dvs= (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        Clue c=new Clue();

        c.setId(UUIDUtil.getUUID());
        c.setFullname(req.getParameter("fullname"));
        c.setAppellation(req.getParameter("appellation"));
        c.setOwner(req.getParameter("owner"));
        c.setCompany(req.getParameter("company"));
        c.setJob(req.getParameter("job"));
        c.setEmail(req.getParameter("email"));
        c.setPhone(req.getParameter("phone"));
        c.setWebsite(req.getParameter("website"));
        c.setMphone(req.getParameter("mphone"));
        c.setState(req.getParameter("state"));
        c.setCreateBy(((User)req.getSession().getAttribute("user")).getName());
        c.setCreateTime(DateTimeUtil.getSysTime());
        c.setDescription(req.getParameter("description"));
        c.setSource(req.getParameter("source"));
        c.setContactSummary(req.getParameter("contactSummary"));
        c.setNextContactTime(req.getParameter("nextContactTime"));
        c.setAddress(req.getParameter("address"));

        PrintJson.printJsonFlag(resp,dvs.insertClue(c));


    }


}
