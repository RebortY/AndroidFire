package com.jaydenxiao.androidfire.ui.main.activity;

import com.jaydenxiao.androidfire.R;
import com.jaydenxiao.androidfire.ui.main.activity.login.RobinActivity;

import android.app.Activity;
import android.content.Intent;

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
    protected void onLogin(String email, String password) {
    }

    @Override
    protected void onSignup(String name, String email, String password) {

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
