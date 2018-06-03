package com.jaydenxiao.androidfire.ui.main.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.text.TextUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jaydenxiao.androidfire.R;
import com.jaydenxiao.androidfire.api.Api;
import com.jaydenxiao.androidfire.bean.rep.BaseRep;
import com.jaydenxiao.androidfire.bean.req.PicReq;
import com.jaydenxiao.androidfire.db.CacheManager;
import com.jaydenxiao.common.base.BaseActivity;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * des:启动页
 * Created by xsf
 * on 2016.09.15:16
 */
public class SplashActivity extends BaseActivity {
    @Bind(R.id.iv_logo)
    ImageView ivLogo;
//    @Bind(R.id.tv_name)
//    TextView tvName;

    @Override
    public int getLayoutId() {
        return R.layout.act_splash;
    }

    @Override
    public void initPresenter() {



        Api.getApi().pic()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<BaseRep<PicReq>>() {
                @Override
                public void call(BaseRep<PicReq> picReqBaseRep) {
                    if (picReqBaseRep.status == 200) {
                        CacheManager.getInstance().chachePic(picReqBaseRep.data.path);
                        loadPic(picReqBaseRep.data.path);
                    }
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
    }


    private void loadPic(String url) {
        Glide.with(SplashActivity.this)
            .load(url)
            .listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target,
                                           boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target,
                                               boolean isFromMemoryCache, boolean isFirstResource) {

                    return false;
                }
            })
            .into(ivLogo);
    }

    @Override
    public void initView() {
        SetTranslanteBar();
        String path = CacheManager.getInstance().getPic();
        if (!TextUtils.isEmpty(path)) {
            loadPic(path);
        }
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0.3f, 1f);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofPropertyValuesHolder(ivLogo, alpha);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimator2);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.setDuration(5000);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                MainActivity.startAction(SplashActivity.this);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animatorSet.start();
    }

}
