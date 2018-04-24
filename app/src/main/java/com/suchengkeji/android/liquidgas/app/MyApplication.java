package com.suchengkeji.android.liquidgas.app;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Environment;
import android.support.multidex.MultiDex;

import com.suchengkeji.android.liquidgas.MainActivity;
import com.suchengkeji.android.liquidgas.R;
import com.suchengkeji.android.liquidgas.api.common.AnCommonUrl;
import com.suchengkeji.android.liquidgas.receivers.NetWorkReceiver;
import com.suchengkeji.android.liquidgas.ui.fragment.tab.StoreFragment;
import com.suchengkeji.android.liquidgas.utils.ActivityUtils;
import com.suchengkeji.android.liquidgas.utils.LogUtils;
import com.suchengkeji.android.liquidgas.utils.SharePreferenceUtils;
import com.suchengkeji.android.liquidgas.utils.TimeUtils;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.beta.Beta;

import java.util.Random;

/**
 * @aboutContent:
 * @author： 安
 * @crateTime: 2017/12/28 11:05
 * @mailBox: an.****.life@gmail.com
 * @company: 东莞速成科技有限公司
 */

public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        ActivityUtils.getScreenActivityUtils();//初始化管理Activity
        mContext = getApplicationContext();
        //主要是为了，每次重新启动都要重新获取数据
        SharePreferenceUtils.put(getApplicationContext(), "times", "2018-02-03 15:40:00");
        setBugly();

        MineTest();
        regeditReceiver();
    }

    /**
     * 广播注册--------主要监听网络的改变
     */
    private void regeditReceiver() {
        NetWorkReceiver netWorkReceiver = new NetWorkReceiver();
        IntentFilter intFilter = new IntentFilter();
        intFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mContext.registerReceiver(netWorkReceiver, intFilter);
    }


    public static String s = "";

    private void MineTest() {
        Random random = new Random();
        for (int i = 1; i <= 5; i++) {
            int i1 = random.nextInt(9);
            s += i1;
        }
    }

    /**
     * 腾讯bugly
     */
    private void setBugly() {
        /***** Beta高级设置 *****/
        /**
         * true表示app启动自动初始化升级模块; false不会自动初始化;
         * 开发者如果担心sdk初始化影响app启动速度，可以设置为false，
         * 在后面某个时刻手动调用Beta.init(getApplicationContext(),false);
         */
        Beta.autoInit = false;

        /**
         * true表示初始化时自动检查升级; false表示不会自动检查升级,需要手动调用Beta.checkUpgrade()方法;
         */
        Beta.autoCheckUpgrade = false;

        /**
         * 设置升级检查周期为60s(默认检查周期为0s)，60s内SDK不重复向后台请求策略);
         */
        Beta.upgradeCheckPeriod = 60 * 1000;
        /**
         * 设置启动延时为1s（默认延时3s），APP启动1s后初始化SDK，避免影响APP启动速度;
         */
        Beta.initDelay = 1 * 1000;
        /**
         * 设置通知栏大图标，largeIconId为项目中的图片资源;
         */
        Beta.largeIconId = R.mipmap.ic_launcher;
        /**
         * 设置状态栏小图标，smallIconId为项目中的图片资源Id;
         */
        Beta.smallIconId = R.mipmap.ic_launcher;
        /**
         * 设置更新弹窗默认展示的banner，defaultBannerId为项目中的图片资源Id;
         * 当后台配置的banner拉取失败时显示此banner，默认不设置则展示“loading“;
         */
        Beta.defaultBannerId = R.mipmap.ic_launcher;
        /**
         * 设置sd卡的Download为更新资源保存目录;
         * 后续更新资源会保存在此目录，需要在manifest中添加WRITE_EXTERNAL_STORAGE权限;
         */
        Beta.storageDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        /**
         * 已经确认过的弹窗在APP下次启动自动检查更新时会再次显示;
         */
        Beta.showInterruptedStrategy = true;
        /**
         * 只允许在MainActivity上显示更新弹窗，其他activity上不显示弹窗; 不设置会默认所有activity都可以显示弹窗;
         */
        Beta.canShowUpgradeActs.add(MainActivity.class);

        /***** Bugly高级设置 *****/
        BuglyStrategy strategy = new BuglyStrategy();
        /**
         * 设置app渠道号
         */
        //strategy.setAppChannel(AnCommonUrl.APP_CHANNEL);

        /***** 统一初始化Bugly产品，包含Beta *****/
        /**
         * 统一初始化Bugly产品，包含Beta
         * @context 上下文
         * @appId 注册时申请的APPID
         * @isDebug 自定义日志将会在Logcat中输出。
         * @strategy Bugly高级设置
         */
        Bugly.init(this, AnCommonUrl.APPID, false, strategy);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        /***  关闭Log日志 ***/
        LogUtils.isEnableDebug(false);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        // 安装tinker---热更新用的
        Beta.installTinker();
    }

    public static Context getmContext() {
        return mContext;
    }
}
