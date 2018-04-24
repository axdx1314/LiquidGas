package com.suchengkeji.android.liquidgas.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @aboutContent: 竖直不可滚动的ViewPager
 * @author： 安
 * @crateTime: 2017/12/29 09:25
 * @mailBox: an.****.life@gmail.com
 * @company: 东莞速成科技有限公司
 */

public class NoScrollViewPager extends ViewPager {

    private boolean isPagingEnabled = false;
    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event);
    }

    @Override public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }
}
