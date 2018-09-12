package com.android.peter.animationdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by peter on 2018/9/12.
 */

public class CircleProgress extends View {
    private final static String TAG = "CircleProgress";

    private float mCircleRadius;
    private float mStrokeWidth;
    private int mBackgroundColor;
    private int mForegroundColor;
    private int mTextColor;

    private Paint mBackgroundPaint = new Paint();
    private Paint mForegroundPaint = new Paint();
    private Paint mEndOutCirclePaint = new Paint();
    private Paint mEndInnerCirclePaint = new Paint();

    private int mCurrentProgress = 0;
    private int mViewWidth;
    private int mViewHeight;
    private int mCenterX;
    private int mCenterY;

    private int mPaddingStart = 0;
    private int mPaddingEnd = 0;
    private int mPaddingTop = 0;
    private int mPaddingBottom = 0;

    private RectF mFrameRectF = new RectF();


    public CircleProgress(Context context) {
        this(context,null);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypedArray(context,attrs);
        initPath();
        initPaint();
    }

    private void initTypedArray(Context context,AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.CircleProgress);
        try {
            mStrokeWidth = ta.getDimension(R.styleable.CircleProgress_stroke_width,16);
            mBackgroundColor = ta.getColor(R.styleable.CircleProgress_background_color,getResources().getColor(R.color.white,null));
            mForegroundColor = ta.getColor(R.styleable.CircleProgress_foreground_color,getResources().getColor(R.color.colorPrimary,null));
            mTextColor = ta.getColor(R.styleable.CircleProgress_text_color,getResources().getColor(R.color.colorPrimary,null));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if(ta != null) {
                ta.recycle();
            }
        }
    }

    private void initPath() {
    }

    private void initPaint() {
        // foreground paint
        mForegroundPaint.setColor(mForegroundColor);
        mForegroundPaint.setStrokeWidth(mStrokeWidth);
        mForegroundPaint.setStyle(Paint.Style.STROKE);
        mForegroundPaint.setStrokeCap(Paint.Cap.ROUND);
        mForegroundPaint.setAntiAlias(true);

        // background paint
        mBackgroundPaint.setColor(mBackgroundColor);
        mBackgroundPaint.setStrokeWidth(mStrokeWidth);
        mBackgroundPaint.setStyle(Paint.Style.STROKE);
        mBackgroundPaint.setAntiAlias(true);

        // end out circle paint
        mEndOutCirclePaint.setColor(mForegroundColor);
        mEndOutCirclePaint.setStyle(Paint.Style.FILL);
        mEndOutCirclePaint.setAntiAlias(true);

        // end inner circle paint
        mEndInnerCirclePaint.setColor(mBackgroundColor);
        mEndInnerCirclePaint.setStyle(Paint.Style.FILL);
        mEndInnerCirclePaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if(widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(800,800);
        } else if (widthSpecMode ==  MeasureSpec.AT_MOST) {
            setMeasuredDimension(800, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize,800);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mPaddingStart = getPaddingStart() + (int)(mStrokeWidth/4);
        mPaddingEnd = getPaddingEnd() + (int)(mStrokeWidth/4);
        mPaddingTop = getPaddingTop() + (int)(mStrokeWidth/4);
        mPaddingBottom = getPaddingBottom() + (int)(mStrokeWidth/4);
        mViewWidth = getWidth() - mPaddingStart - mPaddingEnd;
        mViewHeight = getHeight() - mPaddingTop - mPaddingBottom;
        mCenterX = mPaddingStart + mViewWidth/2;
        mCenterY = mPaddingTop + mViewHeight/2;
        mCircleRadius = mViewWidth < mViewHeight ? mViewWidth/2 : mViewHeight/2;
        mFrameRectF.set(mCenterX - mCircleRadius,
                mCenterY - mCircleRadius,
                mCenterX + mCircleRadius,
                mCenterY + mCircleRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        // background
        canvas.drawCircle(mCenterX,mCenterY,mCircleRadius - mStrokeWidth/2, mBackgroundPaint);

        // foreground
        canvas.drawArc(mFrameRectF.left + mStrokeWidth/2,mFrameRectF.top + mStrokeWidth/2,mFrameRectF.right - mStrokeWidth/2,
                mFrameRectF.bottom - mStrokeWidth/2,270,(float) (3.6*mCurrentProgress),false,mForegroundPaint);

        if(mCurrentProgress != 0 && mCurrentProgress != 100) {
            // end out circle
            canvas.drawCircle((float) (mCenterX + (mCircleRadius-mStrokeWidth/2)*Math.cos((3.6*mCurrentProgress-90)*Math.PI/180)),
                    (float)(mCenterY + (mCircleRadius-mStrokeWidth/2)*Math.sin((3.6*mCurrentProgress-90)*Math.PI/180)), mStrokeWidth*3/4, mEndOutCirclePaint);

            // end inner circle
            canvas.drawCircle((float) (mCenterX + (mCircleRadius-mStrokeWidth/2)*Math.cos((3.6*mCurrentProgress-90)*Math.PI/180)),
                    (float)(mCenterY + (mCircleRadius-mStrokeWidth/2)*Math.sin((3.6*mCurrentProgress-90)*Math.PI/180)), mStrokeWidth/4, mEndInnerCirclePaint);
        }
    }

    public int getCurrentProgress() {
        return mCurrentProgress;
    }

    public void setCurrentProgress(int progress) {
        mCurrentProgress = progress;
    }
}
