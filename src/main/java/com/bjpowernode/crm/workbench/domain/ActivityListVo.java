package com.bjpowernode.crm.workbench.domain;

import java.util.List;

public class ActivityListVo<T>{

    private int total;
    private List<T> list;

    public int getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "ActivityListVo{" +
                "total=" + total +
                ", list=" + list +
                '}';
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
