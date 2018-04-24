package com.suchengkeji.android.liquidgas.api.base.subscribers;

import android.content.Context;
import android.widget.Toast;


import com.suchengkeji.android.liquidgas.api.base.progress.ProgressCancelListener;
import com.suchengkeji.android.liquidgas.api.base.progress.ProgressDialogHandler;
import com.suchengkeji.android.liquidgas.utils.LogUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;


/**
 * @aboutContent: 用于在Http请求开始时，自动显示一个ProgressDialog
 * <></> <></>      在Http请求结束是，关闭ProgressDialog
 * <></> <></>      调用者自己对请求数据进行处理
 * @author： An
 * @crateTime: 2018/1/25 11:05
 * @mailBox: an.****.life@gmail.com
 * @company: 东莞速成科技有限公司
 */
public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {
    private final String TAG = "===->>>" + this.getClass();
    private SubscriberOnNextListener mSubscriberOnNextListener;
    private ProgressDialogHandler mProgressDialogHandler;

    private Context context;
    private String textContent;

    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context, String textContent) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
        this.textContent = textContent;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            //mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG, textContent).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        showProgressDialog();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        dismissProgressDialog();
        //Toast.makeText(context, "数据获取成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else {
            LogUtils.d(TAG, "------onError-------" + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        dismissProgressDialog();

    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onNext(t);
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            LogUtils.d(TAG, "取消网络请求");
            Toast.makeText(context, "网络请求未完成", Toast.LENGTH_SHORT).show();
            this.unsubscribe();
        }
    }
}