package com.jaydenxiao.androidfire.bean.req;

import com.google.gson.annotations.SerializedName;

public class UpdateVersion {

    @SerializedName(value = "path")
    public String path;
    @SerializedName(value = "version")
    public int version;
    @SerializedName(value = "msg")
    public String message;

    @Override
    public String toString() {
        return "UpdateVersion{" +
            "path='" + path + '\'' +
            ", version='" + version + '\'' +
            '}';
    }
}
