package com.android.peter.scrollerdemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Created by peter on 2018/10/30.
 */

public class ScrollerView extends TextView {
    private static final String TAG = ScrollerView.class.getSimpleName();

    private Scroller mScroller;
    private int mLastX = 0;
    private int mLastY = 0;

    public ScrollerView(Context context) {
        this(context,null);
    }

    public ScrollerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScrollerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public ScrollerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mScroller = new Scroller(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        Log.d(TAG,"onTouchEvent x = " + x + " , y = " + y);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = mLastX - x;
                int deltaY = mLastY - y;
                //边界检测判断，防止滑块越界
               /* if (deltaX + getScrollX() > 0) {
                    scrollTo(0, 0);
                    return true;
                } else if (deltaX + getScrollX() + getMeasuredWidth() / 2 < 0) {
                    scrollTo(-getMeasuredWidth() / 2, 0);
                    return true;
                }*/
                scrollBy(deltaX, deltaY);
                break;
            case MotionEvent.ACTION_UP:
                //处理弹性滑动
                smoothScroll(x,y);
                break;
        }
        mLastX = x;
        mLastY = y;
        return super.onTouchEvent(event);
    }

    private void smoothScroll(int destX, int destY) {
        Log.d(TAG,"smoothScroll destX = " + destX + " , destY = " + destY);
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int deltaX = destX - scrollX;
        int deltaY = destY - scrollY;
        mScroller.startScroll(scrollX, scrollY, deltaX, deltaY, 1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        Log.d(TAG,"computeScroll");
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
