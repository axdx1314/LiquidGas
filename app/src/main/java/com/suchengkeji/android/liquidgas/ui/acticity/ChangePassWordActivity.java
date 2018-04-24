package com.suchengkeji.android.liquidgas.ui.acticity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.suchengkeji.android.liquidgas.R;
import com.suchengkeji.android.liquidgas.ui.base.BaseActivity;
import com.suchengkeji.android.liquidgas.ui.base.BaseInterfaceObserver;
import com.suchengkeji.android.liquidgas.utils.ActivityUtils;
import com.suchengkeji.android.liquidgas.utils.EditTextUtils;
import com.suchengkeji.android.liquidgas.utils.PromptUtils;
import com.suchengkeji.android.liquidgas.utils.VerificationUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @aboutContent:
 * @author： 安
 * @crateTime: 2018/1/3 09:11
 * @mailBox: an.****.life@gmail.com
 * @company: 东莞速成科技有限公司
 */

public class ChangePassWordActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.old_pass)
    EditText oldPass;
    @BindView(R.id.new_passs)
    EditText newPasss;
    @BindView(R.id.confirm_new_pass)
    EditText confirmNewPass;
    private String oldpass;//旧密码
    private String newpasss;//新密码
    private String confirmnewpasss;//确认新密码

    @Override
    public int setContentView() {
        return R.layout.activity_change_password;
    }

    @Override
    public void setOtherContentDatas() {
        ActivityUtils.getScreenActivityUtils().addActivity(ChangePassWordActivity.this);
        toolbarTitle.setText(getResources().getString(R.string.string_change_password));//修改密码
        EditTextUtils.setEditTextInhibitInputSpace(oldPass);
        EditTextUtils.setEditTextInhibitInputSpace(newPasss);
        EditTextUtils.setEditTextInhibitInputSpace(confirmNewPass);
    }

    @Override
    public void beforeStart() {
        /***  隐藏状态栏 ***/
        BaseInterfaceObserver.getInstance().hideWindowFeature(ChangePassWordActivity.this, 1);
    }

    @OnClick({R.id.toolbar_back, R.id.btn_change_pass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.btn_change_pass:
                judgeOldPass();
                break;
        }
    }

    /**
     * 先判断旧密码
     */
    private void judgeOldPass() {
        oldpass = oldPass.getText().toString().trim();
        newpasss = newPasss.getText().toString().trim();
        confirmnewpasss = confirmNewPass.getText().toString().trim();
        if (!VerificationUtils.isEmpty(oldpass)) {
            judgeNewPass();
        } else {
            PromptUtils.showToast(this, "旧密码为空");
        }
    }

    /**
     * 判断新密码
     */
    private void judgeNewPass() {
        if (!VerificationUtils.isEmpty(newpasss)) {
            if (!newpasss.equals(oldpass)) {
                judgeConfirmNewPass();
            } else {
                PromptUtils.showToast(this, "新密码不能和旧密码相同");
            }
        } else {
            PromptUtils.showToast(this, "新密码为空");
        }
    }

    /**
     * 再次判断新密码
     */
    private void judgeConfirmNewPass() {
        if (!VerificationUtils.isEmpty(confirmnewpasss)) {
            if (newpasss.equals(confirmnewpasss)) {
                //  此处可再次判断旧密码是否正确，然后再去上传修改
                PromptUtils.showToast(this, "暂不支持密码修改");
            } else {
                PromptUtils.showToast(this, "两次密码输入不一致");
            }
        } else {
            PromptUtils.showToast(this, "确认密码为空");
        }
    }


}
