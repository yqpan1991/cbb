package com.apollo.cbb.biz.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Panda on 2016/3/6.
 */
public class UserInfo {

    @JSONField(name="userId")
    private int userId;

    @JSONField(name="userType")
    private int usertype;

    @JSONField(name="nickName")
    private String nickName;

    @JSONField(name="userName")
    private String userName;

    @JSONField(name="password")
    private String password;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserInfo(){

    }

    public static final UserInfo parseJSONString(String json){
        return JSON.parseObject(json, UserInfo.class);
    }

    public String toServerJSON(){
        return JSON.toJSONString(this);
    }


    public boolean isAdmin(){
        return usertype == EsUserManager.USER_TYPE_ADMIN;
    }
}
