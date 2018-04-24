package com.suchengkeji.android.liquidgas.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @aboutContent: 启动项
 * @author： 安
 * @crateTime: 2017/12/28 11:11
 * @mailBox: an.****.life@gmail.com
 * @company: 东莞速成科技有限公司
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    private Unbinder mBind;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeStart();
        if (setContentView() == 0 || String.valueOf(setContentView()).length() == 0) {
            return;
        }
        setContentView(setContentView());
        mBind = ButterKnife.bind(this);
        mContext = this;
        setOtherContentDatas();
    }

    //开始之前是否有其他操作
    public void beforeStart() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBind != null) {
            mBind.unbind();
        }
    }


    /**
     * 布局设置
     */
    public abstract int setContentView();

    /**
     * 是否有按钮布局
     *
     * @return
     */
    public int setMenuContentView() {
        return 0;
    }

    /**
     * 初始化后操作其他的
     */
    public void setOtherContentDatas() {
    }

    /**
     * 有按钮时，按钮的点击事件
     *
     * @param item
     */
    public void setMenuContentSelected(MenuItem item) {

    }


    /**
     * 共用的Activity跳转，不带参数
     *
     * @param cls
     */
    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    /**
     * 共用的Activity跳转，带参数
     *
     * @param cls
     * @param keys
     * @param values
     */
    public void startActivity(Class<?> cls, String[] keys, String[] values) {
        Intent intent = new Intent(this, cls);
        int size = keys.length;
        for (int i = 0; i < size; i++) {
            intent.putExtra(keys[i], values[i]);
        }
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (menu != null) {
            if (setMenuContentView() != 0) {
                getMenuInflater().inflate(setMenuContentView(), menu);
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item != null) {
            setMenuContentSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
