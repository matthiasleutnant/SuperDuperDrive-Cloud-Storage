package com.udacity.jwdnd.course1.cloudstorage.model;

public class AbstractForm<T> implements IForm<T> {
    protected Integer id;
    protected Integer userid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
