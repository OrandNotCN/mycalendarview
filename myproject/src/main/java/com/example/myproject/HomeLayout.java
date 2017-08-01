package com.example.myproject;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

/**
 * Created by Administrator on 2016/11/27.
 */

public class HomeLayout extends RelativeLayout {
    MaterialCalendarView monthCalendar, weeksCalendar;
    LinearLayout llyMonth;

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
        monthCalendar = (MaterialCalendarView) findViewById(R.id.monthCalendar);
        llyMonth = (LinearLayout) findViewById(R.id.lly_month);
        weeksCalendar = (MaterialCalendarView) findViewById(R.id.weeksCalendar);
        weeksCalendar.state().edit()
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        LogUtils.e(">>>onScrollChanged>>>>>>>l=" + l + " t" + t + " oldl" + oldl + " oldt" + oldt);
    }

    private int downy;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        boolean canTouched = false;
        int x = (int) event.getX();
        int y = (int) event.getY();
//        LogUtils.e(">>>>>>ACTION_UP>>>>>up:" + llyMonth.getScrollY() + " monthCalendar.getHeight():" + monthCalendar.getHeight());
//        LogUtils.e(">>>>>>ACTION_UP>>>>>up:" + " weeksCalendar.getHeight():" + weeksCalendar.getHeight());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - mInterceptLastY;
                int deltaX = x - mInterceptLastX;
                if (Math.abs(deltaX) < Math.abs(deltaY)) {
                    int dScrollY = mInterceptLastY - y;
                    mInterceptLastY = y;
                    int scrollToY = llyMonth.getScrollY() + dScrollY;
//                    LogUtils.e(">>>>>>dScrollY:"+dScrollY+"    scrollToY:"+ scrollToY+"   llyMonth.getTop():"+llyMonth.getTop()+"   llyMonth.getHeight():"+llyMonth.getHeight()+" weeksCalendar.getBottom():"+weeksCalendar.getBottom());
                    if (dScrollY < 0 && scrollToY > 0 && weeksCalendar.getVisibility() == View.VISIBLE) {// && this.getTop() < llyMonth.getBottom() && llyMonth.getScrollY() < llyMonth.getHeight() - 500) {
                        LogUtils.e(">>>>>>scroll>>>>>down");
//                        monthCalendar.setVisibility(View.VISIBLE);
                        llyMonth.scrollTo(llyMonth.getScrollX(), scrollToY);
                    }
                    if (dScrollY > 0 && scrollToY <= llyMonth.getHeight()) {// && monthCalendar.getScrollY() > monthCalendar.getTop()) {
                        weeksCalendar.setVisibility(View.VISIBLE);
                        llyMonth.scrollTo(llyMonth.getScrollX(), scrollToY);
//                        monthCalendar.scrollTo(monthCalendar.getScrollX(),scrollToY>monthCalendar.getTop()?monthCalendar.getTop():scrollToY);
                        LogUtils.e(">>>>>>scroll>>>>>up" + monthCalendar.getScrollY());
//                        mScroller.startScroll(monthCalendar.getScrollX(),monthCalendar.getScrollY(),0,dScrollY);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                LogUtils.e(">>>>>>scroll>>>>>down"+downy+" y"+y);
                int hasScroll = llyMonth.getScrollY() + weeksCalendar.getHeight() - monthCalendar.getHeight();
                if (hasScroll < 0) {//收缩
                    if (y - downy <0)
                        llyMonth.scrollTo(llyMonth.getScrollX(), monthCalendar.getHeight() - weeksCalendar.getHeight());
//                    llyMonth.setVisibility(View.GONE);

                    else {
                        llyMonth.scrollTo(llyMonth.getScrollX(), 0);
                        weeksCalendar.setVisibility(View.GONE);
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * onInterceptTouchEvent最后坐标
     */
    private int mInterceptLastX;
    private int mInterceptLastY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        boolean intercepted = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInterceptLastX = x;
                mInterceptLastY = y;
                downy = y;
//                LogUtils.e("onInterceptTouchEvent_ACTION_DOWN>>>>>>" + " mInterceptLastX=" + x + " mInterceptLastY" + y);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - mInterceptLastY;
                int deltaX = x - mInterceptLastX;
                if (Math.abs(deltaY) < 10) {
                    //横向移动的时候不拦截touch事件，让viewpager处理
                    intercepted = false;
                } else if (Math.abs(deltaX) < Math.abs(deltaY)) {
                    intercepted = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
//                LogUtils.e("onInterceptTouchEvent_ACTION_UP>>>>>>"+" x="+x+" y"+y);
                break;
        }
//        LogUtils.e(">>>onInterceptTouchEvent>>>>>>>calendarView.getTop()="+calendarView.getTop()+" calendarView.getBottom()="+calendarView.getBottom());

//        calendarView.state().edit()
//                .setCalendarDisplayMode(CalendarMode.WEEKS)
//                .commit();
//        LogUtils.e(">>>onInterceptTouchEvent>>>>>>>calendarView.getTop()="+calendarView.getTop()+" calendarView.getBottom()="+calendarView.getBottom());

        return intercepted;
    }

    public void smoothCollapse() {
        monthCalendar.scrollTo(monthCalendar.getScrollX(), monthCalendar.getHeight() - weeksCalendar.getHeight());
        monthCalendar.setVisibility(View.GONE);
    }
}
