package com.suchengkeji.android.liquidgas.api.base.intercept;

import com.suchengkeji.android.liquidgas.app.MyApplication;
import com.suchengkeji.android.liquidgas.utils.LogUtils;
import com.suchengkeji.android.liquidgas.utils.NetworkUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @aboutContent: 缓存拦截
 * @author： 安
 * @crateTime: 2017/12/22 09:36
 * @mailBox: an.****.life@gmail.com
 * @company: 东莞速成科技有限公司
 */

public class CacheInterceptor implements Interceptor {
    private final String TAG = "===->>>" + this.getClass();
    @Override
    public Response intercept(Chain chain) throws IOException {


        //通过 CacheControl 控制缓存数据
        CacheControl.Builder cacheBuilder = new CacheControl.Builder();
        cacheBuilder.maxAge(0, TimeUnit.SECONDS);//这个是控制缓存的最大生命时间
        cacheBuilder.maxStale(365, TimeUnit.DAYS);//这个是控制缓存的过时时间
        CacheControl cacheControl = cacheBuilder.build();

        //设置拦截器
        Request request = chain.request();
        if (!NetworkUtils.isNetworkAvailable(MyApplication.getmContext())) {
            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build();
        }

/****************************************/
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        long t1 = System.nanoTime();//请求发起的时间
        LogUtils.eL(TAG, String.format("发送请求 %s on %s%n%s", request.url(), chain.connection(), request.headers()));
/****************************************/

        Response originalResponse = chain.proceed(request);

/****************************************/
        long t2 = System.nanoTime();//收到响应的时间
        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理
        ResponseBody responseBody = originalResponse.peekBody(1024 * 1024);
        LogUtils.eL(TAG, String.format("接收响应: [%s] %n返回json:【%s】 %.1fms%n%s",
                originalResponse.request().url(),
                responseBody.string(),
                (t2 - t1) / 1e6d,
                originalResponse.headers()));
/****************************************/

        if (NetworkUtils.isNetworkAvailable(MyApplication.getmContext())) {
            int maxAge = 0;//read from cache
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public ,max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28;//tolerate 4-weeks stale
            return originalResponse.newBuilder()
                    .removeHeader("Prama")
                    .header("Cache-Control", "poublic, only-if-cached, max-stale=" + maxStale)
                    .build();
        }


    }
}