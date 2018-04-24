package com.suchengkeji.android.liquidgas.api.base;


import com.suchengkeji.android.liquidgas.api.base.cookies.CookieManger;
import com.suchengkeji.android.liquidgas.api.base.intercept.CacheInterceptor;
import com.suchengkeji.android.liquidgas.app.MyApplication;
import com.suchengkeji.android.liquidgas.utils.LogUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @aboutContent: okhttp配置
 * @author： 安
 * @crateTime: 2017/12/21 18:34
 * @mailBox: an.****.life@gmail.com
 * @company: 东莞速成科技有限公司
 */

public class AnBaseApiServiceManger {

    OkHttpClient okHttpClient;

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public AnBaseApiServiceManger() {


        //缓存文件夹
        File cacheFile = new File(MyApplication.getmContext().getExternalCacheDir().toString(), "liquidGasCache");
        //缓存大小为10M
        int cacheSize = 10 * 1024 * 1024;
        //创建缓存对象
        final Cache cache = new Cache(cacheFile, cacheSize);

        /**
         * 设置cookie
         */
        CookieManger cookieManager = new CookieManger(MyApplication.getmContext());
        /**
         * 设置头，有的接口可能对请求头要设置
         */
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request orignaRequest = chain.request();

                Request request = orignaRequest.newBuilder()
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                        .header("Accept-Encoding", "gzip, deflate")
                        .header("Accept-Language", "zh-CN,zh;q=0.9")
                        .header("Connection", "keep-alive")
                        .header("Content-Type","application/json;charset=UTF-8")
                        //.header("AppType", "TPOS")
                        //.header("Content-Type", "application/json")
                        //.header("Content-Type", "application/x-www-form-urlencoded")
                        //.header("Accept", "application/json")
                        .header("Cache-Control", "max-age=0")
                        .header("Host", "energy-report.gqwap.com")
//                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
//                        .addHeader("Accept-Encoding", "gzip, deflate")
//                        .addHeader("Connection", "keep-alive")
//                        .header("Accept", "*/*")
                        //.addHeader("Cookie", "add cookies here")
                        .method(orignaRequest.method(), orignaRequest.body())
                        .build();
                LogUtils.d("============", orignaRequest.method() + "===" + orignaRequest.body());
                return chain.proceed(request);
            }
        };


        /**
         * 缓存拦截器
         */
        CacheInterceptor cacheInterceptor = new CacheInterceptor();

        okHttpClient = new OkHttpClient.Builder()
                //头
                .addInterceptor(headerInterceptor)
                //缓存拦截
                .addNetworkInterceptor(cacheInterceptor)
                //缓存
                .cache(cache)
                //设置超时--链接--读取--写入
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(25, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS)
                //设置cookie
                .cookieJar(cookieManager)
                //错误重连
                .retryOnConnectionFailure(true)
                .build();
    }
}
