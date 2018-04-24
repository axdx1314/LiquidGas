package com.suchengkeji.android.liquidgas.api.common;

/**
 * @aboutContent: Base_Url
 * @author： 安
 * @crateTime: 2017/12/21 17:48
 * @mailBox: an.****.life@gmail.com
 * @company: 东莞速成科技有限公司
 */

public class AnCommonUrl {
    /********* 网络ＵＲＬ   ***********/
    public static final String Base_Url = "http://energy-report.gqwap.com/app/";// "http://192.168.3.101:8080/energy-report/app/";
    /**
     * @DayChar_Url web Url  ----- 日
     * @MothChar_Url web Url  ----- 月
     * @YearChar_Url web Url  ----- 年
     */
    public static final String DayChar_Url = Base_Url + "sale/dayList?";
    public static final String MothChar_Url = Base_Url + "sale/monthList?";
    public static final String YearChar_Url = Base_Url + "sale/yearList?";


    /********* 广播   ***********/
    public static final String BROADCAST = "com.broadcast.suchengkeji.ACTION_";//之前的拼接链
    public static final String StoreFragment = "StoreFragment";//门店
    public static final String FranchiserFragment = "FranchiserFragment";//加盟商

    /************    腾讯Bugly   *************/
    public static final String APPID = "45fa2ac27f";//Bugly -- APPID
    public static final String APP_CHANNEL = "";//渠道号
}
