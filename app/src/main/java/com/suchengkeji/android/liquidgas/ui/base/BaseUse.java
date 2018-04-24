package com.suchengkeji.android.liquidgas.ui.base;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.suchengkeji.android.liquidgas.R;
import com.suchengkeji.android.liquidgas.api.common.AnCommonUrl;
import com.suchengkeji.android.liquidgas.utils.NetworkUtils;
import com.suchengkeji.android.liquidgas.utils.PromptUtils;

import java.util.List;


/**
 * @aboutContent:
 * @author： An
 * @crateTime: 2018/1/31 14:47
 * @mailBox: an.****.life@gmail.com
 * @company: 东莞速成科技有限公司
 */

public class BaseUse {

    public static boolean isStoreFragmentHidden = false;//识别是否在当前StoreFragment页面
    public static boolean isFranchiserFragmentHidden = false;//识别是否在当前FranchiserFragment页面

    public static boolean getIsStoreFragmentHidden() {
        return isStoreFragmentHidden;
    }

    public static boolean getIsFranchiserFragmentHidden() {
        return isFranchiserFragmentHidden;
    }


    /**
     * 添加数据
     */
    public static void addDatas(List<View> views, Context context, String userID, String storeCode) {
        if (views != null) {
            views.clear();
        }
        //日
        View dayView = LayoutInflater.from(context).inflate(R.layout.chart_day_item, null, false);
        if (!TextUtils.isEmpty(userID) && !TextUtils.isEmpty(storeCode)) {
            BaseUse.setWebViews(context, dayView, AnCommonUrl.DayChar_Url + "userId=" + userID + "&storeCode=" + storeCode);
        } else {
            BaseUse.setWebViews(context, dayView, AnCommonUrl.DayChar_Url);
        }
        views.add(dayView);

        //月
        View mothView = LayoutInflater.from(context).inflate(R.layout.chart_moth_item, null, false);
        if (!TextUtils.isEmpty(userID) && !TextUtils.isEmpty(storeCode)) {
            BaseUse.setWebViews(context, mothView, AnCommonUrl.MothChar_Url + "userId=" + userID + "&storeCode=" + storeCode);
        } else {
            BaseUse.setWebViews(context, mothView, AnCommonUrl.MothChar_Url);
        }
        views.add(mothView);

        //年
        View yearsView = LayoutInflater.from(context).inflate(R.layout.chart_year_item, null, false);
        if (!TextUtils.isEmpty(userID) && !TextUtils.isEmpty(storeCode)) {
            BaseUse.setWebViews(context, yearsView, AnCommonUrl.YearChar_Url + "userId=" + userID + "&storeCode=" + storeCode);
        } else {
            BaseUse.setWebViews(context, yearsView, AnCommonUrl.YearChar_Url);
        }
        views.add(yearsView);
    }


    static WebView mWebView;

    /**
     * WebView 加载展示图表
     *
     * @param view
     * @param url
     */
    public static void setWebViews(Context mContext, View view, String url) {
        //此处改为WebView试试
        mWebView = (WebView) view.findViewById(R.id.blog_detail_webview);
        final ProgressBar mProgressBar = (ProgressBar) view.findViewById(R.id.progress_Bar);
        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.web_imges_lines);
        if (!NetworkUtils.isNetworkAvailable(mContext)) {
            PromptUtils.showToast(mContext, mContext.getString(R.string.string_check_net_ok));
            mWebView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            return;
        }
        mWebView.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);

        WebSettings settings = mWebView.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = mContext.getApplicationContext().getCacheDir().getAbsolutePath();
        settings.setAppCachePath(appCachePath);
        settings.setAllowFileAccess(true); //设置可以访问文件
        settings.setAppCacheEnabled(true);
        //表示不支持js，如果想让java和js交互或者本身希望js完成一定的功能请把false改为true。
        settings.setJavaScriptEnabled(true);
        //设置是否支持缩放，我这里为false，默认为true。
        settings.setSupportZoom(false);//支持缩放，默认为true。是下面那个的前提。
        //设置是否显示缩放工具，默认为false。
        settings.setBuiltInZoomControls(false);//设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //设置加载进来的页面自适应手机屏幕,两者合用
        settings.setUseWideViewPort(true);//将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true);//缩放至屏幕的大小

        //其他细节操作
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式

        /**
         * 是否启用缓存：
         */
//        //优先使用缓存:
//        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        //缓存模式如下：
//        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
//        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
//        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
//        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
//        //不使用缓存:
//        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);


        /*
         一般很少会用到这个，用WebView组件显示普通网页时一般会出现横向滚动条，这样会导致页面查看起来非常不方便。LayoutAlgorithm是一个枚举，用来控制html的布局，总共有三种类型：
         NORMAL：正常显示，没有渲染变化。
         SINGLE_COLUMN：把所有内容放到WebView组件等宽的一列中。
         NARROW_COLUMNS：可能的话，使所有列的宽度不超过屏幕宽度。
         */
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //设置默认的字体大小，默认为16，有效值区间在1-72之间。
        settings.setDefaultFontSize(18);
        //cookies
        //synchronousWebCookies(Context context, String url, String cookies)
        //加载地址
        mWebView.loadUrl(url);

        //取消长按事件
        mWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });


        // 设置WebViewClient
        mWebView.setWebViewClient(new WebViewClient() {
            // url拦截
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 使用自己的WebView组件来响应Url加载事件，而不是使用默认浏览器器加载页面
                view.loadUrl(url);
                // 相应完成返回true
                return true;
                // return super.shouldOverrideUrlLoading(view, url);
            }

            // 页面开始加载
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mProgressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            // 页面加载完成
            @Override
            public void onPageFinished(WebView view, String url) {
                mProgressBar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }

            // WebView加载的所有资源url
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//				view.loadData(errorHtml, "text/html; charset=UTF-8", null);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });

        // 设置WebChromeClient
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            // 处理javascript中的alert
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }


            @Override
            // 处理javascript中的confirm
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }


            @Override
            // 处理javascript中的prompt
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }


            // 设置网页加载的进度条
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mProgressBar.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }

            // 设置程序的Title
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
    }

//
//    public static class MyNetWorkReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            String action = intent.getAction();
//            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
//                //检查网络状态的类型
//                int networkState = NetworkUtils.getNetworkState(context);
//                Log.d("-----ss---", action + "==");
//                // 接口回传网络状态的类型
//                onNetChange(context, networkState);
//            }
//        }
//
//
//        private void onNetChange(Context context, int networkState) {
//            switch (networkState) {
//                case 0:
//                    PromptUtils.showToast(context, "未找到合适匹配网络类型");
//                    break;
//                case 1:
//                    PromptUtils.showToast(context, "中国移动CMNET网络");
//                    break;
//                case 2:
//                    PromptUtils.showToast(context, "中国移动CMWAP网络");
//                    break;
//                case 3:
//                    PromptUtils.showToast(context, "中国联通UNIWAP网络");
//                    break;
//                case 4:
//                    PromptUtils.showToast(context, "中国联通3GWAP网络");
//                    break;
//                case 5:
//                    PromptUtils.showToast(context, "中国联通3HNET网络");
//                    break;
//                case 6:
//                    PromptUtils.showToast(context, "中国联通UNINET网络");
//                    break;
//                case 7:
//                    PromptUtils.showToast(context, "中国电信CTWAP网络");
//                    break;
//                case 8:
//                    PromptUtils.showToast(context, "中国电信CTNET网络");
//                    break;
//                case 10:
//                    PromptUtils.showToast(context, "WIFI网络");
//                    if (mWebView != null&&linearLayout!=null){
//                        mWebView.setVisibility(View.VISIBLE);
//                        linearLayout.setVisibility(View.GONE);
//                        mWebView.reload();
//                    }
//                    break;
//            }
//        }
//    }


}
