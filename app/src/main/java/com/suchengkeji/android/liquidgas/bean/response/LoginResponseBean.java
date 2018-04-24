package com.suchengkeji.android.liquidgas.bean.response;

import java.util.List;

/**
 * @aboutContent:
 * @author： 安
 * @crateTime: 2018/1/5 15:00
 * @mailBox: an.****.life@gmail.com
 * @company: 东莞速成科技有限公司
 */

public class LoginResponseBean {

    /**
     * code : 200
     * msg : null
     * data : {"user_id":1,"username":"aa","full_name":"富民燃气管理员","mobileModules":[]}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * user_id : 1
         * username : aa
         * full_name : 富民燃气管理员
         * mobileModules : []
         */

        private int user_id;
        private String username;
        private String full_name;
        private List<?> mobileModules;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getFull_name() {
            return full_name;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }

        public List<?> getMobileModules() {
            return mobileModules;
        }

        public void setMobileModules(List<?> mobileModules) {
            this.mobileModules = mobileModules;
        }
    }
}
