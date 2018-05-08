package com.jaydenxiao.androidfire.ui.main.activity.login;

import com.google.gson.JsonObject;
import com.jaydenxiao.androidfire.R;
import com.jaydenxiao.androidfire.api.Api;
import com.jaydenxiao.androidfire.bean.rep.BaseRep;
import com.jaydenxiao.androidfire.bean.req.VerifyReq;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.jaydenxiao.common.commonwidget.LoadingDialog;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SignupFragment extends Fragment {

    private TextView login;
    private ImageView logo;
    //    private EditText name;
    private EditText phone;
    private EditText password;
    private EditText confirmPassword;

    @Bind(R.id.verify_code_get)
    public Button verify;

    //    private TextInputLayout nameWrapper;
    private TextInputLayout emailWrapper;
    private TextInputLayout passwordWrapper;
    private TextInputLayout confirmPasswordWrapper;
    private Button submit;

    private Typeface typeface;
    private String titleText;
    private Drawable logoDrawable;
    private Bitmap logoBitmap;

    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this, view);

        // Initialize views
        login = (TextView) view.findViewById(R.id.login);
        logo = (ImageView) view.findViewById(R.id.logo);
        //        name = (EditText) view.findViewById(R.id.name);
        phone = (EditText) view.findViewById(R.id.email);
        password = (EditText) view.findViewById(R.id.password);
        confirmPassword = (EditText) view.findViewById(R.id.confirm_password);
        //        nameWrapper = (TextInputLayout) view.findViewById(R.id.wrapper_name);
        emailWrapper = (TextInputLayout) view.findViewById(R.id.wrapper_email);
        passwordWrapper = (TextInputLayout) view.findViewById(R.id.wrapper_password);
        confirmPasswordWrapper = (TextInputLayout) view.findViewById(R.id.wrapper_confirm_password);
        submit = (Button) view.findViewById(R.id.submit);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RobinActivity) getActivity()).startLoginFragment();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fieldsFilled()) {
                    ((RobinActivity) getActivity()).onSignup(phone.getText().toString(), password.getText().toString(),
                                                             confirmPassword.getText().toString());
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Some information is missing.",
                                   Toast.LENGTH_SHORT).show();
                }
            }
        });

        setDefaults();

        return view;
    }

    @OnClick({R.id.verify_code_get})
    public void onClickVerify(View view) {

        String phoneNumber = phone.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            ToastUitl.show("请输入手机号", Toast.LENGTH_SHORT);
            return;
        }
        LoadingDialog.showDialogForLoading(getActivity(), "获取中", false);
        Api.getApi().verify(new VerifyReq(phoneNumber))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<BaseRep<Boolean>>() {
                @Override
                public void call(BaseRep<Boolean> booleanBaseRep) {
                    LogUtils.logd(booleanBaseRep.toString());
                    if (booleanBaseRep.data) {
                        ToastUitl.show("发送成功", Toast.LENGTH_SHORT);
                        verify.setVisibility(View.GONE);
                    }
                    LoadingDialog.cancelDialogForLoading();
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    LogUtils.logd(throwable.getMessage());
                    verify.setVisibility(View.VISIBLE);
                    LoadingDialog.cancelDialogForLoading();
                }
            });
    }

    /**
     * Get title for fragment
     *
     * @param titleText fragment title
     */
    public void setTitle(String titleText) {
        this.titleText = titleText;
    }

    /**
     * Set title for fragment
     */
    private void setTitle() {
        //        title.setText(titleText);
    }

    /**
     * Get drawable image for logo
     *
     * @param drawable drawable logo
     */
    public void setImage(Drawable drawable) {
        logoDrawable = drawable;
    }

    /**
     * Set bitmap image for logo
     *
     * @param bitmap bitmap logo
     */
    public void setImage(Bitmap bitmap) {
        logoBitmap = bitmap;
    }

    /**
     * Set image for logo
     */
    private void setImage() {
        if (logoDrawable != null) {
            logo.setImageDrawable(logoDrawable);
        } else if (logoBitmap != null) {
            logo.setImageBitmap(logoBitmap);
        }
    }

    /**
     * Get custom font for all Views
     *
     * @param typeface custom typeface
     */
    protected void setFont(Typeface typeface) {
        this.typeface = typeface;
    }

    /**
     * Set custom font for all Views
     */
    private void setFont() {
        //        title.setTypeface(typeface);
        login.setTypeface(typeface);
        //        name.setTypeface(typeface);
        phone.setTypeface(typeface);
        password.setTypeface(typeface);
        confirmPassword.setTypeface(typeface);
        //        nameWrapper.setTypeface(typeface);
        emailWrapper.setTypeface(typeface);
        passwordWrapper.setTypeface(typeface);
        confirmPasswordWrapper.setTypeface(typeface);
        submit.setTypeface(typeface);
    }

    private boolean fieldsFilled() {
        return (!(phone.getText().toString().isEmpty() || password.getText().toString().isEmpty()) && (password
                                                                                                           .getText()
                                                                                                           .toString()
                                                                                                           .equals(
                                                                                                               confirmPassword
                                                                                                                   .getText()
                                                                                                                   .toString())));
    }

    private void setDefaults() {
        setTitle();
        setFont();
        setImage();
    }
}
