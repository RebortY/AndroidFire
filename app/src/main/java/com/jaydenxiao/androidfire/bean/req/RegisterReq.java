package com.jaydenxiao.androidfire.bean.req;

import com.google.gson.annotations.SerializedName;

public class RegisterReq {

    @SerializedName("password")
    public String password;

    @SerializedName("phone")
    public String phone;

    @SerializedName("verifyCode")
    public String verifyCode;


}
