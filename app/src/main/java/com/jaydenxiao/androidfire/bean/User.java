package com.jaydenxiao.androidfire.bean;

import com.google.gson.annotations.SerializedName;

import android.text.TextUtils;

/**
 * des:
 * Created by xsf
 * on 2016.09.9:54
 */
public class User{

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
}
