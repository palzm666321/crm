package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.TranService;
import com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.ClueServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.TranServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TranController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path=req.getServletPath();

        if ("/workbench/transaction/save.do".equals(path)){
            UserOwnerList(req,resp);
        }else if ("/workbench/transaction/ActivityLikeList.do".equals(path)){
            ActivityLikeList(req,resp);
        }else if ("/workbench/transaction/ContactsLikeList.do".equals(path)){
            ContactsLikeList(req,resp);
        }else if ("/workbench/transaction/getCustomerName.do".equals(path)){
            getCustomerName(req,resp);
        }else if ("/workbench/transaction/insertTran.do".equals(path)){
            InsertTran(req,resp);
        }else if ("/workbench/transaction/detail.do".equals(path)){
            detail(req,resp);
        }else if ("/workbench/transaction/TranHistoryByIdList.do".equals(path)){
            TranHistoryByIdList(req,resp);
        }else if ("/workbench/transaction/changStage.do".equals(path)){
            changStage(req,resp);
        }else if ("/workbench/transaction/echarts.do".equals(path)){
            echarts(req,resp);
        }

    }

    private void echarts(HttpServletRequest req, HttpServletResponse resp) {

        TranService tranService= (TranService) ServiceFactory.getService(new TranServiceImpl());

        Map<String,Object> map=tranService.echarts();

        PrintJson.printJsonObj(resp,map);

    }

    private void changStage(HttpServletRequest req, HttpServletResponse resp) {


        TranService tranService= (TranService) ServiceFactory.getService(new TranServiceImpl());
        Map<String,String> pMap= (Map<String, String>) this.getServletContext().getAttribute("pMap");
        String stage=req.getParameter("stage");
        String possibility=pMap.get(stage);
        Tran t=new Tran();
        t.setId(req.getParameter("id"));
        t.setExpectedDate(req.getParameter("expectedDate"));
        t.setMoney(req.getParameter("money"));
        t.setPossibility(possibility);
        t.setStage(stage);
        t.setEditTime(DateTimeUtil.getSysTime());
        t.setEditBy(((User)req.getSession().getAttribute("user")).getName());

        Map<String,Object> map=tranService.changStage(t);

        PrintJson.printJsonObj(resp,map);


    }

    private void TranHistoryByIdList(HttpServletRequest req, HttpServletResponse resp) {

        TranService tranService= (TranService) ServiceFactory.getService(new TranServiceImpl());

        List<TranHistory> list=tranService.getTranHistoryByIdList(req.getParameter("tranId"));

        Map<String,String> map= (Map<String, String>) this.getServletContext().getAttribute("pMap");

        list.forEach(i -> i.setPossibility(map.get(i.getStage())));

        PrintJson.printJsonObj(resp,list);

    }

    private void detail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TranService tranService= (TranService) ServiceFactory.getService(new TranServiceImpl());

        Tran t=tranService.getTranById(req.getParameter("id"));

        Map<String,String> map= (Map<String, String>) this.getServletContext().getAttribute("pMap");

        String possibility=map.get(t.getStage());

        t.setPossibility(possibility);

        req.setAttribute("t",t);

        req.getRequestDispatcher("/workbench/transaction/detail.jsp").forward(req,resp);


    }

    private void InsertTran(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {

        TranService tranService= (TranService) ServiceFactory.getService(new TranServiceImpl());

        Tran t=new Tran();

        t.setId(UUIDUtil.getUUID());
        t.setOwner(req.getParameter("owner"));
        t.setMoney(req.getParameter("money"));
        t.setName(req.getParameter("transactionName"));
        t.setExpectedDate(req.getParameter("expectedClosingDate"));
        t.setCreateBy(((User)req.getSession().getAttribute("user")).getName());
        t.setCreateTime(DateTimeUtil.getSysTime());
        t.setStage(req.getParameter("transactionStage"));
        t.setCustomerId(req.getParameter("customerNameId"));
        t.setType(req.getParameter("transactionType"));
        t.setSource(req.getParameter("clueSource"));
        t.setActivityId(req.getParameter("activitySrcId"));
        t.setContactsId(req.getParameter("contactsNameId"));
        t.setDescription(req.getParameter("describe"));
        t.setContactSummary(req.getParameter("contactSummary"));
        t.setNextContactTime(req.getParameter("nextContactTime"));
        String customerName=req.getParameter("customerName");

        boolean flag=tranService.insert(t,customerName);

        if (flag) {
            resp.sendRedirect(req.getContextPath() + "/workbench/transaction/index.jsp");
        }
    }

    private void getCustomerName(HttpServletRequest req, HttpServletResponse resp) {

        TranService tranService= (TranService) ServiceFactory.getService(new TranServiceImpl());

        PrintJson.printJsonObj(resp,tranService.getCustomerNameList(req.getParameter("name")));

    }

    private void ContactsLikeList(HttpServletRequest req, HttpServletResponse resp) {


        TranService tranService= (TranService) ServiceFactory.getService(new TranServiceImpl());

        PrintJson.printJsonObj(resp,tranService.getContactsLikeList(req.getParameter("name")));

    }

    private void ActivityLikeList(HttpServletRequest req, HttpServletResponse resp) {

        TranService tranService= (TranService) ServiceFactory.getService(new TranServiceImpl());

        PrintJson.printJsonObj(resp,tranService.getActivityLikeList(req.getParameter("name")));

    }

    private void UserOwnerList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

            UserService userService= (UserService) ServiceFactory.getService(new UserServiceImpl());

            req.setAttribute("Auser",userService.array());

            req.getRequestDispatcher("/workbench/transaction/save.jsp").forward(req,resp);


    }


}
