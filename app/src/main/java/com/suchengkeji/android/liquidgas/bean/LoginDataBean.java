package com.suchengkeji.android.liquidgas.bean;

import java.io.Serializable;
import java.util.List;

public class LoginDataBean extends HttpResult implements Serializable {

    /**
     * userId : 1
     * userName : aa
     * fullName : 富民燃气管理员
     * companyId : 1
     * companyName : 虎门能源
     * mobileModules : []
     */

    private int userId;
    private String userName;
    private String fullName;
    private int companyId;
    private String companyName;
    private List<?> mobileModules;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<?> getMobileModules() {
        return mobileModules;
    }

    public void setMobileModules(List<?> mobileModules) {
        this.mobileModules = mobileModules;
    }
}