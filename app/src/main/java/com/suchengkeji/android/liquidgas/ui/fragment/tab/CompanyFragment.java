package com.suchengkeji.android.liquidgas.ui.fragment.tab;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.suchengkeji.android.liquidgas.R;
import com.suchengkeji.android.liquidgas.ui.base.BaseFragment;
import com.suchengkeji.android.liquidgas.ui.base.BaseUse;
import com.suchengkeji.android.liquidgas.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 公司页面
 * A simple {@link Fragment} subclass.
 */
public class CompanyFragment extends BaseFragment {
    private final String TAG = "===->>>" + this.getClass();
    @BindView(R.id.sales_tables)
    TabLayout mTabLayout;
    @BindView(R.id.sales_pagers)
    ViewPager mViewPager;
    private List<View> views;
    //Unbinder unbinder;
    //private WebView mWebView;

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_day, container, false);
//        unbinder = ButterKnife.bind(this, view);
//        initViews();
//        return view;
//    }


//    /**
//     * 设置Vp和Table
//     */
//    private void initViews() {
//        List<View> views = new ArrayList<>();
//        //addDatas();
//        BaseUse.addDatas(views, getActivity(), "", "");
//        mViewPager.setAdapter(new PagerAdapter() {
//            @Override
//            public int getCount() {
//                return views.size();
//            }
//
//            @Override
//            public boolean isViewFromObject(View view, Object object) {
//                return view == object;
//            }
//
//            @Override
//            public Object instantiateItem(ViewGroup container, int position) {
//                container.addView(views.get(position));
//                return views.get(position);
//            }
//
//            @Override
//            public void destroyItem(ViewGroup container, int position, Object object) {
//                container.removeView(views.get(position));
//            }
//        });
//        mTabLayout.setupWithViewPager(mViewPager);
//        mTabLayout.getTabAt(0).setText(getResources().getString(R.string.string_day));
//        mTabLayout.getTabAt(1).setText(getResources().getString(R.string.string_moth));
//        mTabLayout.getTabAt(2).setText(getResources().getString(R.string.string_year));
//        mTabLayout.setSelectedTabIndicatorHeight(2);
//        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.appthemeColor));
//        mTabLayout.setTabTextColors(getResources().getColor(R.color.textColor), getResources().getColor(R.color.appthemeColor));
//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                BaseUse.getmWebView().reload();  //刷新
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//    }


//    /**
//     * 添加数据
//     */
//    private void addDatas() {
//        if (views != null) {
//            views.clear();
//        }
//        //日
//        View dayView = LayoutInflater.from(getContext()).inflate(R.layout.chart_day_item, null, false);
//        //initDayView(dayView);
//        //setWebViews(dayView, AnCommonUrl.DayChar_Url);
//        BaseUse.setWebViews(getContext(), dayView, AnCommonUrl.DayChar_Url);
//        views.add(dayView);
//
//        //月
//        View mothView = LayoutInflater.from(getContext()).inflate(R.layout.chart_moth_item, null, false);
//        //initMothView(mothView);
//        //setWebViews(mothView, AnCommonUrl.MothChar_Url);
//        BaseUse.setWebViews(getContext(), mothView, AnCommonUrl.MothChar_Url);
//        views.add(mothView);
//
//        //年
//        View yearsView = LayoutInflater.from(getContext()).inflate(R.layout.chart_year_item, null, false);
//        //initYearView(yearsView);
//        //setWebViews(yearsView, AnCommonUrl.YearChar_Url);
//        BaseUse.setWebViews(getContext(), yearsView, AnCommonUrl.YearChar_Url);
//        views.add(yearsView);
//    }


//    /**
//     * WebView 加载展示图表
//     *
//     * @param view
//     * @param url
//     */
//    public void setWebViews(View view, String url) {
//        //此处改为WebView试试
//        mWebView = (WebView) view.findViewById(R.id.blog_detail_webview);
//
//        WebSettings settings = mWebView.getSettings();
//        settings.setDomStorageEnabled(true);
//        settings.setAppCacheMaxSize(1024 * 1024 * 8);
//        String appCachePath = getContext().getApplicationContext().getCacheDir().getAbsolutePath();
//        settings.setAppCachePath(appCachePath);
//        settings.setAllowFileAccess(true); //设置可以访问文件
//        settings.setAppCacheEnabled(true);
//
//        //表示不支持js，如果想让java和js交互或者本身希望js完成一定的功能请把false改为true。
//        settings.setJavaScriptEnabled(true);
//        //设置是否支持缩放，我这里为false，默认为true。
//        settings.setSupportZoom(false);//支持缩放，默认为true。是下面那个的前提。
//        //设置是否显示缩放工具，默认为false。
//        settings.setBuiltInZoomControls(false);//设置内置的缩放控件。若为false，则该WebView不可缩放
//        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件
//
//        //设置加载进来的页面自适应手机屏幕,两者合用
//        settings.setUseWideViewPort(true);//将图片调整到适合webview的大小
//        settings.setLoadWithOverviewMode(true);//缩放至屏幕的大小
//
//        //其他细节操作
//        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
//        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
//        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
//        settings.setDefaultTextEncodingName("utf-8");//设置编码格式
//
//
//        /**
//         * 是否启用缓存：
//         */
////        //优先使用缓存:
////        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
////        //缓存模式如下：
////        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
////        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
////        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
////        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
////        //不使用缓存:
////        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//
//
//        /*
//         一般很少会用到这个，用WebView组件显示普通网页时一般会出现横向滚动条，这样会导致页面查看起来非常不方便。LayoutAlgorithm是一个枚举，用来控制html的布局，总共有三种类型：
//         NORMAL：正常显示，没有渲染变化。
//         SINGLE_COLUMN：把所有内容放到WebView组件等宽的一列中。
//         NARROW_COLUMNS：可能的话，使所有列的宽度不超过屏幕宽度。
//         */
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        //设置默认的字体大小，默认为16，有效值区间在1-72之间。
//        settings.setDefaultFontSize(18);
//        //cookies
//        //synchronousWebCookies(Context context, String url, String cookies)
//        //加载地址
//        mWebView.loadUrl(url);
//        //取消长按事件
//        mWebView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                return true;
//            }
//        });
//    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_day;
    }



    @Override
    protected void initView() {
        regeditReceiver();
        setData();
    }

    private void setData() {
        views = new ArrayList<>();
        BaseUse.addDatas(views, getActivity(), "", "");
        mViewPager.setAdapter(new MyAdapter());
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText(getResources().getString(R.string.string_day));
        mTabLayout.getTabAt(1).setText(getResources().getString(R.string.string_moth));
        mTabLayout.getTabAt(2).setText(getResources().getString(R.string.string_year));
        mTabLayout.setSelectedTabIndicatorHeight(2);
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.appthemeColor));
        mTabLayout.setTabTextColors(getResources().getColor(R.color.textColor), getResources().getColor(R.color.appthemeColor));
    }


    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }
    }



    /******  用于关闭打开的item和返回上级界面  ******/
    private MyBroadCastReceiver myBroadCastReceiver;

    /**
     * 广播注册
     */
    private void regeditReceiver() {
        myBroadCastReceiver = new MyBroadCastReceiver();
        IntentFilter intFilter = new IntentFilter();
        intFilter.addAction("com.suchengkeji.android.liquidgas.receivers.NETCHANGE");
        getContext().registerReceiver(myBroadCastReceiver, intFilter);
    }


    /**
     * 广播实现
     */
    public class MyBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String name = intent.getAction();
            Log.d("-----ss---", name + "==");

            if (name.equals("com.suchengkeji.android.liquidgas.receivers.NETCHANGE")) {
                //检查网络状态的类型
                int networkState = NetworkUtils.getNetworkState(context);
                // 接口回传网络状态的类型
                if (networkState != 0) {
                    setData();
                }
                return;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /**  广播解绑 **/
        getContext().unregisterReceiver(myBroadCastReceiver);
    }
}
