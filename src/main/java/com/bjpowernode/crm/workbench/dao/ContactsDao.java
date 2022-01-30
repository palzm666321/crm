package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Contacts;

import java.util.List;

public interface ContactsDao {

    int insert(Contacts contacts);

    List<Activity> getContactsLikeList(String name);
}
