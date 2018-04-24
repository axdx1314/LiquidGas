package com.suchengkeji.android.liquidgas.utils;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.widget.Toast;

import com.suchengkeji.android.liquidgas.NewMainActivity;
import com.suchengkeji.android.liquidgas.R;


/**
 * @aboutContent:
 * @author： An
 * @crateTime: 2018/1/31 09:44
 * @mailBox: an.****.life@gmail.com
 * @company: 东莞速成科技有限公司
 */

public class AppOutUtils {
    //退出时的时间
    private static long mExitTime;
    //推出APP
    public static void exit(Activity mContext) {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            PromptUtils.showToast(mContext, mContext.getResources().getString(R.string.again_out));
            mExitTime = System.currentTimeMillis();
        } else {
            ActivityUtils.getScreenActivityUtils().removeAllActivity();
            mContext.finish();
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            mContext.startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }
}
