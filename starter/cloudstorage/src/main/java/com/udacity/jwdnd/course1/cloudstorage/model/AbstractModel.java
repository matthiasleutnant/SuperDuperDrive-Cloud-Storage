package com.udacity.jwdnd.course1.cloudstorage.model;

public abstract class AbstractModel {
    protected int userid;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
