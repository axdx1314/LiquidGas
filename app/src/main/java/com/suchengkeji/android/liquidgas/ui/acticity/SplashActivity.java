package com.suchengkeji.android.liquidgas.ui.acticity;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.suchengkeji.android.liquidgas.R;
import com.suchengkeji.android.liquidgas.ui.base.BaseActivity;
import com.suchengkeji.android.liquidgas.ui.base.BaseInterfaceObserver;
import com.suchengkeji.android.liquidgas.utils.ActivityUtils;
import com.suchengkeji.android.liquidgas.utils.AppUtils;
import com.suchengkeji.android.liquidgas.utils.LogUtils;

import butterknife.BindView;

/**
 * 启动页面
 */
public class SplashActivity extends BaseActivity {
    private final String TAG = "===->>>" + this.getClass();

    @BindView(R.id.splash_lines)
    ImageView splashLines;
    @BindView(R.id.splash_text)
    TextView splashText;
    @BindView(R.id.splash_text_phone_modle)
    TextView splashTextPhoneModle;
    @BindView(R.id.splash_text_version_number)
    TextView splashTextVersionNumber;

    /**
     * .hideWindowFeature 在加载布局之前设置状态栏和标题栏的状态
     * .setBarColor 可自定义设置状态栏的颜色
     */
    @Override
    public void beforeStart() {
        BaseInterfaceObserver.getInstance().hideWindowFeature(SplashActivity.this, 2);
        BaseInterfaceObserver.getInstance().setBarColor(SplashActivity.this, R.color.appthemeColor);
    }

    @Override
    public int setContentView() {
        return R.layout.activity_splash;
    }

    @Override
    public void setOtherContentDatas() {
        //添加活动栈，以及获取并设置APP名字和版本号
        addActivityStack();
        //设置启动动画
        addAnimations();
    }


    /**
     * 添加活动栈，以及获取并设置APP名字和版本号
     */
    private void addActivityStack() {
        ActivityUtils.getScreenActivityUtils().addActivity(SplashActivity.this);
        String appName = AppUtils.getAppName(this);
        String versionName = AppUtils.getVersionName(this);
        splashText.setText(appName + " v:" + versionName);
        splashTextPhoneModle.setText(String.valueOf(AppUtils.getPhoneModel()));
        splashTextVersionNumber.setText(String.valueOf(AppUtils.getSDKVersionNumber()));
    }

    /**
     * 设置启动动画
     */
    private void addAnimations() {
        //透明
        AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
        aa.setDuration(2000);
        //平移
        TranslateAnimation animation = new TranslateAnimation(
                //X轴初始位置
                Animation.RELATIVE_TO_SELF, 0.0f,
                //X轴移动的结束位置
                Animation.RELATIVE_TO_SELF, 1.0f,
                //y轴开始位置
                Animation.RELATIVE_TO_SELF, 1.0f,
                //y轴移动后的结束位置
                Animation.RELATIVE_TO_SELF, 1.0f);
        animation.setDuration(2000);
        animation.setFillAfter(true);
        //启动
        splashLines.startAnimation(aa);
        splashLines.startAnimation(animation);
        animation.setAnimationListener(animationListener);
    }


    /**
     * 动画的状态监听
     */
    Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            LogUtils.d(TAG, "---onAnimationStart");
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            LogUtils.d(TAG, "---onAnimationEnd");
            startActivity(LoginActivity.class);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            LogUtils.d(TAG, "---onAnimationRepeat");
        }
    };


}
