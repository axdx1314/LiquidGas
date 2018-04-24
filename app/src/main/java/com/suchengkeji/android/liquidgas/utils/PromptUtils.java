package com.suchengkeji.android.liquidgas.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;
import com.suchengkeji.android.liquidgas.R;

/**
 * @aboutContent: Toast\dialog提示
 * @author： 安
 * @crateTime: 2017/12/28 14:33
 * @mailBox: an.****.life@gmail.com
 * @company: 东莞速成科技有限公司
 */

public class PromptUtils {
    private static Dialog loadingDialog;  //dialog进度条提示
    private static Toast mToast;   //Toast提示



    public static void showToast(Context context,String message) {
        if (mToast == null) {
            if (!TextUtils.isEmpty(message)) {
                mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            }
        } else {
            if (!TextUtils.isEmpty(message)) {
                mToast.setText(message);
            }
        }
        mToast.show();
    }


    /**
     * 加载提示
     *
     * @param msg
     */
    public static void showProgressDialog(Activity context, String msg) {
        if (context.isFinishing()) {
            closeProgressDialog();
            return;
        }
        if (loadingDialog == null) {
            loadingDialog = new Dialog(context, R.style.CustomProgressDialog);
            loadingDialog.setContentView(R.layout.progress_view);
            loadingDialog.setCancelable(true);
            loadingDialog.setCanceledOnTouchOutside(false);
            TextView tvMsg = (TextView) loadingDialog.getWindow().findViewById(R.id.dialog_tvShow);
            tvMsg.setText(msg);
            try {
                loadingDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭加载提示
     */
    public  static void closeProgressDialog() {
        if (loadingDialog != null) {
            try {
                loadingDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            loadingDialog = null;
        }
    }

}
