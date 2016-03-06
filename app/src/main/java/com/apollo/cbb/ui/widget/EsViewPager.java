package com.apollo.cbb.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.apollo.cbb.R;

/**
 * Created by Panda on 2016/3/6.
 */
public class EsViewPager extends ViewPager {
    private boolean scrollable = true;

    public EsViewPager(Context context) {
        super(context);
        scrollable = true;
    }

    public EsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EsViewPager, 0, 0);
        scrollable = a.getBoolean(R.styleable.EsViewPager_scrollable, true);
        a.recycle();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!scrollable) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (scrollable) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }

    public boolean isScrollable() {
        return scrollable;
    }

    /**
     * set ViewPager can scroll or not
     *
     * @param scrollable
     */
    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }


}
