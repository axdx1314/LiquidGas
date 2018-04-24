package com.suchengkeji.android.liquidgas.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.suchengkeji.android.liquidgas.api.base.AnBaseApiServiceManger;
import com.suchengkeji.android.liquidgas.api.base.ResponseConvertFactory;
import com.suchengkeji.android.liquidgas.api.common.AnCommonUrl;
import com.suchengkeji.android.liquidgas.api.base.ApiException;
import com.suchengkeji.android.liquidgas.bean.HttpResult;
import com.suchengkeji.android.liquidgas.bean.LoginDataBean;
import com.suchengkeji.android.liquidgas.bean.SalesDataBean;
import com.suchengkeji.android.liquidgas.bean.response.LoginResponseBean;
import com.suchengkeji.android.liquidgas.bean.response.SalesResponseBean;
import com.suchengkeji.android.liquidgas.utils.LogUtils;


import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @aboutContent: Retrofit创建以及网络请求
 * @author： 安
 * @crateTime: 2017/12/21 17:46
 * @mailBox: an.****.life@gmail.com
 * @company: 东莞速成科技有限公司
 */

public class AnApiServiceManger extends AnBaseApiServiceManger {
    private static AnApiServiceManger anApiServiceManger;
    private AnApiService anApiService;

    private AnApiServiceManger() {
        Gson gson = new GsonBuilder().setLenient().create();
        //在构造方法中完成对Retrofit接口的初始化
        Retrofit retrofit = new Retrofit.Builder()
                // 设置baseUrl
                .baseUrl(AnCommonUrl.Base_Url)
                //设置OKHttpClient,如果不设置会提供一个默认的
                .client(getOkHttpClient())
                //添加Gson转换器
                //modify by zqikai 20160317 for 对http请求结果进行统一的预处理 GosnResponseBodyConvert
                //.addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ResponseConvertFactory.create(gson))
                // 使用RxJava作为回调适配器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        //添加Gson转换器
        new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson));
        anApiService = retrofit.create(AnApiService.class);
    }

    /**
     * 单利
     *
     * @return
     */
    public static AnApiServiceManger getInstance() {
        if (anApiServiceManger == null) {
            synchronized (AnApiServiceManger.class) {
                if (anApiServiceManger == null) {
                    anApiServiceManger = new AnApiServiceManger();
                }
            }
        }
        return anApiServiceManger;
    }

    /**
     * 转JSON
     *
     * @param obj
     * @return
     */
    private RequestBody getRequestBody(Object obj) {
        String route = new Gson().toJson(obj);
        LogUtils.d("========getRequestBody", route);
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), route);
    }


    /**
     * 登录
     *
     * @param phone
     * @param password
     * @return
     */
    public Observable<LoginResponseBean> login(String phone, String password) {
        return anApiService.login(phone, password);
    }

    /**
     * 获取销量数据
     *
     * @return
     */
    public Observable<SalesResponseBean> getTodaySaleInfoByUserId() {
        return anApiService.getTodaySaleInfoByUserId();
    }


    /*******        同上面的获取数据方法作用相同，不过下面的添加了错误统一处理                 *******/
    /**
     * 登录
     * <p>
     * 错误统一处理
     *
     * @param observer
     * @param phone
     * @param password
     */
    public void logins(Subscriber<LoginDataBean> observer, String phone, String password) {
        Observable observable = anApiService.logins(phone, password)
                .map(new HttpResultFunc<LoginDataBean>());
        toSubscribe(observable, observer);
    }

    /**
     * 获取销量数据
     * <p>
     * 错误统一处理
     *
     * @return
     */
    public void getTodaySaleInfoByUserIds(Subscriber<SalesDataBean> observer) {
        Observable observable = anApiService.getTodaySaleInfoByUserIds()
                .map(new HttpResultFunc<SalesDataBean>());
        toSubscribe(observable, observer);
    }

    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }


    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> httpResult) {
            if (httpResult.getCode() == 301) {
                throw new ApiException(301);
            } else if (httpResult.getCode() == 302) {
                throw new ApiException(302);
            } else if (httpResult.getCode() == 200) {
                return httpResult.getData();
            } else {
                throw new ApiException(300);
            }
        }
    }
}
