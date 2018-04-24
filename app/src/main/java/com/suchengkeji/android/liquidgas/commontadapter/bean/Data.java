package com.suchengkeji.android.liquidgas.commontadapter.bean;

/**
 * @aboutContent:
 * @author： 安
 * @crateTime: 2017/12/29 15:15
 * @mailBox: an.****.life@gmail.com
 * @company: 东莞速成科技有限公司
 */

public class Data {

    private String title;

    private String content;

    public Data(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
