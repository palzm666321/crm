package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.MD5Util;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path=req.getServletPath();

        if ("/login.do".equals(path)){
            login(req,resp);
        }


    }

    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("进入控制器");
        String loginAct=req.getParameter("loginAct");
        String loginPwd=req.getParameter("loginPwd");

        //密码进行md5加密
        loginPwd= MD5Util.getMD5(loginAct);
        //取得当前用户ip
        String ip=req.getRemoteAddr();
        UserService us=(UserService) ServiceFactory.getService(new UserServiceImpl());
        try {
            User user = us.login(loginAct, loginPwd, ip);
            req.getSession().setAttribute("user",user);
            PrintJson.printJsonFlag(resp,true);

        }catch (Exception e){
            e.printStackTrace();
            String msg=e.getMessage();
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("msg",msg);
            map.put("success",false);
            PrintJson.printJsonObj(resp,map);

        }





    }



}
