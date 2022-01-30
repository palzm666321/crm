package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityListVo;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.domain.Tran;
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
        }

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
   //    t.setPossibility(req.getParameter("customerName"));

        tranService.insert(t,customerName);

        resp.sendRedirect(req.getContextPath()+"/workbench/transaction/index.jsp");

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
