package com.thinkerwolf.frameworkstudy.dwr;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

@DataTransferObject
public class User {

    @RemoteProperty
    private String userid;
    @RemoteProperty
    private String username;

    public User(String userid, String username) {
        super();
        this.userid = userid;
        this.username = username;
    }

    @RemoteProperty
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @RemoteProperty
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
