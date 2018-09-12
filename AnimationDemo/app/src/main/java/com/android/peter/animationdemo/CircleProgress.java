package com.android.peter.animationdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by peter on 2018/9/12.
 */

public class CircleProgress extends View {
    private float mCircleRadius;
    private float mStrokeWidth;
    private int mBackgroundColor;
    private int mForegroundColor;
    private int mTextColor;

    private Paint mBackgroundPaint = new Paint();
    private Paint mForegroundPaint = new Paint();
    private Paint mEndOutCirclePaint = new Paint();
    private Paint mEndInnerCirclePaint = new Paint();

    private int mCurrentProgress = 100;
    private int mLayoutWidth;
    private int mLayoutHeight;
    private int mCenterX;
    private int mCenterY;


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
            mCircleRadius = ta.getDimension(R.styleable.CircleProgress_circle_radius,300);
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
        mEndOutCirclePaint.setStrokeWidth(mStrokeWidth/2);
        mEndOutCirclePaint.setStyle(Paint.Style.STROKE);
        mEndOutCirclePaint.setAntiAlias(true);

        // end inner circle paint
        mEndInnerCirclePaint.setColor(mBackgroundColor);
        mEndInnerCirclePaint.setStrokeWidth(1);
        mEndInnerCirclePaint.setStyle(Paint.Style.FILL);
        mEndInnerCirclePaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mLayoutWidth = MeasureSpec.getSize(widthMeasureSpec);
        mLayoutHeight = MeasureSpec.getSize(heightMeasureSpec);

        if (mLayoutWidth > mLayoutHeight) {
            mLayoutWidth = mLayoutHeight;
        }

        if (mLayoutHeight > mLayoutWidth) {
            mLayoutHeight = mLayoutWidth;
        }

        mCenterX = mLayoutWidth/2;
        mCenterY = mLayoutHeight/2;

        setMeasuredDimension(mLayoutWidth, mLayoutHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // background
        canvas.drawCircle(mCircleRadius,mCircleRadius,mCircleRadius - mStrokeWidth/2, mBackgroundPaint);

        // foreground
        canvas.drawArc(mStrokeWidth/2,mStrokeWidth/2,2*mCircleRadius - mStrokeWidth/2,
                2*mCircleRadius - mStrokeWidth/2,270,(float) (3.6*mCurrentProgress),false,mForegroundPaint);

        if(mCurrentProgress != 0 && mCurrentProgress != 100) {
            // end out circle
            canvas.drawCircle((float) (mCircleRadius + (mCircleRadius-mStrokeWidth/2)*Math.cos((3.6*mCurrentProgress-90)*Math.PI/180)),
                    (float)(mCircleRadius + (mCircleRadius-mStrokeWidth/2)*Math.sin((3.6*mCurrentProgress-90)*Math.PI/180)), mStrokeWidth/2, mEndOutCirclePaint);

            // end inner circle
            canvas.drawCircle((float) (mCircleRadius + (mCircleRadius-mStrokeWidth/2)*Math.cos((3.6*mCurrentProgress-90)*Math.PI/180)),
                    (float)(mCircleRadius + (mCircleRadius-mStrokeWidth/2)*Math.sin((3.6*mCurrentProgress-90)*Math.PI/180)), mStrokeWidth/2, mEndInnerCirclePaint);
        }

        /*canvas.drawLine(mCircleRadius,-mCircleRadius,mCircleRadius,3*mCircleRadius,mForegroundPaint);
        canvas.drawLine(-mCircleRadius,mCircleRadius,3*mCircleRadius,mCircleRadius,mForegroundPaint);*/
    }

}
