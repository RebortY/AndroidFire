package com.jaydenxiao.androidfire.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

import android.text.TextUtils;

/**
 * des:
 * Created by xsf
 * on 2016.09.9:54
 */
public class User implements Serializable{

    @SerializedName("appid")
    public int appId;
    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("phone")
    public String phone;
    @SerializedName("sex")
    public int sex;
    @SerializedName("token")
    public String token;

    public boolean isLogin() {
        return TextUtils.isEmpty(token);
    }

    @Override
    public String toString() {
        return "User{" +
            "appId=" + appId +
            ", id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", phone='" + phone + '\'' +
            ", sex=" + sex +
            ", token='" + token + '\'' +
            '}';
    }
}
