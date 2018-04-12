package com.jaydenxiao.androidfire.bean.rep;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class BaseRep<T> implements Serializable {

    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public T data;

}
