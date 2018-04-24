package com.suchengkeji.android.liquidgas.ui.acticity;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.suchengkeji.android.liquidgas.NewMainActivity;
import com.suchengkeji.android.liquidgas.R;
import com.suchengkeji.android.liquidgas.api.AnApiServiceManger;
import com.suchengkeji.android.liquidgas.api.base.subscribers.ProgressSubscriber;
import com.suchengkeji.android.liquidgas.api.base.subscribers.SubscriberOnNextListener;
import com.suchengkeji.android.liquidgas.bean.LoginDataBean;
import com.suchengkeji.android.liquidgas.ui.base.BaseActivity;
import com.suchengkeji.android.liquidgas.ui.base.BaseInterfaceObserver;
import com.suchengkeji.android.liquidgas.utils.ActivityUtils;
import com.suchengkeji.android.liquidgas.utils.AppOutUtils;
import com.suchengkeji.android.liquidgas.utils.EditTextUtils;
import com.suchengkeji.android.liquidgas.utils.LogUtils;
import com.suchengkeji.android.liquidgas.utils.NetworkUtils;
import com.suchengkeji.android.liquidgas.utils.PromptUtils;
import com.suchengkeji.android.liquidgas.utils.SharePreferenceUtils;
import com.suchengkeji.android.liquidgas.utils.VerificationUtils;
import com.tencent.bugly.beta.Beta;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登陆
 */
public class LoginActivity extends BaseActivity {
    private final String TAG = "===->>>" + this.getClass();
    @BindView(R.id.ed_phonenumber)
    EditText edPhonenumber;//电话
    @BindView(R.id.ed_passtext)
    EditText edPasstext;//密码

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.d(TAG, "onDestroy---closeProgressDialog");
        PromptUtils.closeProgressDialog();
    }

    /**
     * .hideWindowFeature 在加载布局之前设置状态栏和标题栏的状态
     * .setBarColor 可自定义设置状态栏的颜色
     */
    @Override
    public void beforeStart() {
        BaseInterfaceObserver.getInstance().hideWindowFeature(LoginActivity.this, 1);
        BaseInterfaceObserver.getInstance().setBarColor(LoginActivity.this, R.color.appthemeColor);
    }

    @Override
    public int setContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void setOtherContentDatas() {
        //添加活动栈，以及腾讯Bugly检查更新
        addActivityStack();
        //初始输入框以及内容
        initEditText();
    }

    /**
     * 添加活动栈，以及腾讯Bugly检查更新
     */
    private void addActivityStack() {
        ActivityUtils.getScreenActivityUtils().addActivity(LoginActivity.this);
        /***** 腾讯Bugly检查更新 *****/
        Beta.checkUpgrade();
        //电话号码自动加   "-"
        //edPhonenumber.addTextChangedListener(new PhoneTextWatcher(edPhonenumber));
    }

    /**
     * 初始输入框以及内容
     */
    private void initEditText() {
        EditTextUtils.setEditTextInhibitInputSpace(edPhonenumber);
        EditTextUtils.setEditTextInhibitInputSpace(edPasstext);
        String userName = (String) SharePreferenceUtils.get(LoginActivity.this, "userName", "");
        String userPass = (String) SharePreferenceUtils.get(LoginActivity.this, "userPass", "");
        if (!VerificationUtils.isEmpty(userName) && !VerificationUtils.isEmpty(userPass)) {
            edPhonenumber.setText(String.valueOf(userName));
            edPasstext.setText(String.valueOf(userPass));
            edPhonenumber.setSelection(edPhonenumber.getText().toString().length());
        }
    }

    /**
     * 返回拦截监听
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            AppOutUtils.exit(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @OnClick({R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                //登陆点击
                judgePhoneName();
                break;
        }
    }

    /**
     * 手机号码判断
     */
    private void judgePhoneName() {
        PromptUtils.closeProgressDialog();
        String phone = edPhonenumber.getText().toString().trim();
        String pass = edPasstext.getText().toString().trim();
        if (!VerificationUtils.isEmpty(phone)) {
//            String newPhone = "";
//            if (phone.toString().contains("-")) {
//                String[] str = phone.toString().split("-");
//                for (int i = 0; i < str.length; i++) {
//                    newPhone += str[i];
//                }
//            }
            getNetLogin(phone, pass);
            //判断是否为手机号码
//            if (VerificationUtils.isPhone(newPhone)) {
//                //手机号码判断正确，获取网络判断密码
//                getNetLogin(newPhone, pass);
//            } else {
//                PromptUtils.showToast(this, getResources().getString(R.string.phone_isnot));
//            }
        } else {
            PromptUtils.showToast(this, getResources().getString(R.string.phone_notnull));
        }
    }

    /**
     * 登陆
     *
     * @param phone
     * @param pass
     */
    private void getNetLogin(final String phone, final String pass) {
        if (pass == null || TextUtils.isEmpty(pass)) {
            PromptUtils.showToast(this, getResources().getString(R.string.pass_notnull));
            return;
        }
//        Toast.makeText(mContext, "免登陆直接去测试", Toast.LENGTH_SHORT).show();
//        startActivity(MainActivity.class);
//        if (true == true) {
//            SharePreferenceUtils.put(LoginActivity.this, "userID", "1");
//            Logger.d("====取消登录方便与测试。。。。。。。。。====");
//            return;
//        }

        if (!NetworkUtils.isNetworkAvailable(this)) {
            PromptUtils.showToast(this, getResources().getString(R.string.string_check_net_ok));
            return;
        }
        AnApiServiceManger.getInstance().
                logins(new ProgressSubscriber<LoginDataBean>
                        (new SubscriberOnNextListener<LoginDataBean>() {
                            @Override
                            public void onNext(LoginDataBean loginDataBean) {

                                LogUtils.d(TAG, loginDataBean.getUserName() + "===" + loginDataBean.getCompanyName() + "==" + loginDataBean.getUserId());
                                if (loginDataBean.getUserName().equals(phone)) {
                                    SharePreferenceUtils.put(LoginActivity.this, "userName", phone);
                                    SharePreferenceUtils.put(LoginActivity.this, "userPass", pass);
                                    SharePreferenceUtils.put(LoginActivity.this, "userIDs", String.valueOf(loginDataBean.getUserId()));
//                                    startActivity(MainActivity.class);
                                    startActivity(NewMainActivity.class);
                                }
                            }
                        }, LoginActivity.this, getResources().getString(R.string.string_loging)), phone, pass);

//        PromptUtils.showProgressDialog(this, getResources().getString(R.string.string_loging));
//         用次请求，当网络拒绝被访问时会崩溃
//        AnApiServiceManger.getInstance().login("aa", edPasstext.getText().toString().trim())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<LoginResponseBean>() {
//                    @Override
//                    public void call(LoginResponseBean loginResponseBean) {
//                    }
//                });

/**/
//        PromptUtils.showProgressDialog(this, getResources().getString(R.string.string_loging));
//        AnApiServiceManger.getInstance().login(phone, pass)
//                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
//                .observeOn(AndroidSchedulers.mainThread())// 指定 Subscriber 的回调发生在主线程
//                .subscribe(new Observer<LoginResponseBean>() {
//                    @Override
//                    public void onCompleted() {
//                        Logger.d("==========onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Logger.d("==========onError" + e.toString() + "===" + e.getMessage());
//                        PromptUtils.showToast(LoginActivity.this, e.getMessage());
//                        PromptUtils.closeProgressDialog();
//
//                        startActivity(MainActivity.class);
//                    }
//
//                    @Override
//                    public void onNext(LoginResponseBean loginResponseBean) {
//                        Logger.d("==========onNext" + loginResponseBean.toString());
//                        PromptUtils.closeProgressDialog();
//                        int code = loginResponseBean.getCode();
//                        Logger.d("========1==onNext" + String.valueOf(code));
//                        if (code == 200) {
//                            Logger.d("==========onNext" + loginResponseBean.getCode());
//                            startActivity(MainActivity.class);
//                        }
//                        if (!VerificationUtils.isEmpty(loginResponseBean.getMsg())) {
//                            PromptUtils.showToast(LoginActivity.this, loginResponseBean.getMsg());
//                        }
//                    }
//                });
    }

}
