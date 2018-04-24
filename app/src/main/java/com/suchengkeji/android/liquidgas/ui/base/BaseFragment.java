package com.suchengkeji.android.liquidgas.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @aboutContent:
 * @author： An
 * @crateTime: 2018/1/30 17:58
 * @mailBox: an.****.life@gmail.com
 * @company: 东莞速成科技有限公司
 */

public abstract class BaseFragment extends Fragment {
    private Unbinder unbinder;
    /**
     * 贴附的activity
     */
    protected FragmentActivity mActivity;

    /**
     * 根view
     */
    protected View mRootView;

    /**
     * 是否对用户可见
     */
    protected boolean mIsVisible;
    /**
     * 是否加载完成
     * 当执行完oncreatview,View的初始化方法后方法后即为true
     */
    protected boolean mIsPrepare;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(setLayoutResouceId(), container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        initData(getArguments());
        initView();
        mIsPrepare = true;
        onLazyLoad();
        setListener();
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 初始化数据
     *
     * @param arguments 接收到的从其他地方传递过来的参数
     * @author 漆可
     * @date 2016-5-26 下午3:57:48
     */
    protected void initData(Bundle arguments) {

    }

    /**
     * 初始化View
     *
     * @author 漆可
     * @date 2016-5-26 下午3:58:49
     */
    protected void initView() {

    }

    /**
     * 设置监听事件
     *
     * @author 漆可
     * @date 2016-5-26 下午3:59:36
     */
    protected void setListener() {

    }

    private boolean mHasLoadedOnce = false;// 页面已经加载过
//    private List mNetData;// 网络数据



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

//        if (isVisibleToUser) {
//            // 相当于Fragment的onResume
//            //Log.i("OperateFragment", "ChatFragment ---setUserVisibleHint---isVisibleToUser - TURE");
//        } else {
//            // 相当于Fragment的onPause
//            //Log.i("OperateFragment", "ChatFragment ---setUserVisibleHint---isVisibleToUser - FALSE");
//
//        }
//        if (isVisibleToUser && !mHasLoadedOnce && mNetData == null) {
//            Log.i("OperateFragment", "FoundFragment 加载请求网络数据");
//            //TO-DO 执行网络数据请求
//            mHasLoadedOnce = true;
//        }




        this.mIsVisible = isVisibleToUser;
        if (isVisibleToUser) {
            onVisibleToUser();
        }else {
            mHasLoadedOnce = false;
        }

        if (isVisibleToUser && !mHasLoadedOnce) {
            Log.i("OperateFragment", "FoundFragment 加载请求网络数据");
            //TO-DO 执行网络数据请求
            onVisibleToUser2();
            mHasLoadedOnce = true;
        }

    }

    /**
     * 用户可见时执行的操作
     *
     * @author 漆可
     * @date 2016-5-26 下午4:09:39
     */
    protected void onVisibleToUser() {
        if (mIsPrepare && mIsVisible) {
            onLazyLoad();
        }
    }

    protected void onVisibleToUser2() {
        if (mIsPrepare && mIsVisible) {
            onLazyLoad2();
        }
    }

    /**
     * 懒加载，仅当用户可见切view初始化结束后才会执行
     *
     * @author 漆可
     * @date 2016-5-26 下午4:10:20
     */
    protected void onLazyLoad() {

    }

    protected void onLazyLoad2() {

    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewById(int id) {
        if (mRootView == null) {
            return null;
        }

        return (T) mRootView.findViewById(id);
    }

    /**
     * 设置根布局资源id
     *
     * @return
     * @author 漆可
     * @date 2016-5-26 下午3:57:09
     */
    protected abstract int setLayoutResouceId();
}
