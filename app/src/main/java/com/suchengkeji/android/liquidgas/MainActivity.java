package com.suchengkeji.android.liquidgas;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.suchengkeji.android.liquidgas.api.common.AnCommonUrl;
import com.suchengkeji.android.liquidgas.ui.base.BaseActivity;
import com.suchengkeji.android.liquidgas.ui.base.BaseInterfaceObserver;
import com.suchengkeji.android.liquidgas.ui.fragment.AdvisoryFragment;
import com.suchengkeji.android.liquidgas.ui.fragment.MineFragment;
import com.suchengkeji.android.liquidgas.ui.fragment.OperateFragment;
import com.suchengkeji.android.liquidgas.byeburger.ByeBurgerBehavior;
import com.suchengkeji.android.liquidgas.utils.ActivityUtils;
import com.suchengkeji.android.liquidgas.utils.LogUtils;
import com.suchengkeji.android.liquidgas.utils.PromptUtils;
import com.suchengkeji.android.liquidgas.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MainActivity extends BaseActivity {

    @BindView(R.id.bye_burger)
    BottomNavigationView mNavigationView;
    @BindView(R.id.viewpager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.floatButton)
    FloatingActionButton floatButton;
    //退出时的时间
    private long mExitTime;

    public static ByeBurgerBehavior mBehavior;

    @Override
    public int setContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void beforeStart() {
        BaseInterfaceObserver.getInstance().hideWindowFeature(MainActivity.this, 1);
    }

    @Override
    public void setOtherContentDatas() {
        ActivityUtils.getScreenActivityUtils().addActivity(MainActivity.this);
        initData();
        initView();
    }

    private void initView() {
        mBehavior = ByeBurgerBehavior.from(mNavigationView);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });


        toolbar.setVisibility(View.VISIBLE);
        toolbarTitle.setText(getResources().getString(R.string.string_operate));//运营
        mViewPager.setCurrentItem(0);
        //不能滑动界面，把下面隐藏部分提上来
        //mViewPager.setPadding(0, 0, 0, mNavigationView.getHeight() + 5);
        //拿到默认选中的item
        mNavigationView.getMenu().getItem(0).setChecked(true);

        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_home:
                        toolbar.setVisibility(View.VISIBLE);
                        toolbarTitle.setText(getResources().getString(R.string.string_operate));//运营
                        mViewPager.setCurrentItem(0);
                        //不能滑动界面，把下面隐藏部分提上来
                        //mViewPager.setPadding(0, 0, 0, mNavigationView.getHeight() + 5);
                        //拿到默认选中的item
                        mNavigationView.getMenu().getItem(0).setChecked(true);
                        break;
                    case R.id.bottom_search:
                        toolbarTitle.setText(getResources().getString(R.string.string_consultation));//咨询
                        toolbar.setVisibility(View.VISIBLE);
                        mViewPager.setCurrentItem(1);
                        mNavigationView.getMenu().getItem(1).setChecked(true);
                        break;
                    case R.id.bottom_me:
                        toolbar.setVisibility(View.GONE);
                        mViewPager.setCurrentItem(2);
                        mNavigationView.getMenu().getItem(2).setChecked(true);
                        break;
                }
                return false;
            }
        });
    }

    private List<Fragment> fragmentList;

    private void initData() {
        fragmentList = new ArrayList<>();
        fragmentList.add(OperateFragment.newInstance());//运营
        fragmentList.add(AdvisoryFragment.newInstance());//咨询
        fragmentList.add(MineFragment.newInstance());//我的
    }


    public static boolean isStoreFragmentHidden = false;//识别是否在当前StoreFragment页面
    public static boolean isFranchiserFragmentHidden = false;//识别是否在当前FranchiserFragment页面

    public static boolean getIsStoreFragmentHidden() {
        return isStoreFragmentHidden;
    }

    public static boolean getIsFranchiserFragmentHidden() {
        return isFranchiserFragmentHidden;
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

            LogUtils.d("------aa---", isStoreFragmentHidden + "==" + isFranchiserFragmentHidden);
            if (isStoreFragmentHidden) {
                sendMyBroadcast(AnCommonUrl.StoreFragment);
            } else if (isFranchiserFragmentHidden) {
                sendMyBroadcast(AnCommonUrl.FranchiserFragment);
            } else {
                exit();
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

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            PromptUtils.showToast(MainActivity.this, getResources().getString(R.string.again_out));
            mExitTime = System.currentTimeMillis();
        } else {
            ActivityUtils.getScreenActivityUtils().removeAllActivity();
            finish();
            System.exit(0);
        }
    }
}
