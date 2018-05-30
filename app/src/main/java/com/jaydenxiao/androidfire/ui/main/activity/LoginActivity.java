package com.jaydenxiao.androidfire.ui.main.activity;

import com.jaydenxiao.androidfire.R;
import com.jaydenxiao.androidfire.api.Api;
import com.jaydenxiao.androidfire.app.AppConstant;
import com.jaydenxiao.androidfire.bean.User;
import com.jaydenxiao.androidfire.bean.rep.BaseRep;
import com.jaydenxiao.androidfire.bean.req.LoginReq;
import com.jaydenxiao.androidfire.bean.req.RegisterReq;
import com.jaydenxiao.androidfire.db.CacheManager;
import com.jaydenxiao.androidfire.ui.main.activity.login.RobinActivity;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.jaydenxiao.common.security.Md5Security;

import android.app.Activity;
import android.content.Intent;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class LoginActivity extends RobinActivity {

    @Override
    public void initView() {
        super.initView();

        disableSocialLogin();
        setLoginTitle(getString(R.string.app_name));
        setSignupTitle("Welcome to Robin");
        setForgotPasswordTitle("Forgot Password");
    }

    public static void start(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onLogin(String phone, String password) {

        final LoginReq  req = new LoginReq();
        req.appid = AppConstant.APPID;
        req.password = Md5Security.getMD5(password);
        req.phone = phone;
        Api.getApi().login(req).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<BaseRep<User>>() {
                @Override
                public void call(BaseRep<User> userBaseRep) {
                    if (userBaseRep.status == 200 ) {
                        loginFinish(userBaseRep.data);
                        // 登录成功后，保存一下登录信息
                        CacheManager.getInstance().cacheSys(req);
                    } else {
                        ToastUitl.showLong(userBaseRep.message);
                    }
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    LogUtils.logd(throwable.getMessage());
                }
            });

    }


    // 注册输入的信息
    @Override
    protected void onSignup(String phone, String passWord, String comfirmPassWord) {
        if (!passWord.equals(comfirmPassWord)) {
            ToastUitl.showShort(R.string.confirm_pw_inconformity);
        } else  {
            RegisterReq req =  new RegisterReq();
            req.phone = phone;
            req.password = Md5Security.getMD5(passWord);
            Api.getApi().reg(req).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseRep<User>>() {
                    @Override
                    public void call(BaseRep<User> userBaseRep) {
                        if (userBaseRep.status == 200 ) {
                            loginFinish(userBaseRep.data);
                        } else {
                            ToastUitl.showLong(userBaseRep.message);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.logd(throwable.getMessage());
                    }
                });
        }
    }

    private void loginFinish(User user) {
        CacheManager.getInstance().cacheUser(user);
        ToastUitl.showShort(R.string.login_success);
        mRxManager.post(AppConstant.LOGIN, user);
        finish();
    }

    @Override
    protected void onForgotPassword(String email) {

    }

    @Override
    protected void onGoogleLogin() {

    }

    @Override
    protected void onFacebookLogin() {

    }
}
