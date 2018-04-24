package com.suchengkeji.android.liquidgas.ui.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.suchengkeji.android.liquidgas.api.AnApiServiceManger;

/**
 * @aboutContent:
 * @author： An
 * @crateTime: 2018/1/26 11:42
 * @mailBox: an.****.life@gmail.com
 * @company: 东莞速成科技有限公司
 */

public class BaseInterfaceObserver implements BaseInterface {
    private static BaseInterfaceObserver interfaceObserver;

    /**
     * 单利
     *
     * @return
     */
    public static BaseInterfaceObserver getInstance() {
        if (interfaceObserver == null) {
            synchronized (BaseInterfaceObserver.class) {
                if (interfaceObserver == null) {
                    interfaceObserver = new BaseInterfaceObserver();
                }
            }
        }
        return interfaceObserver;
    }


    /**
     * 隐藏或显示状态栏
     * number = 0  隐藏状态栏
     * number = 1  隐藏标题栏
     * number = 2  隐藏标题栏和状态栏
     *
     * @param context
     * @param number
     */
    @Override
    public void hideWindowFeature(Activity context, int number) {
        switch (number) {
            case 0:
                //隐藏状态栏
                context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                break;
            case 1:
                //隐藏标题栏
                context.requestWindowFeature(Window.FEATURE_NO_TITLE);
                break;
            case 2:
                //隐藏标题栏
                context.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //隐藏状态栏
                //设置当前窗体为全屏显示-----------------定义全屏参数
                context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                break;
        }
    }

    /**
     * 给状态栏设置颜色
     *
     * @param context
     * @param number
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setBarColor(Activity context, int number) {
        Window window = context.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(context.getResources().getColor(number));
    }
}
