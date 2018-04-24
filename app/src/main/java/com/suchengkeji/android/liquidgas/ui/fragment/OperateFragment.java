package com.suchengkeji.android.liquidgas.ui.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.gson.Gson;
import com.suchengkeji.android.liquidgas.R;
import com.suchengkeji.android.liquidgas.api.AnApiServiceManger;
import com.suchengkeji.android.liquidgas.api.base.subscribers.ProgressSubscriber;
import com.suchengkeji.android.liquidgas.api.base.subscribers.SubscriberOnNextListener;
import com.suchengkeji.android.liquidgas.bean.SalesDataBean;
import com.suchengkeji.android.liquidgas.commontadapter.CommonRecyclerAdapter;
import com.suchengkeji.android.liquidgas.commontadapter.MyViewHolder;
import com.suchengkeji.android.liquidgas.ui.base.BaseUse;
import com.suchengkeji.android.liquidgas.ui.fragment.tab.CompanyFragment;
import com.suchengkeji.android.liquidgas.ui.fragment.tab.FranchiserFragment;
import com.suchengkeji.android.liquidgas.ui.fragment.tab.StoreFragment;
import com.suchengkeji.android.liquidgas.utils.LogUtils;
import com.suchengkeji.android.liquidgas.utils.NetworkUtils;
import com.suchengkeji.android.liquidgas.utils.PromptUtils;
import com.suchengkeji.android.liquidgas.utils.SharePreferenceUtils;
import com.suchengkeji.android.liquidgas.utils.TimeUtils;
import com.suchengkeji.android.liquidgas.utils.VerificationUtils;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 运营
 * A simple {@link Fragment} subclass.
 */
public class OperateFragment extends Fragment implements TabHost.OnTabChangeListener {
    private final String TAG = "===->>>" + this.getClass();
    //    @BindView(R.id.text_datas)
//    TextView textDatas;
//    @BindView(R.id.text_order)
//    TextView textOrder;
//    @BindView(R.id.text_day_order)
//    TextView textDayOrder;
//    @BindView(R.id.text_type_one)
//    TextView textTypeOne;
//    @BindView(R.id.text_bottle_one)
//    TextView textBottleOne;
//    @BindView(R.id.line_type_one)
//    LinearLayout lineTypeOne;
//    @BindView(R.id.text_type_two)
//    TextView textTypeTwo;
//    @BindView(R.id.text_bottle_two)
//    TextView textBottleTwo;
//    @BindView(R.id.line_type_two)
//    LinearLayout lineTypeTwo;
//    @BindView(R.id.text_type_three)
//    TextView textTypeThree;
//    @BindView(R.id.text_bottle_three)
//    TextView textBottleThree;
//    @BindView(R.id.line_type_three)
//    LinearLayout lineTypeThree;
//    @BindView(R.id.text_time_data)
//    TextView textTimeData;
//    @BindView(R.id.text_order_number)
//    TextView textOrderNumber;

    @BindView(R.id.lines_hot)
    LinearLayout linesHot;
    @BindView(android.R.id.tabhost)
    TabHost mTabHost;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.new_text_timedata)
    TextView newTextTimedata;
    @BindView(R.id.new_text_order)
    TextView newTextOrder;
    @BindView(R.id.new_text_day_order)
    TextView newTextDayOrder;
    @BindView(R.id.new_hot_title_rec)
    RecyclerView newHotTitleRec;
    @BindView(R.id.new_line_hot)
    LinearLayout newLineHot;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_operate, container, false);
        unbinder = ButterKnife.bind(this, view);

        toolbarTitle.setText(getResources().getString(R.string.string_operate));
        newLineHot.setVisibility(View.VISIBLE);
        linesHot.setVisibility(View.GONE);
        initNetWorke();
        ininTabHost();
        Date date = TimeUtils.subDateTime(TimeUtils.gainCurrentDate(), 24);
        LogUtils.d(TAG, "===本地时间===" + TimeUtils.subDateTime(TimeUtils.gainCurrentDate(), 24)
                + "==\n==" + TimeUtils.formatDateTime(date, "yyyy-MM-dd"));
//        textDatas.setText(String.valueOf(TimeUtils.todayYyyyMmDd()).substring(5, (TimeUtils.todayYyyyMmDd()).length()));
        //textDatas.setText(String.valueOf(TimeUtils.todayYyyyMmDd()).substring(5, (TimeUtils.todayYyyyMmDd()).length()));
        //newTextTimedata.setText(String.valueOf(TimeUtils.todayYyyyMmDd()).substring(5, (TimeUtils.todayYyyyMmDd()).length()));
        newTextTimedata.setText(String.valueOf(TimeUtils.formatDateTime(date, "yyyy-MM-dd")).substring(5, (TimeUtils.todayYyyyMmDd()).length()));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 网络请求
     */
    private void initNetWorke() {

        //根据时间判断是否获取网络数据--------5分钟可获取一次
        String tm = TimeUtils.todayYyyyMmDdHhMmSs();
        String times = (String) SharePreferenceUtils.get(getContext(), "times", "2018-02-03 15:40:00");
        String timeDifference = TimeUtils.getTimeDifference(times, tm);
        //Toast.makeText(getContext(), timeDifference, Toast.LENGTH_SHORT).show();
        int m = (int) Integer.valueOf(timeDifference);
        if (m <= 5) {
            LoadCach();
            return;
        }
        if (!NetworkUtils.isNetworkAvailable(getContext())) {
            PromptUtils.showToast(getContext(), getResources().getString(R.string.string_check_net_ok));
            LoadCach();
            return;
        }

        //获取过数据不到5分钟不获取网络
        AnApiServiceManger.getInstance().getTodaySaleInfoByUserIds(new ProgressSubscriber<SalesDataBean>(new SubscriberOnNextListener<SalesDataBean>() {
            @Override
            public void onNext(SalesDataBean salesDataBean) {
                //实体类转换成JSON保存
                String s = new Gson().toJson(salesDataBean, SalesDataBean.class);
                SharePreferenceUtils.put(getContext(), "json", s);

                String time = TimeUtils.todayYyyyMmDdHhMmSs();
                SharePreferenceUtils.put(getContext(), "times", time);


                SalesDataBean.CompanyBean company = salesDataBean.getCompany();
                if (company == null) return;
                String saleDate = company.getSaleDate();//日期 2011-01-01
                int todayOrderNum = company.getTodayOrderNum();//今日销量
                int yesterdayGrowthOrderNum = company.getYesterdayGrowthOrderNum();//较上日增加量
                if (!VerificationUtils.isEmpty(saleDate))
                    newTextTimedata.setText(saleDate.substring(5, 10));//textDatas.setText(saleDate.substring(5, 10));
                if (todayOrderNum >= 0)
                    newTextOrder.setText(String.valueOf(todayOrderNum));//textOrder.setText(String.valueOf(todayOrderNum));
                if (yesterdayGrowthOrderNum >= 0)
                    newTextDayOrder.setText(String.valueOf(yesterdayGrowthOrderNum)); //textDayOrder.setText(String.valueOf(yesterdayGrowthOrderNum));
                List<SalesDataBean.CompanyBean.SaleDetailInfosBean> saleDetailInfos = company.getSaleDetailInfos();
                if (saleDetailInfos.size() <= 0) return;
                newHotTitleRec.setLayoutManager(new GridLayoutManager(getContext(), saleDetailInfos.size()));
                MyAdapter myAdapter = new MyAdapter(getContext(), saleDetailInfos, R.layout.rec_hot_title_item);
                newHotTitleRec.setAdapter(myAdapter);
//                if (saleDetailInfos.size() == 3) {
//                    textTypeOne.setText(saleDetailInfos.get(0).getMaterialTypeName());//类型
//                    textBottleOne.setText(String.valueOf(saleDetailInfos.get(0).getOrderBottleNum()));//多少瓶
//                    textTypeTwo.setText(saleDetailInfos.get(1).getMaterialTypeName());//类型
//                    textBottleTwo.setText(String.valueOf(saleDetailInfos.get(1).getOrderBottleNum()));//多少瓶
//                    textTypeThree.setText(saleDetailInfos.get(2).getMaterialTypeName());//类型
//                    textBottleThree.setText(String.valueOf(saleDetailInfos.get(2).getOrderBottleNum()));//多少瓶
//                }
            }

        }, getContext(), null));
    }

    /**
     * 读取本地存储的ＪＳＯＮ
     */
    private void LoadCach() {
        /******  此处列表改用本地的  *****/
        String json = (String) SharePreferenceUtils.get(getContext(), "json", "");
        if (TextUtils.isEmpty(json)) return;
        SalesDataBean salesDataBean = new Gson().fromJson(json, SalesDataBean.class);
        SalesDataBean.CompanyBean company = salesDataBean.getCompany();
        if (company == null) return;
        String saleDate = company.getSaleDate();//日期 2011-01-01
        int todayOrderNum = company.getTodayOrderNum();//今日销量
        int yesterdayGrowthOrderNum = company.getYesterdayGrowthOrderNum();//较上日增加量
        if (!VerificationUtils.isEmpty(saleDate))
            newTextTimedata.setText(saleDate.substring(5, 10));//textDatas.setText(saleDate.substring(5, 10));
        if (todayOrderNum >= 0)
            newTextOrder.setText(String.valueOf(todayOrderNum));//textOrder.setText(String.valueOf(todayOrderNum));
        if (yesterdayGrowthOrderNum >= 0)
            newTextDayOrder.setText(String.valueOf(yesterdayGrowthOrderNum)); //textDayOrder.setText(String.valueOf(yesterdayGrowthOrderNum));
        List<SalesDataBean.CompanyBean.SaleDetailInfosBean> saleDetailInfos = company.getSaleDetailInfos();
        if (saleDetailInfos.size() <= 0) return;
        newHotTitleRec.setLayoutManager(new GridLayoutManager(getContext(), saleDetailInfos.size()));
        MyAdapter myAdapter = new MyAdapter(getContext(), saleDetailInfos, R.layout.rec_hot_title_item);
        newHotTitleRec.setAdapter(myAdapter);
    }

    /**
     * TabHost添加设置
     */
    private void ininTabHost() {
        mTabHost.setup();
        View inflateCompany = LayoutInflater.from(getContext()).inflate(R.layout.sales_menu_item_company, null, false);
        View inflateStore = LayoutInflater.from(getContext()).inflate(R.layout.sales_menu_item_store, null, false);
        View inflateFranchiser = LayoutInflater.from(getContext()).inflate(R.layout.sales_menu_item_franchiser, null, false);
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
        //设置当前显示第一个Tab
        mTabHost.setCurrentTab(0);
        //TabHost改变监听
        mTabHost.setOnTabChangedListener(this);
        //公司
        CompanyFragment companyFragment = new CompanyFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.line_one, companyFragment).commit();
        //门店
        StoreFragment storeFragment = new StoreFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.line_two, storeFragment).commit();
        //加盟商
        FranchiserFragment franchiserFragment = new FranchiserFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.line_three, franchiserFragment).commit();
    }


    public static OperateFragment newInstance() {
        Bundle args = new Bundle();
        OperateFragment fragment = new OperateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 实现TabHost改变监听回掉
     *
     * @param tabId
     */
    @Override
    public void onTabChanged(String tabId) {
        if (mTabHost.getCurrentTab() == 1) {
            //linesHot.setVisibility(View.GONE);
            newLineHot.setVisibility(View.GONE);
            BaseUse.isStoreFragmentHidden = true;
            BaseUse.isFranchiserFragmentHidden = false;
            sendMyBroadcast();
        } else if (mTabHost.getCurrentTab() == 2) {
            //linesHot.setVisibility(View.GONE);
            newLineHot.setVisibility(View.GONE);
            BaseUse.isStoreFragmentHidden = false;
            BaseUse.isFranchiserFragmentHidden = true;
        } else {
            //linesHot.setVisibility(View.VISIBLE);
            newLineHot.setVisibility(View.VISIBLE);
            BaseUse.isStoreFragmentHidden = false;
            BaseUse.isFranchiserFragmentHidden = false;
        }
    }

    @OnClick(R.id.new_text_timedata)
    public void onViewClicked() {
        //当天的年月日
        String s = TimeUtils.todayYyyyMmDd();
        //时间选择对话框
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.ThemeDialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d("----选择的日期是-----", year + "年" + (month + 1) + "月" + dayOfMonth + "日");
                PromptUtils.showToast(getContext(), year + "年" + (month + 1) + "月" + dayOfMonth + "日");
            }
        }, Integer.valueOf(s.substring(0, 4)), Integer.valueOf(s.substring(5, 7)) - 1, Integer.valueOf(s.substring(8, 10)));
        datePickerDialog.show();
    }

    private class MyAdapter extends CommonRecyclerAdapter<SalesDataBean.CompanyBean.SaleDetailInfosBean> {
        public MyAdapter(Context context, List<SalesDataBean.CompanyBean.SaleDetailInfosBean> data, int layoutId) {
            super(context, data, layoutId);
        }

        public MyAdapter(Context context, List<SalesDataBean.CompanyBean.SaleDetailInfosBean> data, int layoutId, OnItemListener mOnItemListener) {
            super(context, data, layoutId, mOnItemListener);
        }

        @Override
        public void convert(MyViewHolder holder, SalesDataBean.CompanyBean.SaleDetailInfosBean item) {
            holder.setText(R.id.text_types, item.getMaterialTypeName());
            holder.setText(R.id.text_bottle_numbers, String.valueOf(item.getOrderBottleNum()));
        }
    }


    /**
     * 发送一个点击的广播
     */
    private void sendMyBroadcast() {
        Intent intent = new Intent();
        //设置intent的动作为com.example.broadcast，可以任意定义
        intent.setAction("com.suchengkeji.android");
        //发送无序广播
        getContext().sendBroadcast(intent);
    }

}
