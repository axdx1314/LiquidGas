package com.suchengkeji.android.liquidgas.bean.request;

/**
 * @aboutContent:
 * @author： 安
 * @crateTime: 2018/1/5 15:11
 * @mailBox: an.****.life@gmail.com
 * @company: 东莞速成科技有限公司
 */

public class LoginRequestBean {


    private String phone;
    private String password;

    public LoginRequestBean(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
