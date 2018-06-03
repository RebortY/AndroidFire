package com.jaydenxiao.androidfire.db;

import com.google.gson.Gson;
import com.jaydenxiao.androidfire.bean.User;
import com.jaydenxiao.androidfire.bean.req.LoginReq;
import com.jaydenxiao.common.baseapp.BaseApplication;
import com.mob.tools.utils.SharePrefrenceHelper;

import android.text.TextUtils;

public class CacheManager {

    private static final CacheManager manager = new CacheManager();
    private static final String TABLE_NAME = "user_table";
    private static final String USER_KEY = "user";
    // 用户的登录名和密码
    private static final String USER_SYS = "user_sys_pw";
    private static final String PIC_KEY = "PIC";

    private static volatile User cacheUser = null;
    private SharePrefrenceHelper dbHelper = null;
    private Gson gson = new Gson();

    private CacheManager() {
        dbHelper = new SharePrefrenceHelper(BaseApplication.getAppContext());
        dbHelper.open(TABLE_NAME);
    }

    public static CacheManager getInstance() {
        return manager;
    }


    public void cacheUser(User user) {
        synchronized(CacheManager.class) {
            if (cacheUser == null) {
                cacheUser = user;
            }
            String json = gson.toJson(user);
            dbHelper.putString(USER_KEY, json);
        }
    }

    public User getCacheUser() {
        if(cacheUser == null) {
            String userStr = dbHelper.getString(USER_KEY);
            if (!TextUtils.isEmpty(userStr)) {
                cacheUser = gson.fromJson(userStr, User.class);
            }
        }
        return cacheUser;
    }

    public void cacheSys(LoginReq req) {
        String cacheStr = gson.toJson(req);
        dbHelper.putString(USER_SYS, cacheStr);
    }

    public LoginReq getUserSys() {
        String sysStr =  dbHelper.getString(USER_SYS);
        if (TextUtils.isEmpty(sysStr)) {
            return null;
        }
        return gson.fromJson(sysStr, LoginReq.class);
    }

    public void chachePic(String path) {
        dbHelper.putString(PIC_KEY, path);
    }

    public String getPic() {
        return dbHelper.getString(PIC_KEY);
    }

}
