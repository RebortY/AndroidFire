package com.jaydenxiao.androidfire.bean.req;

import com.google.gson.annotations.SerializedName;

public class VerifyReq {
    @SerializedName("phone")
    public String phone;
    public VerifyReq(String phone) {
        this.phone = phone;
    }
}
