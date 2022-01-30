package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.exception.LoginException;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {

    private UserDao userDao= SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    public User login(String loginAct, String loginPwd, String ip)throws Exception {

        Map<String,String> map=new HashMap<String,String>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);

        User user=userDao.UserLogin(map);

        if (user == null){
            throw new LoginException("请输入正确的用户名和密码");
        }
        if (user.getExpireTime().compareTo(DateTimeUtil.getSysTime())<0){
            throw new LoginException("已过期");
        }
        if ("0".equals(user.getLockState())){
            throw new LoginException("已锁定");
        }
        if (!user.getAllowIps().contains(ip)){
            throw new LoginException("ip地址无效");
        }

        return user;

    }

    @Override
    public List<User> array() {
        return userDao.array();
    }

    @Override
    public List<User> getOwnerList() {
        return userDao.getOwnerList();
    }
}
