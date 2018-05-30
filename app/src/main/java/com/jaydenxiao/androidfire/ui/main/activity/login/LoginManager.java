package com.jaydenxiao.androidfire.ui.main.activity.login;

import com.jaydenxiao.androidfire.bean.User;
import com.jaydenxiao.androidfire.db.CacheManager;

import android.text.TextUtils;

public class LoginManager {


    public static boolean checkLogin() {
        User user =  CacheManager.getInstance().getCacheUser();
        if (user == null || TextUtils.isEmpty(user.token)) {
            return false;
        }
        return true;
    }

}
