package com.suchengkeji.android.liquidgas.api.base.progress;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.suchengkeji.android.liquidgas.R;


/**
 * @aboutContent: ProgressDialog
 * @author： An
 * @crateTime: 2018/1/25 11:15
 * @mailBox: an.****.life@gmail.com
 * @company: 东莞速成科技有限公司
 */
public class ProgressDialogHandler extends Handler {

    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private ProgressDialog pd;//对话框进度
    private static Dialog loadingDialog;  //dialog进度条提示

    private Context context;
    private boolean cancelable;
    private ProgressCancelListener mProgressCancelListener;

    public ProgressDialogHandler(Context context, ProgressCancelListener mProgressCancelListener,
                                 boolean cancelable) {
        super();
        this.context = context;
        this.mProgressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
    }

    private void initProgressDialog() {
        if (pd == null) {
            pd = new ProgressDialog(context, R.style.CustomProgressDialog);
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
            pd.setCancelable(cancelable);
            if (cancelable) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        mProgressCancelListener.onCancelProgress();
                    }
                });
            }
            if (!pd.isShowing()) {
                pd.show();
            }
        }
    }

    private void dismissProgressDialog() {
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
    }

    private void initDialog(String msg) {
        if (loadingDialog == null) {
            loadingDialog = new Dialog(context, R.style.CustomProgressDialog);
            loadingDialog.setContentView(R.layout.progress_view);
            loadingDialog.setCancelable(cancelable);
            loadingDialog.setCanceledOnTouchOutside(false);
            TextView tvMsg = (TextView) loadingDialog.getWindow().findViewById(R.id.dialog_tvShow);
            tvMsg.setText(msg);
            if (cancelable) {
                loadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mProgressCancelListener.onCancelProgress();
                    }
                });
            }
            try {
                if (!loadingDialog.isShowing()) {
                    loadingDialog.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void dismissDialog() {

        if (loadingDialog != null) {
            try {
                loadingDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            loadingDialog = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                String ms = (String) msg.obj;
                if (ms != null) {
                    initDialog(ms);
                } else {
                    initDialog("加载中...");
                }
                //initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissDialog();
                //dismissProgressDialog();
                break;
        }
    }

}
