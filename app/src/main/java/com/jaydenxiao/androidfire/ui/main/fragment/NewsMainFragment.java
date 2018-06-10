package com.jaydenxiao.androidfire.ui.main.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.jaydenxiao.androidfire.BuildConfig;
import com.jaydenxiao.androidfire.R;
import com.jaydenxiao.androidfire.api.Api;
import com.jaydenxiao.androidfire.app.AppConstant;
import com.jaydenxiao.androidfire.bean.NewsChannelTable;
import com.jaydenxiao.androidfire.bean.rep.BaseRep;
import com.jaydenxiao.androidfire.bean.req.UpdateVersion;
import com.jaydenxiao.androidfire.ui.main.activity.LoginActivity;
import com.jaydenxiao.androidfire.ui.main.activity.login.LoginManager;
import com.jaydenxiao.androidfire.ui.news.activity.NewsChannelActivity;
import com.jaydenxiao.androidfire.ui.main.contract.NewsMainContract;
import com.jaydenxiao.androidfire.ui.news.fragment.NewsFrament;
import com.jaydenxiao.androidfire.ui.main.model.NewsMainModel;
import com.jaydenxiao.androidfire.ui.main.presenter.NewsMainPresenter;
import com.jaydenxiao.androidfire.utils.MyUtils;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.base.BaseFragmentAdapter;
import com.loveplusplus.update.UpdateDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * des:新闻首页首页
 * Created by xsf
 * on 2016.09.16:45
 */
public class NewsMainFragment extends BaseFragment<NewsMainPresenter,NewsMainModel>implements NewsMainContract.View {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.add_channel_iv)
    ImageView addChannelIv;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    private BaseFragmentAdapter fragmentAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.app_bar_news;
    }

    @Override
    public void initPresenter() {
      mPresenter.setVM(this,mModel);
    }

    @Override
    public void initView() {
        mPresenter.lodeMineChannelsRequest();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = getActivity();
                if (activity != null) {
                    LoginActivity.start(getActivity());
                }
//                mRxManager.post(AppConstant.NEWS_LIST_TO_TOP, "");
            }
        });

        if (LoginManager.checkLogin()) {
            fab.setVisibility(View.GONE);
        }
        mRxManager.on(AppConstant.LOGIN, new Action1<Object>() {
            @Override
            public void call(Object o) {
                fab.setVisibility(View.GONE);
            }
        });

        Api.getApi().checkUpdate(BuildConfig.VERSION_CODE)
            .delay(3, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<BaseRep<UpdateVersion>>() {
                @Override
                public void call(BaseRep<UpdateVersion> updateVersionBaseRep) {
                    if (updateVersionBaseRep == null) {
                        return;
                    }
                    if (updateVersionBaseRep.data != null && updateVersionBaseRep.status == 200) {
                        if (updateVersionBaseRep.data.version > BuildConfig.VERSION_CODE) {
                            UpdateDialog.show(getActivity(), updateVersionBaseRep.data.message , updateVersionBaseRep
                                .data.path);
                        }
                    }
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    Log.e("CheckUpdate", throwable.getLocalizedMessage());
                }
            });

    }



    @OnClick(R.id.add_channel_iv)
    public void clickAdd(){
        NewsChannelActivity.startAction(getContext());
    }

    @Override
    public void returnMineNewsChannels(List<NewsChannelTable> newsChannelsMine) {
        if(newsChannelsMine!=null) {
            List<String> channelNames = new ArrayList<>();
            List<Fragment> mNewsFragmentList = new ArrayList<>();
            for (int i = 0; i < newsChannelsMine.size(); i++) {
                channelNames.add(newsChannelsMine.get(i).getNewsChannelName());
                mNewsFragmentList.add(createListFragments(newsChannelsMine.get(i)));
            }
            if(fragmentAdapter==null) {
                fragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager(), mNewsFragmentList, channelNames);
            }else{
                //刷新fragment
                fragmentAdapter.setFragments(getChildFragmentManager(),mNewsFragmentList,channelNames);
            }
            viewPager.setAdapter(fragmentAdapter);
            tabs.setupWithViewPager(viewPager);
            MyUtils.dynamicSetTabLayoutMode(tabs);
            setPageChangeListener();
        }
    }

    private void setPageChangeListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private NewsFrament createListFragments(NewsChannelTable newsChannel) {
        NewsFrament fragment = new NewsFrament();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.NEWS_ID, newsChannel.getNewsChannelId());
        bundle.putString(AppConstant.NEWS_TYPE, newsChannel.getNewsChannelType());
        bundle.putInt(AppConstant.CHANNEL_POSITION, newsChannel.getNewsChannelIndex());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }
}
