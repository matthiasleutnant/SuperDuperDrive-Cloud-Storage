package com.udacity.jwdnd.course1.cloudstorage.model;

public abstract class AbstractModel {
    protected Integer userid;
    protected Integer id;


    public Integer getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
