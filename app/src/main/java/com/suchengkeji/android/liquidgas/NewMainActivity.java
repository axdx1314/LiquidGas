package com.suchengkeji.android.liquidgas;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.KeyEvent;

import com.suchengkeji.android.liquidgas.api.common.AnCommonUrl;
import com.suchengkeji.android.liquidgas.ui.base.BaseActivity;
import com.suchengkeji.android.liquidgas.ui.base.BaseInterfaceObserver;
import com.suchengkeji.android.liquidgas.ui.base.BaseUse;
import com.suchengkeji.android.liquidgas.ui.fragment.AdvisoryFragment;
import com.suchengkeji.android.liquidgas.ui.fragment.MineFragment;
import com.suchengkeji.android.liquidgas.ui.fragment.OperateFragment;
import com.suchengkeji.android.liquidgas.utils.ActivityUtils;
import com.suchengkeji.android.liquidgas.utils.AppOutUtils;
import com.suchengkeji.android.liquidgas.utils.LogUtils;
import com.suchengkeji.android.liquidgas.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class NewMainActivity extends BaseActivity {
    private final String TAG = "===->>>" + this.getClass();
    @BindView(R.id.main_noscr)
    NoScrollViewPager mainNoscr;
    @BindView(R.id.main_table)
    TabLayout mainTable;
    private List<Fragment> fragmentList;

    @Override
    public int setContentView() {
        return R.layout.activity_main_new;
    }

    @Override
    public void beforeStart() {
        BaseInterfaceObserver.getInstance().hideWindowFeature(NewMainActivity.this, 1);
    }

    @Override
    public void setOtherContentDatas() {
        //添加活动栈，以及分页
        addActivityStack();
        //设置TAB
        addTabAndPager();
    }

    /**
     * 添加活动栈，以及分页
     */
    private void addActivityStack() {
        ActivityUtils.getScreenActivityUtils().addActivity(NewMainActivity.this);
        initPagerData();
    }

    /**
     * 设置分页
     */
    private void addTabAndPager() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        mainNoscr.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
        mainNoscr.setOffscreenPageLimit(0);
        mainTable.setTabGravity(TabLayout.GRAVITY_FILL);
        mainTable.setupWithViewPager(mainNoscr);
        mainTable.getTabAt(0).setIcon(R.drawable.home_slecte_operate_bg).setText("运营");
        mainTable.getTabAt(1).setIcon(R.drawable.home_slecte_advice_bg).setText("咨询");
        mainTable.getTabAt(2).setIcon(R.drawable.home_slecte_mine_bg).setText("我的");
    }

    /**
     * 需要的分页数据
     */
    private void initPagerData() {
        fragmentList = new ArrayList<>();
        fragmentList.add(OperateFragment.newInstance());//运营
        fragmentList.add(AdvisoryFragment.newInstance());//咨询
        fragmentList.add(MineFragment.newInstance());//我的
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
            LogUtils.d(TAG, BaseUse.getIsStoreFragmentHidden() + "=\n=" + BaseUse.getIsFranchiserFragmentHidden());
            if (BaseUse.getIsStoreFragmentHidden()) {
                sendMyBroadcast(AnCommonUrl.StoreFragment);
            } else if (BaseUse.getIsFranchiserFragmentHidden()) {
                sendMyBroadcast(AnCommonUrl.FranchiserFragment);
            } else {
                AppOutUtils.exit(NewMainActivity.this);
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
}
