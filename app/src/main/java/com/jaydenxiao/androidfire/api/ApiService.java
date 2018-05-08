package com.jaydenxiao.androidfire.api;

import com.google.gson.JsonObject;
import com.jaydenxiao.androidfire.bean.GirlData;
import com.jaydenxiao.androidfire.bean.rep.BaseRep;
import com.jaydenxiao.androidfire.bean.req.LoginReq;
import com.jaydenxiao.androidfire.bean.NewsDetail;
import com.jaydenxiao.androidfire.bean.NewsSummary;
import com.jaydenxiao.androidfire.bean.User;
import com.jaydenxiao.androidfire.bean.VideoData;
import com.jaydenxiao.androidfire.bean.req.RegistReq;
import com.jaydenxiao.androidfire.bean.req.VerifyReq;
import com.jaydenxiao.common.basebean.BaseRespose;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 * des:ApiService
 * Created by xsf
 * on 2016.06.15:47
 */
public interface ApiService {

    @POST("/account/login")
    Observable<BaseRep<User>> login(@Body LoginReq loginReq);

    @POST("/account/register")
    Observable<BaseRep<User>> register(@Body RegistReq registReq);

    @POST("/account/verify")
    Observable<BaseRep<Boolean>> verify(@Body VerifyReq phone);

    @GET("nc/article/{postId}/full.html")
    Observable<Map<String, NewsDetail>> getNewDetail(
            @Header("Cache-Control") String cacheControl,
            @Path("postId") String postId);

    @GET("nc/article/{type}/{id}/{startPage}-20.html")
    Observable<Map<String, List<NewsSummary>>> getNewsList(
            @Header("Cache-Control") String cacheControl,
            @Path("type") String type, @Path("id") String id,
            @Path("startPage") int startPage);

    @GET
    Observable<ResponseBody> getNewsBodyHtmlPhoto(
            @Header("Cache-Control") String cacheControl,
            @Url String photoPath);
    //@Url，它允许我们直接传入一个请求的URL。这样以来我们可以将上一个请求的获得的url直接传入进来，baseUrl将被无视
    // baseUrl 需要符合标准，为空、""、或不合法将会报错

    @GET("data/福利/{size}/{page}")
    Observable<GirlData> getPhotoList(
            @Header("Cache-Control") String cacheControl,
            @Path("size") int size,
            @Path("page") int page);

    @GET("nc/video/list/{type}/n/{startPage}-10.html")
    Observable<Map<String, List<VideoData>>> getVideoList(
            @Header("Cache-Control") String cacheControl,
            @Path("type") String type,
            @Path("startPage") int startPage);

    // 获取开屏图


}
