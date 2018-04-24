package com.suchengkeji.android.liquidgas.api.base;

import com.google.gson.Gson;
import com.suchengkeji.android.liquidgas.bean.HttpResult;
import com.suchengkeji.android.liquidgas.utils.LogUtils;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @aboutContent: 对http请求结果进行统一的预处理
 * @author： An
 * @crateTime: 2018/1/25 11:45
 * @mailBox: an.****.life@gmail.com
 * @company: 东莞速成科技有限公司
 */
class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final String TAG = "===->>>" + this.getClass();
    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        LogUtils.dL(TAG, "---Network---" + response);
        //httpResult 只解析result字段
        HttpResult httpResult = gson.fromJson(response, HttpResult.class);
        /**
         * 请求返回码为:
         * 200  ---  请求成功
         * 301  ---  无此用户
         * 302  ---  密码错误
         * ...  ---  未知错误
         */
        if (httpResult.getCode() == 301) {
            throw new ApiException(301);
        } else if (httpResult.getCode() == 302) {
            throw new ApiException(302);
        } else if (httpResult.getCode() == 200) {
            return gson.fromJson(response, type);
        } else {
            throw new ApiException(100);
        }
//        if (httpResult.getCode() == 0) {
//            throw new ApiException(100);
//        }
//        return gson.fromJson(response, type);
    }
}
