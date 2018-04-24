package com.suchengkeji.android.liquidgas.ui.acticity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.gson.Gson;
import com.suchengkeji.android.liquidgas.R;
import com.suchengkeji.android.liquidgas.api.AnApiServiceManger;
import com.suchengkeji.android.liquidgas.api.base.subscribers.ProgressSubscriber;
import com.suchengkeji.android.liquidgas.api.base.subscribers.SubscriberOnNextListener;
import com.suchengkeji.android.liquidgas.api.common.AnCommonUrl;
import com.suchengkeji.android.liquidgas.bean.SalesDataBean;
import com.suchengkeji.android.liquidgas.ui.base.BaseActivity;
import com.suchengkeji.android.liquidgas.ui.base.BaseInterfaceObserver;
import com.suchengkeji.android.liquidgas.ui.fragment.tab.CompanyFragment;
import com.suchengkeji.android.liquidgas.ui.fragment.tab.FranchiserFragment;
import com.suchengkeji.android.liquidgas.ui.fragment.tab.StoreFragment;
import com.suchengkeji.android.liquidgas.commontadapter.CommonRecyclerAdapter;
import com.suchengkeji.android.liquidgas.commontadapter.MyViewHolder;
import com.suchengkeji.android.liquidgas.utils.ActivityUtils;
import com.suchengkeji.android.liquidgas.utils.LogUtils;
import com.suchengkeji.android.liquidgas.utils.SharePreferenceUtils;
import com.suchengkeji.android.liquidgas.utils.TimeUtils;
import com.suchengkeji.android.liquidgas.utils.VerificationUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @aboutContent: 销量
 * @author： 安
 * @crateTime: 2017/12/28 18:09
 * @mailBox: an.****.life@gmail.com
 * @company: 东莞速成科技有限公司
 */

public class SalesActivity extends BaseActivity implements TabHost.OnTabChangeListener {
    private final String TAG = "===->>>" + this.getClass();
    @BindView(R.id.toolbar_back)
    ImageView toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_more)
    ImageView toolbarMore;
    @BindView(R.id.lines_hot)
    LinearLayout linearLayout;
    protected TabHost mTabHost;
    @BindView(R.id.text_type_one)
    TextView textTypeOne;
    @BindView(R.id.text_bottle_one)
    TextView textBottleOne;
    @BindView(R.id.text_type_two)
    TextView textTypeTwo;
    @BindView(R.id.text_bottle_two)
    TextView textBottleTwo;
    @BindView(R.id.text_type_three)
    TextView textTypeThree;
    @BindView(R.id.text_bottle_three)
    TextView textBottleThree;
    @BindView(R.id.text_datas)
    TextView textDatas;
    @BindView(R.id.text_order)
    TextView textOrder;
    @BindView(R.id.text_day_order)
    TextView textDayOrder;

//    @BindView(R.id.text_time_data)
//    TextView mtextTimeData;
//    @BindView(R.id.text_order_number)
//    TextView mtextOrderNumber;
//    @BindView(R.id.text_yearstoday_order)
//    TextView mtextYearstodayOrder;
//    @BindView(R.id.type_rec)
//    RecyclerView mtypeRec;

    @Override
    public void beforeStart() {
        BaseInterfaceObserver.getInstance().hideWindowFeature(SalesActivity.this, 1);
    }

    @Override
    public int setContentView() {
        return R.layout.activity_sales;
    }

    @Override
    public void setOtherContentDatas() {
        ActivityUtils.getScreenActivityUtils().addActivity(SalesActivity.this);
        initNetWorke();
        setToolbar();
        ininTabHost();
        textDatas.setText(String.valueOf(TimeUtils.todayYyyyMmDd()).substring(5, (TimeUtils.todayYyyyMmDd()).length()));
    }


    //private MyAdapter myAdapter;

    private void initNetWorke() {
        LogUtils.d(TAG, "此处列表改用本地的");

        /******  此处列表改用本地的  *****/
        String json = (String) SharePreferenceUtils.get(this, "json", "");
        if (TextUtils.isEmpty(json)) return;
        SalesDataBean salesDataBean = new Gson().fromJson(json, SalesDataBean.class);
        SalesDataBean.CompanyBean company = salesDataBean.getCompany();
        if (company == null) return;
        String saleDate = company.getSaleDate();//日期 2011-01-01
        int todayOrderNum = company.getTodayOrderNum();//今日销量
        int yesterdayGrowthOrderNum = company.getYesterdayGrowthOrderNum();//较上日增加量
        try {
            LogUtils.d(TAG, saleDate + "==" + todayOrderNum + "==" + yesterdayGrowthOrderNum + "===");
        } catch (Error e) {
            e.getMessage();
        }

        if (!VerificationUtils.isEmpty(saleDate)) {
            textDatas.setText(saleDate.substring(5, 10));
//                    mtextTimeData.setText(saleDate.substring(5, 10));
        }
        if (todayOrderNum >= 0) {
            textOrder.setText(String.valueOf(todayOrderNum));
//                    mtextOrderNumber.setText(String.valueOf(todayOrderNum));
        }
        if (yesterdayGrowthOrderNum >= 0) {
            textDayOrder.setText(String.valueOf(yesterdayGrowthOrderNum));
//                    mtextYearstodayOrder.setText(String.valueOf(yesterdayGrowthOrderNum));
        }
        List<SalesDataBean.CompanyBean.SaleDetailInfosBean> saleDetailInfos = company.getSaleDetailInfos();
        if (saleDetailInfos.size() <= 0) {
            LogUtils.d(TAG, "公司网络请求---网络返回成功---类型数据为空");
            return;
        }

//                mtypeRec.setLayoutManager(new GridLayoutManager(SalesActivity.this, 3));
//                myAdapter = new MyAdapter(SalesActivity.this, saleDetailInfos, R.layout.rec_hot_title_item);
//                mtypeRec.setAdapter(myAdapter);

        if (saleDetailInfos.size() == 3) {
            textTypeOne.setText(saleDetailInfos.get(0).getMaterialTypeName());//类型
            textBottleOne.setText(String.valueOf(saleDetailInfos.get(0).getOrderBottleNum()));//多少瓶
            textTypeTwo.setText(saleDetailInfos.get(1).getMaterialTypeName());//类型
            textBottleTwo.setText(String.valueOf(saleDetailInfos.get(1).getOrderBottleNum()));//多少瓶
            textTypeThree.setText(saleDetailInfos.get(2).getMaterialTypeName());//类型
            textBottleThree.setText(String.valueOf(saleDetailInfos.get(2).getOrderBottleNum()));//多少瓶
            LogUtils.d(TAG, "-----------" + saleDetailInfos.get(0).getOrderBottleNum() + "==" + saleDetailInfos.get(1).getOrderBottleNum()
                    + "==" + saleDetailInfos.get(2).getOrderBottleNum());
        }
//        AnApiServiceManger.getInstance().getTodaySaleInfoByUserIds(new ProgressSubscriber<SalesDataBean>(new SubscriberOnNextListener<SalesDataBean>() {
//            @Override
//            public void onNext(SalesDataBean salesDataBean) {
//                SalesDataBean.CompanyBean company = salesDataBean.getCompany();
//                if (company == null) {
//                    LogUtils.d(TAG, "公司网络请求---网络返回成功---公司数据为空");
//                    return;
//                }
//                String saleDate = company.getSaleDate();//日期 2011-01-01
//                int todayOrderNum = company.getTodayOrderNum();//今日销量
//                int yesterdayGrowthOrderNum = company.getYesterdayGrowthOrderNum();//较上日增加量
//                try {
//                    LogUtils.d(TAG, saleDate + "==" + todayOrderNum + "==" + yesterdayGrowthOrderNum + "===");
//                } catch (Error e) {
//                    e.getMessage();
//                }
//
//                if (!VerificationUtils.isEmpty(saleDate)) {
//                    textDatas.setText(saleDate.substring(5, 10));
////                    mtextTimeData.setText(saleDate.substring(5, 10));
//                }
//                if (todayOrderNum >= 0) {
//                    textOrder.setText(String.valueOf(todayOrderNum));
////                    mtextOrderNumber.setText(String.valueOf(todayOrderNum));
//                }
//                if (yesterdayGrowthOrderNum >= 0) {
//                    textDayOrder.setText(String.valueOf(yesterdayGrowthOrderNum));
////                    mtextYearstodayOrder.setText(String.valueOf(yesterdayGrowthOrderNum));
//                }
//                List<SalesDataBean.CompanyBean.SaleDetailInfosBean> saleDetailInfos = company.getSaleDetailInfos();
//                if (saleDetailInfos.size() <= 0) {
//                    LogUtils.d(TAG, "公司网络请求---网络返回成功---类型数据为空");
//                    return;
//                }
//
////                mtypeRec.setLayoutManager(new GridLayoutManager(SalesActivity.this, 3));
////                myAdapter = new MyAdapter(SalesActivity.this, saleDetailInfos, R.layout.rec_hot_title_item);
////                mtypeRec.setAdapter(myAdapter);
//
//                if (saleDetailInfos.size() == 3) {
//                    textTypeOne.setText(saleDetailInfos.get(0).getMaterialTypeName());//类型
//                    textBottleOne.setText(String.valueOf(saleDetailInfos.get(0).getOrderBottleNum()));//多少瓶
//                    textTypeTwo.setText(saleDetailInfos.get(1).getMaterialTypeName());//类型
//                    textBottleTwo.setText(String.valueOf(saleDetailInfos.get(1).getOrderBottleNum()));//多少瓶
//                    textTypeThree.setText(saleDetailInfos.get(2).getMaterialTypeName());//类型
//                    textBottleThree.setText(String.valueOf(saleDetailInfos.get(2).getOrderBottleNum()));//多少瓶
//                    LogUtils.d(TAG, "-----------" + saleDetailInfos.get(0).getOrderBottleNum() + "==" + saleDetailInfos.get(1).getOrderBottleNum()
//                            + "==" + saleDetailInfos.get(2).getOrderBottleNum());
//                }
//            }
//
//        }, SalesActivity.this, null));

        ////////////////

//        AnApiServiceManger.getInstance().getTodaySaleInfoByUserId()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<SalesResponseBean>() {
//                    @Override
//                    public void onCompleted() {
//                        Logger.d(TAG + "公司网络请求---网络链接");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Logger.d(TAG + "公司网络请求---网络错误" + e.getLocalizedMessage());
//                        PromptUtils.showToast(SalesActivity.this, e.getMessage());
//                        PromptUtils.closeProgressDialog();
//                    }
//
//                    @Override
//                    public void onNext(SalesResponseBean salesResponseBean) {
//
//                        if (salesResponseBean.getCode() == 200) {
//                            Logger.d(TAG + "公司网络请求---网络返回成功");
//                            SalesResponseBean.DataBean data = salesResponseBean.getData();
//                            SalesResponseBean.DataBean.CompanyBean company = data.getCompany();
//                            if (company == null) {
//                                Logger.d(TAG + "公司网络请求---网络返回成功---公司数据为空");
//                                return;
//                            }
//                            String saleDate = company.getSaleDate();//日期 2011-01-01
//                            int todayOrderNum = company.getTodayOrderNum();//今日销量
//                            int yesterdayGrowthOrderNum = company.getYesterdayGrowthOrderNum();//较上日增加量
//                            try {
//                                Logger.d(TAG + "-----------", saleDate + "==" + todayOrderNum + "==" + yesterdayGrowthOrderNum + "===");
//                            } catch (Error e) {
//                                e.getMessage();
//                            }
//
//                            if (!VerificationUtils.isEmpty(saleDate)) {
//                                textDatas.setText(saleDate.substring(5, 10));
//                                mtextTimeData.setText(saleDate.substring(5, 10));
//                            }
//                            if (todayOrderNum >= 0) {
//                                textOrder.setText(String.valueOf(todayOrderNum));
//                                mtextOrderNumber.setText(String.valueOf(todayOrderNum));
//                            }
//                            if (yesterdayGrowthOrderNum >= 0) {
//                                textDayOrder.setText(String.valueOf(yesterdayGrowthOrderNum));
//                                mtextYearstodayOrder.setText(String.valueOf(yesterdayGrowthOrderNum));
//                            }
//                            List<SalesResponseBean.DataBean.CompanyBean.SaleDetailInfosBean>
//                                    saleDetailInfos = company.getSaleDetailInfos();
//                            if (saleDetailInfos.size() <= 0) {
//                                Logger.d(TAG + "公司网络请求---网络返回成功---类型数据为空");
//                                return;
//                            }
//
//                            myAdapter = new MyAdapter(SalesActivity.this, saleDetailInfos, R.layout.rec_hot_title_item);
//                            mtypeRec.setAdapter(myAdapter);
//                            if (saleDetailInfos.size() == 3) {
//                                textTypeOne.setText(saleDetailInfos.get(0).getMaterialTypeName());//类型
//                                textBottleOne.setText(String.valueOf(saleDetailInfos.get(0).getOrderBottleNum()));//多少瓶
//                                textTypeTwo.setText(saleDetailInfos.get(1).getMaterialTypeName());//类型
//                                textBottleTwo.setText(String.valueOf(saleDetailInfos.get(1).getOrderBottleNum()));//多少瓶
//                                textTypeThree.setText(saleDetailInfos.get(2).getMaterialTypeName());//类型
//                                textBottleThree.setText(String.valueOf(saleDetailInfos.get(2).getOrderBottleNum()));//多少瓶
//                                Logger.d("-----------", saleDetailInfos.get(0).getOrderBottleNum() + "==" + saleDetailInfos.get(1).getOrderBottleNum()
//                                        + "==" + saleDetailInfos.get(2).getOrderBottleNum());
//                            }
//                        }
//
//                        if (!VerificationUtils.isEmpty(salesResponseBean.getMsg())) {
//                            Logger.d(TAG, salesResponseBean.getMsg());
//                            PromptUtils.showToast(SalesActivity.this, salesResponseBean.getMsg());
//                        }
//                        PromptUtils.closeProgressDialog();
//
//
//                    }
//                });
    }

    /**
     * TabHost添加设置
     */
    private void ininTabHost() {
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();

        View inflateCompany = LayoutInflater.from(this).inflate(R.layout.sales_menu_item_company, null, false);
        View inflateStore = LayoutInflater.from(this).inflate(R.layout.sales_menu_item_store, null, false);
        View inflateFranchiser = LayoutInflater.from(this).inflate(R.layout.sales_menu_item_franchiser, null, false);

        //添加“主页”Tab到TabHost控件中
        mTabHost.addTab(mTabHost.newTabSpec("company")
                .setIndicator(inflateCompany)//设置Tab标签和图标
                .setContent(R.id.line_one));        //设置Tab内容

        //添加“时间”Tab到TabHost控件中
        mTabHost.addTab(mTabHost.newTabSpec("store")
                .setIndicator(inflateStore)
                .setContent(R.id.line_two));


        //添加“联系人”Tab到TabHost控件中
        mTabHost.addTab(mTabHost.newTabSpec("franchiser")
                .setIndicator(inflateFranchiser)
                .setContent(R.id.line_three));

//        mTabHost.setBackgroundResource(R.mipmap.ic_launcher);  //设置TabHost控件的背景图片
        //设置当前显示第一个Tab
        mTabHost.setCurrentTab(0);
        //TabHost改变监听
        mTabHost.setOnTabChangedListener(this);
        //公司
        CompanyFragment companyFragment = new CompanyFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.line_one, companyFragment).commit();
        //门店
        StoreFragment storeFragment = new StoreFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.line_two, storeFragment).commit();
        //加盟商
        FranchiserFragment franchiserFragment = new FranchiserFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.line_three, franchiserFragment).commit();
    }

    /**
     * 标题设置
     */
    private void setToolbar() {
        toolbarTitle.setText(getResources().getString(R.string.string_sales));//销量
    }

    private static boolean isStoreFragmentHidden = false;//识别是否在当前StoreFragment页面
    private static boolean isFranchiserFragmentHidden = false;//识别是否在当前FranchiserFragment页面

    public static boolean getIsStoreFragmentHidden() {
        return isStoreFragmentHidden;
    }

    public static boolean getIsFranchiserFragmentHidden() {
        return isFranchiserFragmentHidden;
    }

    @OnClick({R.id.toolbar_back, R.id.toolbar_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                if (isStoreFragmentHidden) {
                    sendMyBroadcast(AnCommonUrl.StoreFragment);
                } else if (isFranchiserFragmentHidden) {
                    sendMyBroadcast(AnCommonUrl.FranchiserFragment);
                } else {
                    finish();
                }
                break;
            case R.id.toolbar_more:
                //PromptUtils.showToast(this, "more....");
                break;
        }
    }


    /**
     * 返回键拦截
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (isStoreFragmentHidden) {
                sendMyBroadcast(AnCommonUrl.StoreFragment);
            } else if (isFranchiserFragmentHidden) {
                sendMyBroadcast(AnCommonUrl.FranchiserFragment);
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 发送一个点击的广播
     *
     * @param tag
     */
    private void sendMyBroadcast(String tag) {
        Intent intent = new Intent();
        //设置intent的动作为com.example.broadcast，可以任意定义
        intent.setAction(AnCommonUrl.BROADCAST + tag);
        //发送无序广播
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 实现TabHost改变监听回掉
     *
     * @param tabId
     */
    @Override
    public void onTabChanged(String tabId) {
        if (mTabHost.getCurrentTab() == 1) {
            linearLayout.setVisibility(View.GONE);
            isStoreFragmentHidden = true;
            isFranchiserFragmentHidden = false;
        } else if (mTabHost.getCurrentTab() == 2) {
            linearLayout.setVisibility(View.GONE);
            isStoreFragmentHidden = false;
            isFranchiserFragmentHidden = true;
        } else {
            linearLayout.setVisibility(View.VISIBLE);
            isStoreFragmentHidden = false;
            isFranchiserFragmentHidden = false;
        }
    }


    /**
     * rec---适配器
     */
    public class MyAdapter extends CommonRecyclerAdapter<SalesDataBean.CompanyBean.SaleDetailInfosBean> {


        public MyAdapter(Context context, List<SalesDataBean.CompanyBean.SaleDetailInfosBean> data, int layoutId) {
            super(context, data, layoutId);
        }

        @Override
        public void convert(MyViewHolder holder, SalesDataBean.CompanyBean.SaleDetailInfosBean item) {

            holder.setText(R.id.text_types, item.getMaterialTypeName());//类型
            holder.setText(R.id.text_bottle_numbers, String.valueOf(item.getOrderBottleNum()));//多少瓶
        }
    }
}
