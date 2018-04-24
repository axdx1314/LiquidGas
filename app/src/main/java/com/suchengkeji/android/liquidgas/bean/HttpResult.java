package com.suchengkeji.android.liquidgas.bean;


/**
 * @aboutContent: 返回信息处公用部分
 * @author： An
 * @crateTime: 2018/1/24 17:45
 * @mailBox: an.****.life@gmail.com
 * @company: 东莞速成科技有限公司
 */

public class HttpResult<T> {
    /**
     * code : 200
     * msg : null
     * data : {"userId":1,"userName":"aa","fullName":"富民燃气管理员","companyId":1,"companyName":"虎门能源","mobileModules":[]}
     */

    private int code;
    private String msg;

    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}