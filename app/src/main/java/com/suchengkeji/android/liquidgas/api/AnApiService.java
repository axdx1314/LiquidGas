package com.suchengkeji.android.liquidgas.api;


import com.suchengkeji.android.liquidgas.bean.HttpResult;
import com.suchengkeji.android.liquidgas.bean.LoginDataBean;
import com.suchengkeji.android.liquidgas.bean.SalesDataBean;
import com.suchengkeji.android.liquidgas.bean.response.LoginResponseBean;
import com.suchengkeji.android.liquidgas.bean.response.SalesResponseBean;


import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @aboutContent: api
 * @author： 安
 * @crateTime: 2017/12/21 17:46
 * @mailBox: an.****.life@gmail.com
 * @company: 东莞速成科技有限公司
 */

public interface AnApiService {

    /**
     * 登陆
     *
     * @param username
     * @param password
     * @return
     */
    //http://192.168.3.101:8080/energy-report/app/login/loginByAccount?username=aa&password=aa
    @POST("login/loginByAccount?")
    Observable<LoginResponseBean> login(@Query("username") String username, @Query("password") String password);


    /**
     * 获取销量数据
     *
     * @return
     */
    //http://192.168.3.101:8080/energy-report/app/sale/getTodaySaleInfoByUserId
    @POST("sale/getTodaySaleInfoByUserId")
    Observable<SalesResponseBean> getTodaySaleInfoByUserId();


    /*******************      添加错误统一处理     *************************/

    /**
     * 登陆
     * <p>
     * 错误统一处理
     *
     * @param username
     * @param password
     * @return
     */
    @POST("login/loginByAccount?")
    Observable<HttpResult<LoginDataBean>> logins(@Query("username") String username, @Query("password") String password);


    /**
     * 获取销量数据
     * <p>
     * 错误统一处理
     *
     * @return
     */
    @GET("sale/getTodaySaleInfoByUserId")
    Observable<HttpResult<SalesDataBean>> getTodaySaleInfoByUserIds();
}