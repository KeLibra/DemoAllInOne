package com.android.peter.animationdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
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
    private int mTitleTextColor;

    private String mTitleText = "----";
    private String mValueText = "0";
    private String mUnitText = "Kcal";

    private Paint mTestPaint = new Paint();
    private Paint mBackgroundPaint = new Paint();
    private Paint mForegroundPaint = new Paint();
    private Paint mEndOutCirclePaint = new Paint();
    private Paint mEndInnerCirclePaint = new Paint();
    private Paint mTitleTextPaint = new Paint();
    private Paint mValueTextPaint = new Paint();
    private Paint mUnitTextPaint = new Paint();

    private int mPercentage = 0;
    private int mViewWidth;
    private int mViewHeight;
    private int mCenterX;
    private int mCenterY;

    private int mPaddingStart = 0;
    private int mPaddingEnd = 0;
    private int mPaddingTop = 0;
    private int mPaddingBottom = 0;

    private RectF mFrameRectF = new RectF();
    private Rect mTextRect = new Rect();

    public CircleProgress(Context context) {
        this(context,null);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypedArray(context,attrs);
        initPaint();
    }

    private void initTypedArray(Context context,AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.CircleProgress);
        try {
            mStrokeWidth = ta.getDimension(R.styleable.CircleProgress_stroke_width,getResources().getDimension(R.dimen.circle_progress_default_stroke_width));
            mBackgroundColor = ta.getColor(R.styleable.CircleProgress_background_color,getResources().getColor(R.color.white,null));
            mForegroundColor = ta.getColor(R.styleable.CircleProgress_foreground_color,getResources().getColor(R.color.colorPrimary,null));
            mTitleTextColor = ta.getColor(R.styleable.CircleProgress_text_color,getResources().getColor(R.color.colorPrimary,null));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if(ta != null) {
                ta.recycle();
            }
        }
    }

    private void initPaint() {
        // test paint
        mTestPaint.setColor(mForegroundColor);
        mTestPaint.setAntiAlias(true);
        mTestPaint.setStyle(Paint.Style.STROKE);
        mTestPaint.setStrokeWidth(1);

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

        // title text paint
        mTitleTextPaint.setColor(mTitleTextColor);
        mTitleTextPaint.setTextSize(getResources().getDimension(R.dimen.circle_progress_default_title_size));
        mTitleTextPaint.setAntiAlias(true);
        mTitleTextPaint.setTextAlign(Paint.Align.CENTER);

        // value text paint
        mValueTextPaint.setColor(mForegroundColor);
        mValueTextPaint.setTextSize(getResources().getDimension(R.dimen.circle_progress_default_value_size));
        mValueTextPaint.setAntiAlias(true);
        mValueTextPaint.setTextAlign(Paint.Align.CENTER);

        // unit text paint
        mUnitTextPaint.setColor(mForegroundColor);
        mUnitTextPaint.setTextSize(getResources().getDimension(R.dimen.circle_progress_default_unit_size));
        mUnitTextPaint.setAntiAlias(true);
        mUnitTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG,"onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if(widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension((int)getResources().getDimension(R.dimen.circle_progress_default_width),
                    (int)getResources().getDimension(R.dimen.circle_progress_default_height));
        } else if (widthSpecMode ==  MeasureSpec.AT_MOST) {
            setMeasuredDimension((int)getResources().getDimension(R.dimen.circle_progress_default_width), heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize,(int)getResources().getDimension(R.dimen.circle_progress_default_height));
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.i(TAG,"onLayout");
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
        Log.i(TAG,"onDraw");
        super.onDraw(canvas);
        // background
        canvas.drawCircle(mCenterX,mCenterY,mCircleRadius - mStrokeWidth/2, mBackgroundPaint);

        // foreground
        canvas.drawArc(mFrameRectF.left + mStrokeWidth/2,mFrameRectF.top + mStrokeWidth/2,mFrameRectF.right - mStrokeWidth/2,
                mFrameRectF.bottom - mStrokeWidth/2,270,(float) (3.6* mPercentage),false,mForegroundPaint);

        // end circle
        if(mPercentage != 0 && mPercentage != 100) {
            // end out circle
            canvas.drawCircle((float) (mCenterX + (mCircleRadius-mStrokeWidth/2)*Math.cos((3.6* mPercentage -90)*Math.PI/180)),
                    (float)(mCenterY + (mCircleRadius-mStrokeWidth/2)*Math.sin((3.6* mPercentage -90)*Math.PI/180)), mStrokeWidth*3/4, mEndOutCirclePaint);

            // end inner circle
            canvas.drawCircle((float) (mCenterX + (mCircleRadius-mStrokeWidth/2)*Math.cos((3.6* mPercentage -90)*Math.PI/180)),
                    (float)(mCenterY + (mCircleRadius-mStrokeWidth/2)*Math.sin((3.6* mPercentage -90)*Math.PI/180)), mStrokeWidth/4, mEndInnerCirclePaint);
        }

        // text
        mTitleTextPaint.getTextBounds(mTitleText,0,mTitleText.length(), mTextRect);
        canvas.drawText(mTitleText,mCenterX,mCenterY/2 + mTextRect.height()/2,mTitleTextPaint);

        mValueTextPaint.getTextBounds(mValueText,0,mValueText.length(),mTextRect);
        canvas.drawText(mValueText,mCenterX,mCenterY + mTextRect.height()/2,mValueTextPaint);

        mUnitTextPaint.getTextBounds(mUnitText,0,mUnitText.length(),mTextRect);
        canvas.drawText(mUnitText,mCenterX,mCenterY*4/3,mUnitTextPaint);

        // coordinate
        /*canvas.drawLine(0,mCenterY,2*mCenterX,mCenterY,mTestPaint);
        canvas.drawLine(mCenterX,0,mCenterX,2*mCenterY,mTestPaint);*/
    }

    public int getPercentage() {
        return mPercentage;
    }

    public void setPercentage(int progress) {
        if(progress < 0 || progress > 100) {
            throw new IllegalArgumentException("The value should be in 1 ~ 100");
        }

        mPercentage = progress;
        invalidate();
    }

    public String getTitleText() {
        return mTitleText;
    }

    public void setTitleText(String titleText) {
        mTitleText = titleText;
        invalidate();
    }

    public String getValueText() {
        return mValueText;
    }

    public void setValueText(String valueText) {
        mValueText = valueText;
        invalidate();
    }
}
