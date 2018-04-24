package com.suchengkeji.android.liquidgas.ui.fragment;


import android.Manifest;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.suchengkeji.android.liquidgas.R;
import com.suchengkeji.android.liquidgas.app.MyApplication;
import com.suchengkeji.android.liquidgas.ui.acticity.ChangePassWordActivity;
import com.suchengkeji.android.liquidgas.ui.acticity.LoginActivity;
import com.suchengkeji.android.liquidgas.ui.base.BaseFragment;
import com.suchengkeji.android.liquidgas.utils.AppUtils;
import com.suchengkeji.android.liquidgas.utils.LogUtils;
import com.suchengkeji.android.liquidgas.utils.PromptUtils;
import com.suchengkeji.android.liquidgas.view.AppBarStateChangeListener;
import com.suchengkeji.android.liquidgas.utils.SharePreferenceUtils;
import com.suchengkeji.android.liquidgas.utils.VerificationUtils;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * 我的界面
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment {
    private final String TAG = "===->>>" + this.getClass();
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.mine_user_image)
    CircleImageView mineUserImage;
    @BindView(R.id.mine_user_name)
    TextView mineUserName;
    @BindView(R.id.user_textPhone)
    TextView userTextPhone;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    private String userName = "";
    private String phoneNum;
    private final int CODE = 208;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        userName = (String) SharePreferenceUtils.get(getContext(), "userName", "");
        //对应手机版本请求权限
        addPermission();
        //CollapsingToolbarLayout折叠与展开的监听
        inintCollBar();
    }

    /**
     * 对应手机版本请求权限
     */
    private void addPermission() {
        LogUtils.d(TAG, "==-- SDK_INT -==" + Build.VERSION.SDK_INT + "");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED) {
                //----做权限申请处理
                PermissionGen.with(this).addRequestCode(CODE).permissions(
                        Manifest.permission.READ_PHONE_NUMBERS,
                        Manifest.permission.READ_PHONE_STATE).request();
            } else {
                //----用户已经授权,直接处理业务逻辑
                setData();
            }
        } else {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                PermissionGen.needPermission(this, CODE, Manifest.permission.READ_PHONE_STATE);
            } else {
                setData();
            }
        }
    }

    private void setData() {
        LogUtils.d(TAG, "权限获取成功");
        phoneNum = AppUtils.getPhoneNum(getContext());
        LogUtils.e(TAG, "tel:" + phoneNum);
        if (phoneNum == null) {
            if (TextUtils.isEmpty(userName)) userName = "null";
            phoneNum = "14788885830";
            //查看账户------ VerificationUtils.phoneNoHide(phoneNum) 手机号中间四位隐藏
            userTextPhone.setText(VerificationUtils.phoneNoHide(phoneNum));
            mineUserName.setText(userName);
        } else {
            if (TextUtils.isEmpty(userName)) userName = MyApplication.s;
            phoneNum = phoneNum.substring(3, phoneNum.length());
            userTextPhone.setText(VerificationUtils.phoneNoHide(phoneNum));
            mineUserName.setText(userName);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = CODE)
    public void requestPhotoSuccess() {
        //成功之后的处理
        LogUtils.d(TAG, "权限获取成功");
        setData();
    }

    @PermissionFail(requestCode = CODE)
    public void requestPhotoFail() {
        //失败之后的处理，我一般是跳到设置界面
        LogUtils.d(TAG, "权限获取失败");
        goToSetting(getContext());
    }

    /***
     * 去设置界面
     */
    public static void goToSetting(Context context) {
        //go to setting view
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    /**
     * CollapsingToolbarLayout折叠与展开的监听
     */
    private void inintCollBar() {
        appBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                LogUtils.d("STATE" + state.name());
                if (state == State.EXPANDED) {//展开状态
                    //collapsingToolbar.setTitle(" ");
                    toolbarTitle.setText("");
                } else if (state == State.COLLAPSED) {//折叠状态
                    //collapsingToolbar.setTitle(" ");
                    toolbarTitle.setText(userName);
                    collapsingToolbar.setCollapsedTitleGravity(Gravity.CENTER | Gravity.CLIP_HORIZONTAL);//设置收缩后标题的位置
                } else {//中间状态
                    //collapsingToolbar.setTitle(" ");
                    toolbarTitle.setText("");
                    collapsingToolbar.setCollapsedTitleGravity(Gravity.CENTER);//设置收缩后标题的位置
                }
            }
        });
    }

    public static MineFragment newInstance() {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick({R.id.mine_user_image, R.id.mine_change_pass, R.id.mine_read_user, R.id.mine_out_user})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_user_image:
                //点击头像
                break;
            case R.id.mine_change_pass:
                //修改密码
                startActivity(new Intent(getActivity(), ChangePassWordActivity.class));
                break;
            case R.id.mine_read_user:
                //读取账户
                PromptUtils.showToast(getContext(), phoneNum);
                break;
            case R.id.mine_out_user:
                //退出
                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setTitle("提示")
                        .setMessage("确定退出?")
                        .setCancelable(false)
                        .setPositiveButton(" 是 ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharePreferenceUtils.put(getActivity(), "userName", "");
                                SharePreferenceUtils.put(getActivity(), "userPass", "");
                                SharePreferenceUtils.put(getActivity(), "userIDs", "");
                                //退出时清除存储的JSON数据
                                SharePreferenceUtils.put(getContext(), "json", "");
                                //主要是为了，每次重新启动都要重新获取数据
                                SharePreferenceUtils.put(getContext(), "times", "2018-02-03 15:40:00");
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                            }
                        }).setNegativeButton(" 否 ", null).create();
                alertDialog.show();
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.appthemeColor));
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.appthemeColor));
                break;
        }
    }
}
