package com.jaydenxiao.androidfire.bean.req;

import com.google.gson.annotations.SerializedName;

public class LoginReq {

    @SerializedName("appid")
    public String appid;
    @SerializedName("password")
    public String password;
    @SerializedName("phone")
    public String phone;

}
