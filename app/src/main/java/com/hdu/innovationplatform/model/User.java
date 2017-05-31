package com.hdu.innovationplatform.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * com.hdu.innovationplatform.model
 * Created by 73958 on 2017/5/24.
 */

public class User {

    private String userIconPath;
    private Bitmap userIcon;

    private String userId;
    private String username;
    private String password;
    private String name;
    private String sex;
    private String school_num;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String userId) {
        this(username, password);
        this.userId = userId;
    }

    public String getUserIconPath() {
        return userIconPath;
    }

    public void setUserIconPath(String userIconPath) {
        this.userIconPath = userIconPath;
        if(this.userIconPath != "null"){
            //LogUtil.d(this.userIconPath);
            this.userIcon = BitmapFactory.decodeFile(userIconPath);
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Bitmap getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(Bitmap userIcon) {
        this.userIcon = userIcon;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSchool_num() {
        return school_num;
    }

    public void setSchool_num(String school_num) {
        this.school_num = school_num;
    }
}
