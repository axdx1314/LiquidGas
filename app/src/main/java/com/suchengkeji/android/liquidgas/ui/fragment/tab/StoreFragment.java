package com.suchengkeji.android.liquidgas.ui.fragment.tab;


import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.suchengkeji.android.liquidgas.R;
import com.suchengkeji.android.liquidgas.api.common.AnCommonUrl;
import com.suchengkeji.android.liquidgas.bean.SalesDataBean;
import com.suchengkeji.android.liquidgas.commontadapter.CommonRecyclerAdapter;
import com.suchengkeji.android.liquidgas.commontadapter.MyViewHolder;
import com.suchengkeji.android.liquidgas.ui.base.BaseFragment;
import com.suchengkeji.android.liquidgas.ui.base.BaseUse;
import com.suchengkeji.android.liquidgas.utils.AppOutUtils;
import com.suchengkeji.android.liquidgas.utils.LogUtils;
import com.suchengkeji.android.liquidgas.utils.NetworkUtils;
import com.suchengkeji.android.liquidgas.utils.PromptUtils;
import com.suchengkeji.android.liquidgas.utils.SharePreferenceUtils;
import com.suchengkeji.android.liquidgas.utils.TimeUtils;
import com.suchengkeji.android.liquidgas.utils.VerificationUtils;
import com.suchengkeji.android.liquidgas.view.PriceHighToLowComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 门店
 * A simple {@link Fragment} subclass.
 */
public class StoreFragment extends BaseFragment {
    private final String TAG = "===->>>" + this.getClass();
    @BindView(R.id.stor_rec)
    RecyclerView storRec;
    @BindView(R.id.rel_hot)
    RelativeLayout relHot;
    @BindView(R.id.sales_tables)
    TabLayout salesTables;
    @BindView(R.id.sales_pagers)
    ViewPager salesPagers;
    @BindView(R.id.line_chart)
    LinearLayout lineChart;
    @BindView(R.id.text_datas)
    TextView textDatas;
    @BindView(R.id.text_type_one)
    TextView textTypeOne;
    @BindView(R.id.text_bottle_one)
    TextView textBottleOne;
    @BindView(R.id.text_order)
    TextView textOrder;
    @BindView(R.id.text_type_two)
    TextView textTypeTwo;
    @BindView(R.id.text_bottle_two)
    TextView textBottleTwo;
    @BindView(R.id.text_day_order)
    TextView textDayOrder;
    @BindView(R.id.text_type_three)
    TextView textTypeThree;
    @BindView(R.id.text_bottle_three)
    TextView textBottleThree;
    @BindView(R.id.store_text_prompt)
    TextView storeTextPrompt;//无数据时显示
    @BindView(R.id.text_names_store)
    TextView textNameStore;

    @BindView(R.id.card_store_names)
    CardView cardView;
    Unbinder unbinder;

    //Unbinder unbinder;
    // private WebView mWebView;//Android内嵌浏览器
    private MyAdapter myAdapter;//rec适配器
    private List<View> views;//年-月-日===界面View


    private int TYPE = 0;
    private String userID = "";
    //private int MP = 0;
    private String storeCode;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_store;
    }


    @Override
    protected void initView() {

        userID = (String) SharePreferenceUtils.get(getContext(), "userIDs", "");
        Date date = TimeUtils.subDateTime(TimeUtils.gainCurrentDate(), 24);
        LogUtils.d(TAG, "===本地时间===" + TimeUtils.subDateTime(TimeUtils.gainCurrentDate(), 24)
                + "==\n==" + TimeUtils.formatDateTime(date, "yyyy-MM-dd"));
//        textDatas.setText(String.valueOf(TimeUtils.todayYyyyMmDd()).substring(5, (TimeUtils.todayYyyyMmDd()).length()));
        textDatas.setText(String.valueOf(TimeUtils.formatDateTime(date, "yyyy-MM-dd")).substring(5, (TimeUtils.todayYyyyMmDd()).length()));
//        subscriberOnNextListener = new SubscriberOnNextListener<SalesDataBean>() {
//            @Override
//            public void onNext(SalesDataBean salesDataBean) {
//                //实体类转换成JSON保存
//                String s = new Gson().toJson(salesDataBean, SalesDataBean.class);
//                SharePreferenceUtils.put(getContext(), "json", s);
//
//                List<SalesDataBean.StoresBean> stores = salesDataBean.getStores();
//                if (stores == null || stores.size() <= 0) return;
//                storeCode = stores.get(MP).getStoreCode();
//                LogUtils.d(TAG, "------storeCode------" + storeCode + "---" + userID);
//                netWorkeCall(stores, MP);
//                initTableVp();
//            }
//        };
        //initNetWorke();
        regeditReceiver();
    }


    //    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_store, container, false);
//        unbinder = ButterKnife.bind(this, view);
//
//        userID = (String) SharePreferenceUtils.get(getContext(), "userIDs", "");
//        subscriberOnNextListener = new SubscriberOnNextListener<SalesDataBean>() {
//            @Override
//            public void onNext(SalesDataBean salesDataBean) {
//                List<SalesDataBean.StoresBean> stores = salesDataBean.getStores();
//                if (stores == null || stores.size() <= 0) {
//                    return;
//                }
//                storeCode = stores.get(MP).getStoreCode();
//                Log.d("------storeCode------", storeCode + "---" + userID);
//                netWorkeCall(stores, MP);
//                initTableVp();
//            }
//        };
//        initNetWorke();
//        //initView();
//        regeditReceiver();
//        return view;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }


    private void netWorkeCall(List<SalesDataBean.StoresBean> stores, int position) {

        if (TYPE == 1) {

            Comparator<SalesDataBean.StoresBean> comparator = new PriceHighToLowComparator();
            Collections.sort(stores, comparator);

            if (stores == null || stores.size() <= 0) return;
            SalesDataBean.StoresBean storesBean = stores.get(position);
            if (storesBean == null) return;
            String saleDate = storesBean.getSaleDate();//日期 2011-01-01
            int todayOrderNum = storesBean.getTodayOrderNum();//今日销量
            int yesterdayGrowthOrderNum = storesBean.getYesterdayGrowthOrderNum();//较上日增加量
//            try {
//                Logger.d("-----------", saleDate + "==" + todayOrderNum + "==" + yesterdayGrowthOrderNum);
//            } catch (Error e) {
//                e.getMessage();
//            }

            String storeName = storesBean.getStoreName();
            if (!TextUtils.isEmpty(storeName)) textNameStore.setText(storeName);

            if (!VerificationUtils.isEmpty(saleDate))
                textDatas.setText(saleDate.substring(5, 10));
            if (todayOrderNum >= 0)
                textOrder.setText(String.valueOf(todayOrderNum));
            if (yesterdayGrowthOrderNum >= 0)
                textDayOrder.setText(String.valueOf(yesterdayGrowthOrderNum));
            List<SalesDataBean.StoresBean.SaleDetailInfosBeanX> saleDetailInfos = storesBean.getSaleDetailInfos();
            LogUtils.d(TAG, "=======v======" + saleDetailInfos.size() + "");
            benfangfa1(saleDetailInfos);
//            for (int i = 0; i < stores.size(); i++) {
//                Log.d("====fg======", "stores" + stores.size() + "=======" + stores.get(i).getStoreCode());
//                SalesDataBean.StoresBean storesBean = stores.get(i);
//                String saleDate = storesBean.getSaleDate();//日期 2011-01-01
//                int todayOrderNum = storesBean.getTodayOrderNum();//今日销量
//                int yesterdayGrowthOrderNum = storesBean.getYesterdayGrowthOrderNum();//较上日增加量
//                try {
//                    Logger.d("-----------", saleDate + "==" + todayOrderNum + "==" + yesterdayGrowthOrderNum);
//                } catch (Error e) {
//                    e.getMessage();
//                }
//                if (!VerificationUtils.isEmpty(saleDate))
//                    textDatas.setText(saleDate.substring(5, 10));
//                if (todayOrderNum >= 0)
//                    textOrder.setText(String.valueOf(todayOrderNum));
//                if (yesterdayGrowthOrderNum >= 0)
//                    textDayOrder.setText(String.valueOf(yesterdayGrowthOrderNum));
//                List<SalesDataBean.StoresBean.SaleDetailInfosBeanX> saleDetailInfos = storesBean.getSaleDetailInfos();
//                Log.d("=======v======", saleDetailInfos.size() + "");
//
//                if (saleDetailInfos.size() == 0) {
//
//                } else if (saleDetailInfos.size() == 1) {
//                    textTypeOne.setText(saleDetailInfos.get(0).getMaterialTypeName());//类型
//                    textBottleOne.setText(String.valueOf(saleDetailInfos.get(0).getOrderBottleNum()));//多少瓶
//
//                } else if (saleDetailInfos.size() == 2) {
//                    textTypeOne.setText(saleDetailInfos.get(0).getMaterialTypeName());//类型
//                    textBottleOne.setText(String.valueOf(saleDetailInfos.get(0).getOrderBottleNum()));//多少瓶
//                    textTypeTwo.setText(saleDetailInfos.get(1).getMaterialTypeName());//类型
//                    textBottleTwo.setText(String.valueOf(saleDetailInfos.get(1).getOrderBottleNum()));//多少瓶
//
//                } else if (saleDetailInfos.size() == 3) {
//                    textTypeOne.setText(saleDetailInfos.get(0).getMaterialTypeName());//类型
//                    textBottleOne.setText(String.valueOf(saleDetailInfos.get(0).getOrderBottleNum()));//多少瓶
//                    textTypeTwo.setText(saleDetailInfos.get(1).getMaterialTypeName());//类型
//                    textBottleTwo.setText(String.valueOf(saleDetailInfos.get(1).getOrderBottleNum()));//多少瓶
//                    textTypeThree.setText(saleDetailInfos.get(2).getMaterialTypeName());//类型
//                    textBottleThree.setText(String.valueOf(saleDetailInfos.get(2).getOrderBottleNum()));//多少瓶
//                }
////                if (saleDetailInfos.size() == 3) {
////                    textTypeOne.setText(saleDetailInfos.get(0).getMaterialTypeName());//类型
////                    textBottleOne.setText(String.valueOf(saleDetailInfos.get(0).getOrderBottleNum()));//多少瓶
////                    textTypeTwo.setText(saleDetailInfos.get(1).getMaterialTypeName());//类型
////                    textBottleTwo.setText(String.valueOf(saleDetailInfos.get(1).getOrderBottleNum()));//多少瓶
////                    textTypeThree.setText(saleDetailInfos.get(2).getMaterialTypeName());//类型
////                    textBottleThree.setText(String.valueOf(saleDetailInfos.get(2).getOrderBottleNum()));//多少瓶
////                }
//            }
        } else {
            if (stores.size() <= 0) {
                LogUtils.d(TAG, "门店网络请求---网络请求返回成功---数据data里面没有stores数据");
                storeTextPrompt.setVisibility(View.VISIBLE);
            } else {
                storeTextPrompt.setVisibility(View.GONE);
            }
            initView(stores);
        }
    }


    /**
     * rec
     */
    private void initView(List<SalesDataBean.StoresBean> stores) {
        storRec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        Comparator<SalesDataBean.StoresBean> comparator = new PriceHighToLowComparator();
        Collections.sort(stores, comparator);

        myAdapter = new MyAdapter(getContext(), stores, R.layout.store_rec_item);
        storRec.setAdapter(myAdapter);
        myAdapter.setmOnItemListener(new CommonRecyclerAdapter.OnItemListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                initNetWorkeHotTitle(position);
                //initTableVp();
                //MainActivity.mBehavior.hide();
                storRec.setVisibility(View.GONE);
                relHot.setVisibility(View.VISIBLE);
                lineChart.setVisibility(View.VISIBLE);

                cardView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLongClickListener(View view, int position) {

            }
        });
    }


    private void initNetWorkeHotTitle(int position) {
        TYPE = 1;

        /******  此处列表改用本地的  *****/
        String json = (String) SharePreferenceUtils.get(getContext(), "json", "");
        if (TextUtils.isEmpty(json)) return;
        SalesDataBean salesDataBean = new Gson().fromJson(json, SalesDataBean.class);
        List<SalesDataBean.StoresBean> stores = salesDataBean.getStores();
        if (stores == null || stores.size() <= 0) return;
        storeCode = stores.get(position).getStoreCode();
        LogUtils.d(TAG, "------storeCode------" + storeCode + "---" + userID);
        netWorkeCall(stores, position);
        initTableVp();


//        if (!NetworkUtils.isNetworkAvailable(getContext())) {
//            PromptUtils.showToast(getContext(), getResources().getString(R.string.string_check_net_ok));
//            return;
//        }
//        AnApiServiceManger.getInstance().getTodaySaleInfoByUserIds
//                (new ProgressSubscriber<SalesDataBean>(subscriberOnNextListener, getContext(), null));


//        AnApiServiceManger.getInstance().getTodaySaleInfoByUserId()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<SalesResponseBean>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Logger.d(TAG, e.toString());
//                        PromptUtils.showToast(getActivity(), e.getMessage());
//                        PromptUtils.closeProgressDialog();
//                    }
//
//                    @Override
//                    public void onNext(SalesResponseBean salesResponseBean) {
//                        if (salesResponseBean.getCode() == 200) {
//                            SalesResponseBean.DataBean data = salesResponseBean.getData();
//                            if (data.getStores() == null) return;
//                            List<SalesResponseBean.DataBean.StoresBean> stores = data.getStores();
//                            for (int i = 0; i < stores.size(); i++) {
//                                Logger.d("==========", "stores" + stores.size());
//                                SalesResponseBean.DataBean.StoresBean storesBean = stores.get(i);
//                                String saleDate = storesBean.getSaleDate();//日期 2011-01-01
//                                int todayOrderNum = storesBean.getTodayOrderNum();//今日销量
//                                int yesterdayGrowthOrderNum = storesBean.getYesterdayGrowthOrderNum();//较上日增加量
//                                try {
//                                    Logger.d("-----------", saleDate + "==" + todayOrderNum + "==" + yesterdayGrowthOrderNum);
//                                } catch (Error e) {
//                                    e.getMessage();
//                                }
//                                if (!VerificationUtils.isEmpty(saleDate))
//                                    textDatas.setText(saleDate.substring(5, 10));
//                                if (todayOrderNum >= 0)
//                                    textOrder.setText(String.valueOf(todayOrderNum));
//                                if (yesterdayGrowthOrderNum >= 0)
//                                    textDayOrder.setText(String.valueOf(yesterdayGrowthOrderNum));
//                                List<SalesResponseBean.DataBean.StoresBean.SaleDetailInfosBeanX> saleDetailInfos = storesBean.getSaleDetailInfos();
//                                if (saleDetailInfos.size() == 3) {
//                                    textTypeOne.setText(saleDetailInfos.get(0).getMaterialTypeName());//类型
//                                    textBottleOne.setText(String.valueOf(saleDetailInfos.get(0).getOrderBottleNum()));//多少瓶
//                                    textTypeTwo.setText(saleDetailInfos.get(1).getMaterialTypeName());//类型
//                                    textBottleTwo.setText(String.valueOf(saleDetailInfos.get(1).getOrderBottleNum()));//多少瓶
//                                    textTypeThree.setText(saleDetailInfos.get(2).getMaterialTypeName());//类型
//                                    textBottleThree.setText(String.valueOf(saleDetailInfos.get(2).getOrderBottleNum()));//多少瓶
//                                }
//                            }
//                        }
//
//                        if (!VerificationUtils.isEmpty(salesResponseBean.getMsg())) {
//                            PromptUtils.showToast(getActivity(), salesResponseBean.getMsg());
//                        }
//                        PromptUtils.closeProgressDialog();
//                    }
//                });
    }


    private void initNetWorke() {
        storeTextPrompt.setVisibility(View.GONE);
        LogUtils.d(TAG, "门店开始加载已保存的ＪＳＯＮ");
        TYPE = 0;
//        if (!NetworkUtils.isNetworkAvailable(getContext())) {
//            PromptUtils.showToast(getContext(), getResources().getString(R.string.string_check_net_ok));
//            return;
//        }
//        AnApiServiceManger.getInstance().getTodaySaleInfoByUserIds
//                (new ProgressSubscriber<SalesDataBean>(subscriberOnNextListener, getContext(), null));

        /******  此处列表改用本地的  *****/
        String json = (String) SharePreferenceUtils.get(getContext(), "json", "");
        if (TextUtils.isEmpty(json)) return;
        SalesDataBean salesDataBean = new Gson().fromJson(json, SalesDataBean.class);
        List<SalesDataBean.StoresBean> stores = salesDataBean.getStores();
        if (stores == null || stores.size() <= 0) return;
        initView(stores);
//        PromptUtils.showProgressDialog(getActivity(), getResources().getString(R.string.string_loading));
//        AnApiServiceManger.getInstance().getTodaySaleInfoByUserId()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<SalesResponseBean>() {
//                    @Override
//                    public void onCompleted() {
//                        Logger.d(TAG + "门店网络请求---网络链接");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Logger.d(TAG + "门店网络请求---网络错误" + e.getMessage());
//                        PromptUtils.showToast(getActivity(), e.getMessage());
//                        PromptUtils.closeProgressDialog();
//                    }
//
//                    @Override
//                    public void onNext(SalesResponseBean salesResponseBean) {
//
//                        if (salesResponseBean.getCode() == 200) {
//                            Logger.d(TAG + "门店网络请求---网络请求返回成功");
//                            SalesResponseBean.DataBean data = salesResponseBean.getData();
//                            if (data == null) {
//                                Logger.d(TAG + "门店网络请求---网络请求返回成功-没有数据data");
//                                return;
//                            }
//                            List<SalesResponseBean.DataBean.StoresBean> stores = data.getStores();
//                            if (stores.size() <= 0) {
//                                Logger.d(TAG + "门店网络请求---网络请求返回成功---数据data里面没有stores数据");
//                                storeTextPrompt.setVisibility(View.VISIBLE);
//                            } else {
//                                storeTextPrompt.setVisibility(View.GONE);
//                            }
//                            initView(stores);
////                            String saleDate = company.getSaleDate();//日期 2011-01-01
////                            int todayOrderNum = company.getTodayOrderNum();//今日销量
////                            int yesterdayGrowthOrderNum = company.getYesterdayGrowthOrderNum();//较上日增加量
////                            textDatas.setText(saleDate.substring(5, 10));
////                            textOrder.setText(String.valueOf(todayOrderNum));
////                            textDayOrder.setText(String.valueOf(yesterdayGrowthOrderNum));
////                            List<SalesResponseBean.LoginDataBean.CompanyBean.SaleDetailInfosBean>
////                                    saleDetailInfos = company.getSaleDetailInfos();
////                            if (saleDetailInfos.size() == 3) {
////                                textTypeOne.setText(saleDetailInfos.get(0).getMaterialTypeName());//类型
////                                textBottleOne.setText(String.valueOf(saleDetailInfos.get(0).getOrderBottleNum()));//多少瓶
////                                textTypeTwo.setText(saleDetailInfos.get(1).getMaterialTypeName());//类型
////                                textBottleTwo.setText(String.valueOf(saleDetailInfos.get(1).getOrderBottleNum()));//多少瓶
////                                textTypeThree.setText(saleDetailInfos.get(2).getMaterialTypeName());//类型
////                                textBottleThree.setText(String.valueOf(saleDetailInfos.get(2).getOrderBottleNum()));//多少瓶
////                            }
//                        }
//
//
//                        if (!VerificationUtils.isEmpty(salesResponseBean.getMsg())) {
//                            PromptUtils.showToast(getActivity(), salesResponseBean.getMsg());
//                        }
//                        PromptUtils.closeProgressDialog();
//
//
//                    }
//                });
    }


    /**
     * ViewPager+TableLayout
     */

    private void initTableVp() {
        views = new ArrayList<>();
        //addDatas();
        BaseUse.addDatas(views, getActivity(), userID, storeCode);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter();
        salesPagers.setAdapter(myPagerAdapter);
        salesTables.setupWithViewPager(salesPagers);
        salesTables.getTabAt(0).setText(getResources().getString(R.string.string_day));//日
        salesTables.getTabAt(1).setText(getResources().getString(R.string.string_moth));//月
        salesTables.getTabAt(2).setText(getResources().getString(R.string.string_year));//年
        salesTables.setSelectedTabIndicatorHeight(2);
        salesTables.setSelectedTabIndicatorColor(getResources().getColor(R.color.appthemeColor));
        salesTables.setTabTextColors(getResources().getColor(R.color.textColor), getResources().getColor(R.color.appthemeColor));
//        salesPagers.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                mWebView.reload();  //刷新
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }

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
//        Log.d("==========ssss===", userID + "======" + storeCode);
//        setWebViews(dayView, AnCommonUrl.DayChar_Url + "userId=" + userID + "&storeCode=" + storeCode);
//        views.add(dayView);
//
//        //月
//        View mothView = LayoutInflater.from(getContext()).inflate(R.layout.chart_moth_item, null, false);
//        //initMothView(mothView);
//        setWebViews(mothView, AnCommonUrl.MothChar_Url + "userId=" + userID + "&storeCode=" + storeCode);
//        views.add(mothView);
//
//        //年
//        View yearsView = LayoutInflater.from(getContext()).inflate(R.layout.chart_year_item, null, false);
//        //initYearView(yearsView);
//        setWebViews(yearsView, AnCommonUrl.YearChar_Url + "userId=" + userID + "&storeCode=" + storeCode);
//        views.add(yearsView);
//    }
//
//
//    /**
//     * WebView 加载展示图表
//     *
//     * @param view
//     * @param url
//     */
//    public void setWebViews(View view, String url) {
//        //此处改为WebView试试
//        mWebView = (WebView) view.findViewById(R.id.blog_detail_webview);
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
//
//    }


//    @OnClick(R.id.lines_hot)
//    public void onViewClicked() {
////        linesHot.setVisibility(View.GONE);
////        lineChart.setVisibility(View.GONE);
////        storRec.setVisibility(View.VISIBLE);
//    }


    @OnClick({R.id.text_datas, R.id.card_store_names})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_datas:
                String s = TimeUtils.todayYyyyMmDd();
                Log.d("------", s);
                //时间选择对话框
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.ThemeDialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Log.d("----选择的日期是-----", year + "年" + (month + 1) + "月" + dayOfMonth + "日");
                        PromptUtils.showToast(getContext(), year + "年" + (month + 1) + "月" + dayOfMonth + "日");
                    }
                }, Integer.valueOf(s.substring(0, 4)), Integer.valueOf(s.substring(5, 7)) - 1, Integer.valueOf(s.substring(8, 10)));
                datePickerDialog.show();
                break;
            case R.id.card_store_names:

                relHot.setVisibility(View.GONE);
                lineChart.setVisibility(View.GONE);
                storRec.setVisibility(View.VISIBLE);
                cardView.setVisibility(View.GONE);
                break;
        }
    }


    /**
     * rec---适配器
     */
    private class MyAdapter extends CommonRecyclerAdapter<SalesDataBean.StoresBean> {
        public MyAdapter(Context context, List<SalesDataBean.StoresBean> data, int layoutId) {
            super(context, data, layoutId);
        }

        @Override
        public void convert(MyViewHolder holder, SalesDataBean.StoresBean item) {

            holder.setText(R.id.tv_names, item.getStoreName());//名称
            holder.setText(R.id.tv_totalSales, String.valueOf(item.getTodayOrderNum()));//今日总销量
            List<SalesDataBean.StoresBean.SaleDetailInfosBeanX> saleDetailInfos = item.getSaleDetailInfos();
            LogUtils.d(TAG, "-----=========--" + saleDetailInfos.size() + "");
            benfangfa2(saleDetailInfos, holder);
        }
    }


    private class MyPagerAdapter extends PagerAdapter {
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        /**  广播解绑 **/
        getContext().unregisterReceiver(myBroadCastReceiver);
    }


    /******  用于关闭打开的item和返回上级界面  ******/
    private MyBroadCastReceiver myBroadCastReceiver;

    /**
     * 广播注册
     */
    private void regeditReceiver() {
        myBroadCastReceiver = new MyBroadCastReceiver();
        IntentFilter intFilter = new IntentFilter();
        intFilter.addAction(AnCommonUrl.BROADCAST + AnCommonUrl.StoreFragment);
        intFilter.addAction("com.suchengkeji.android");//点击门店是刷新Adapter并给rec添加数据
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


            if (name.equals("com.suchengkeji.android")) {

                relHot.setVisibility(View.GONE);
                lineChart.setVisibility(View.GONE);
                storRec.setVisibility(View.VISIBLE);

                cardView.setVisibility(View.GONE);
                //MainActivity.mBehavior.show();
                initNetWorke();
                myAdapter.notifyDataSetChanged();
                return;
            }

            if (name.equals("com.suchengkeji.android.liquidgas.receivers.NETCHANGE")) {
                //检查网络状态的类型
                int networkState = NetworkUtils.getNetworkState(context);
                // 接口回传网络状态的类型
                if (networkState != 0) {
                    initTableVp();
                }
                return;
            }


            if (VerificationUtils.isEmpty(name) || !name.equals(AnCommonUrl.BROADCAST + AnCommonUrl.StoreFragment)) {
                getActivity().finish();
            }

            if (relHot.getVisibility() == View.VISIBLE || lineChart.getVisibility() == View.VISIBLE) {
                if (BaseUse.getIsStoreFragmentHidden()) {
                    relHot.setVisibility(View.GONE);
                    lineChart.setVisibility(View.GONE);
                    storRec.setVisibility(View.VISIBLE);

                    cardView.setVisibility(View.GONE);
                    //MainActivity.mBehavior.show();
                    initNetWorke();
                    myAdapter.notifyDataSetChanged();
                } else {
                    AppOutUtils.exit(getActivity());
                }
            } else {
                AppOutUtils.exit(getActivity());
            }


        }
    }

    /**
     * 方法不可取--可用rec
     * <p>
     * 为了显示出来
     *
     * @param saleDetailInfos
     */
    private void benfangfa1(List<SalesDataBean.StoresBean.SaleDetailInfosBeanX> saleDetailInfos) {
        if (saleDetailInfos.size() == 0) {
            textTypeOne.setText(getResources().getString(R.string.string_type_15));//类型
            textBottleOne.setText(String.valueOf(0));//多少瓶
            textTypeTwo.setText(getResources().getString(R.string.string_type_50));//类型
            textBottleTwo.setText(String.valueOf(0));//多少瓶
            textTypeThree.setText(getResources().getString(R.string.string_type_5));//类型
            textBottleThree.setText(String.valueOf(0));//多少瓶
        } else if (saleDetailInfos.size() == 1) {
            textTypeOne.setText(saleDetailInfos.get(0).getMaterialTypeName());//类型
            textBottleOne.setText(String.valueOf(saleDetailInfos.get(0).getOrderBottleNum()));//多少瓶
            if (saleDetailInfos.get(0).getMaterialTypeName().equals(getResources().getString(R.string.string_type_15))) {
                textTypeTwo.setText(getResources().getString(R.string.string_type_50));//类型
                textBottleTwo.setText(String.valueOf(0));//多少瓶
                textTypeThree.setText(getResources().getString(R.string.string_type_5));//类型
                textBottleThree.setText(String.valueOf(0));//多少瓶
            } else if (saleDetailInfos.get(0).getMaterialTypeName().equals(getResources().getString(R.string.string_type_50))) {
                textTypeTwo.setText(getResources().getString(R.string.string_type_15));//类型
                textBottleTwo.setText(String.valueOf(0));//多少瓶
                textTypeThree.setText(getResources().getString(R.string.string_type_5));//类型
                textBottleThree.setText(String.valueOf(0));//多少瓶
            } else if (saleDetailInfos.get(0).getMaterialTypeName().equals(getResources().getString(R.string.string_type_5))) {
                textTypeTwo.setText(getResources().getString(R.string.string_type_15));//类型
                textBottleTwo.setText(String.valueOf(0));//多少瓶
                textTypeThree.setText(getResources().getString(R.string.string_type_50));//类型
                textBottleThree.setText(String.valueOf(0));//多少瓶
            }
        } else if (saleDetailInfos.size() == 2) {
            textTypeOne.setText(saleDetailInfos.get(0).getMaterialTypeName());//类型
            textBottleOne.setText(String.valueOf(saleDetailInfos.get(0).getOrderBottleNum()));//多少瓶
            textTypeTwo.setText(saleDetailInfos.get(1).getMaterialTypeName());//类型
            textBottleTwo.setText(String.valueOf(saleDetailInfos.get(1).getOrderBottleNum()));//多少瓶
            LogUtils.d(TAG, "-----===-----" + saleDetailInfos.get(0).getMaterialTypeName() + "===" + saleDetailInfos.get(1).getMaterialTypeName());

            if (saleDetailInfos.get(0).getMaterialTypeName().equals(getResources().getString(R.string.string_type_15))
                    && saleDetailInfos.get(1).getMaterialTypeName().equals(getResources().getString(R.string.string_type_50))) {
                textTypeThree.setText(getResources().getString(R.string.string_type_5));//类型
                textBottleThree.setText(String.valueOf(0));//多少瓶
            } else if (saleDetailInfos.get(0).getMaterialTypeName().equals(getResources().getString(R.string.string_type_50))
                    && saleDetailInfos.get(1).getMaterialTypeName().equals(getResources().getString(R.string.string_type_15))) {
                textTypeThree.setText(getResources().getString(R.string.string_type_5));//类型
                textBottleThree.setText(String.valueOf(0));//多少瓶
            } else if (saleDetailInfos.get(0).getMaterialTypeName().equals(getResources().getString(R.string.string_type_15))
                    && saleDetailInfos.get(1).getMaterialTypeName().equals(getResources().getString(R.string.string_type_5))) {
                textTypeThree.setText(getResources().getString(R.string.string_type_50));//类型
                textBottleThree.setText(String.valueOf(0));//多少瓶
            } else if (saleDetailInfos.get(0).getMaterialTypeName().equals(getResources().getString(R.string.string_type_5))
                    && saleDetailInfos.get(1).getMaterialTypeName().equals(getResources().getString(R.string.string_type_15))) {
                textTypeThree.setText(getResources().getString(R.string.string_type_50));//类型
                textBottleThree.setText(String.valueOf(0));//多少瓶
            } else if (saleDetailInfos.get(0).getMaterialTypeName().equals(getResources().getString(R.string.string_type_50))
                    && saleDetailInfos.get(1).getMaterialTypeName().equals(getResources().getString(R.string.string_type_5))) {
                textTypeThree.setText(getResources().getString(R.string.string_type_15));//类型
                textBottleThree.setText(String.valueOf(0));//多少瓶
            } else if (saleDetailInfos.get(0).getMaterialTypeName().equals(getResources().getString(R.string.string_type_5))
                    && saleDetailInfos.get(1).getMaterialTypeName().equals(getResources().getString(R.string.string_type_50))) {
                textTypeThree.setText(getResources().getString(R.string.string_type_15));//类型
                textBottleThree.setText(String.valueOf(0));//多少瓶
            }
        } else if (saleDetailInfos.size() == 3) {
            textTypeOne.setText(saleDetailInfos.get(0).getMaterialTypeName());//类型
            textBottleOne.setText(String.valueOf(saleDetailInfos.get(0).getOrderBottleNum()));//多少瓶
            textTypeTwo.setText(saleDetailInfos.get(1).getMaterialTypeName());//类型
            textBottleTwo.setText(String.valueOf(saleDetailInfos.get(1).getOrderBottleNum()));//多少瓶
            textTypeThree.setText(saleDetailInfos.get(2).getMaterialTypeName());//类型
            textBottleThree.setText(String.valueOf(saleDetailInfos.get(2).getOrderBottleNum()));//多少瓶
        }
    }


    private void benfangfa2(List<SalesDataBean.StoresBean.SaleDetailInfosBeanX> saleDetailInfos, MyViewHolder holder) {
        if (saleDetailInfos.size() == 0) {
            holder.setText(R.id.tv_type5, getResources().getString(R.string.string_type_15));
            holder.setText(R.id.tv_bottle_number5, String.valueOf(0));
            holder.setText(R.id.tv_type15, getResources().getString(R.string.string_type_50));
            holder.setText(R.id.tv_bottle_number15, String.valueOf(0));
            holder.setText(R.id.tv_type50, getResources().getString(R.string.string_type_5));
            holder.setText(R.id.tv_bottle_number50, String.valueOf(0));
        } else if (saleDetailInfos.size() == 1) {
            holder.setText(R.id.tv_type5, saleDetailInfos.get(0).getMaterialTypeName());
            holder.setText(R.id.tv_bottle_number5, String.valueOf(saleDetailInfos.get(0).getOrderBottleNum()));
            if (saleDetailInfos.get(0).getMaterialTypeName().equals(getResources().getString(R.string.string_type_15))) {
                holder.setText(R.id.tv_type15, getResources().getString(R.string.string_type_50));
                holder.setText(R.id.tv_bottle_number15, String.valueOf(0));
                holder.setText(R.id.tv_type50, getResources().getString(R.string.string_type_5));
                holder.setText(R.id.tv_bottle_number50, String.valueOf(0));
            } else if (saleDetailInfos.get(0).getMaterialTypeName().equals(getResources().getString(R.string.string_type_50))) {
                holder.setText(R.id.tv_type15, getResources().getString(R.string.string_type_15));
                holder.setText(R.id.tv_bottle_number15, String.valueOf(0));
                holder.setText(R.id.tv_type50, getResources().getString(R.string.string_type_5));
                holder.setText(R.id.tv_bottle_number50, String.valueOf(0));
            } else if (saleDetailInfos.get(0).getMaterialTypeName().equals(getResources().getString(R.string.string_type_5))) {
                holder.setText(R.id.tv_type15, getResources().getString(R.string.string_type_15));
                holder.setText(R.id.tv_bottle_number15, String.valueOf(0));
                holder.setText(R.id.tv_type50, getResources().getString(R.string.string_type_50));
                holder.setText(R.id.tv_bottle_number50, String.valueOf(0));
            }
        } else if (saleDetailInfos.size() == 2) {
            holder.setText(R.id.tv_type5, saleDetailInfos.get(0).getMaterialTypeName());
            holder.setText(R.id.tv_bottle_number5, String.valueOf(saleDetailInfos.get(0).getOrderBottleNum()));
            holder.setText(R.id.tv_type15, saleDetailInfos.get(1).getMaterialTypeName());
            holder.setText(R.id.tv_bottle_number15, String.valueOf(saleDetailInfos.get(1).getOrderBottleNum()));

            LogUtils.d(TAG, "-----===-----" + saleDetailInfos.get(0).getMaterialTypeName() + "===" + saleDetailInfos.get(1).getMaterialTypeName());
            if (saleDetailInfos.get(0).getMaterialTypeName().equals(getResources().getString(R.string.string_type_15))
                    && saleDetailInfos.get(1).getMaterialTypeName().equals(getResources().getString(R.string.string_type_50))) {
                holder.setText(R.id.tv_type50, getResources().getString(R.string.string_type_5));
                holder.setText(R.id.tv_bottle_number50, String.valueOf(0));
            } else if (saleDetailInfos.get(0).getMaterialTypeName().equals(getResources().getString(R.string.string_type_50))
                    && saleDetailInfos.get(1).getMaterialTypeName().equals(getResources().getString(R.string.string_type_15))) {
                holder.setText(R.id.tv_type50, getResources().getString(R.string.string_type_5));
                holder.setText(R.id.tv_bottle_number50, String.valueOf(0));
            } else if (saleDetailInfos.get(0).getMaterialTypeName().equals(getResources().getString(R.string.string_type_15))
                    && saleDetailInfos.get(1).getMaterialTypeName().equals(getResources().getString(R.string.string_type_5))) {
                holder.setText(R.id.tv_type50, getResources().getString(R.string.string_type_50));
                holder.setText(R.id.tv_bottle_number50, String.valueOf(0));
            } else if (saleDetailInfos.get(0).getMaterialTypeName().equals(getResources().getString(R.string.string_type_5))
                    && saleDetailInfos.get(1).getMaterialTypeName().equals(getResources().getString(R.string.string_type_15))) {
                holder.setText(R.id.tv_type50, getResources().getString(R.string.string_type_50));
                holder.setText(R.id.tv_bottle_number50, String.valueOf(0));
            } else if (saleDetailInfos.get(0).getMaterialTypeName().equals(getResources().getString(R.string.string_type_50))
                    && saleDetailInfos.get(1).getMaterialTypeName().equals(getResources().getString(R.string.string_type_5))) {
                holder.setText(R.id.tv_type50, getResources().getString(R.string.string_type_15));
                holder.setText(R.id.tv_bottle_number50, String.valueOf(0));
            } else if (saleDetailInfos.get(0).getMaterialTypeName().equals(getResources().getString(R.string.string_type_5))
                    && saleDetailInfos.get(1).getMaterialTypeName().equals(getResources().getString(R.string.string_type_50))) {
                holder.setText(R.id.tv_type50, getResources().getString(R.string.string_type_15));
                holder.setText(R.id.tv_bottle_number50, String.valueOf(0));
            }
        } else if (saleDetailInfos.size() == 3) {
            holder.setText(R.id.tv_type5, saleDetailInfos.get(0).getMaterialTypeName());
            holder.setText(R.id.tv_bottle_number5, String.valueOf(saleDetailInfos.get(0).getOrderBottleNum()));
            holder.setText(R.id.tv_type15, saleDetailInfos.get(1).getMaterialTypeName());
            holder.setText(R.id.tv_bottle_number15, String.valueOf(saleDetailInfos.get(1).getOrderBottleNum()));
            holder.setText(R.id.tv_type50, saleDetailInfos.get(2).getMaterialTypeName());
            holder.setText(R.id.tv_bottle_number50, String.valueOf(saleDetailInfos.get(2).getOrderBottleNum()));
        }
    }


}
