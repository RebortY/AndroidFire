package com.jaydenxiao.androidfire.bean.rep;

import com.google.gson.annotations.SerializedName;

public class RegistResp {

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

}
