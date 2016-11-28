package com.example.myproject;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

/**
 * Created by Administrator on 2016/11/27.
 */

public class HomeLayout extends LinearLayout{
    MaterialCalendarView calendarView;

    public HomeLayout(Context context) {
        super(context);
    }

    public HomeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            init();
        }
    }

    private void init() {
        calendarView = (MaterialCalendarView)findViewById(R.id.calendarView);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        LogUtils.e(">>>onScrollChanged>>>>>>>l="+l+" t"+t+" oldl"+oldl+" oldt"+oldt);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        boolean canTouched = false;
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtils.e("onTouchEvent_ACTION_DOWN>>>>>>"+" x="+x+" y"+y);
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.e("onTouchEvent_ACTION_MOVE>>>>>>"+" x="+x+" y"+y);
                break;
            case MotionEvent.ACTION_UP:
                LogUtils.e("onTouchEvent_ACTION_UP>>>>>>"+" x="+x+" y"+y);
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * onInterceptTouchEvent最后坐标
     */
    private int mLastX;
    private int mLastY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
//                LogUtils.e("onInterceptTouchEvent_ACTION_DOWN>>>>>>"+" x="+x+" y"+y);
                break;
            case MotionEvent.ACTION_MOVE:

                LogUtils.e("onInterceptTouchEvent_ACTION_MOVE>>>>>>"+" x="+x+" y"+y);
                LogUtils.e("calendarView.getScrolly()>>>>>>"+calendarView.getScrollY());
                if(this.getTop()< calendarView.getBottom() && calendarView.getScrollY()<calendarView.getHeight()-500) {
                    calendarView.scrollTo(calendarView.getScrollX(),mLastY - y );
                }
                break;
            case MotionEvent.ACTION_UP:
//                LogUtils.e("onInterceptTouchEvent_ACTION_UP>>>>>>"+" x="+x+" y"+y);
                break;
        }
        LogUtils.e(">>>onInterceptTouchEvent>>>>>>>calendarView.getTop()="+calendarView.getTop()+" calendarView.getBottom()="+calendarView.getBottom());

//        calendarView.state().edit()
//                .setCalendarDisplayMode(CalendarMode.WEEKS)
//                .commit();
//        LogUtils.e(">>>onInterceptTouchEvent>>>>>>>calendarView.getTop()="+calendarView.getTop()+" calendarView.getBottom()="+calendarView.getBottom());

        return super.onInterceptTouchEvent(event);
    }
}
