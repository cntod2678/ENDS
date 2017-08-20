package com.cdj.ends.base.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.cdj.ends.base.command.PageSwipeCommand;


/**
 * Created by Dongjin on 2017. 8. 5..
 */

public class ViewPagerDotView extends View implements PageSwipeCommand {
    private PageSwipeCommand mPageSwipeCommand;

    public static int DEFAULT_RADIUS = 8;
    public static int DEFAULT_STROKE_COLOR = Color.parseColor("#999999");
    public static int DEFAULT_FILL_COLOR = Color.parseColor("#ffffff");

    private int mNumOfCircles = 0;
    private int mScreenWidth;
    private float mRadius = DEFAULT_RADIUS;
    private float mDistance;
    private float[] mXPositions;
    private float mYPositions;

    private float mLeftOrRightSpace;
    private float mAllDotsDomainLength;
    private float mOnceMovedTotalDistance;

    private Paint mPaintStroke;
    private int mStrokeColor = DEFAULT_STROKE_COLOR;
    private Paint mPaintFill;
    private int mFillColor = DEFAULT_FILL_COLOR;

    private float mScrollRatio; // current drag ratio

    private int mCurrentIndex;
    private int mState;

    public ViewPagerDotView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ViewPagerDotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initLengthRelatedVariables();
    }

    public void setNumOfCircles(int numOfCircles){
        setNumOfCircles(numOfCircles, DEFAULT_RADIUS);
    }

    @Override
    public void setNumOfCircles(int numOfCircles, float radius) {
        if (numOfCircles <= 1) {
            this.setVisibility(View.INVISIBLE);
            return;
        }
        this.mRadius = radius;
        initLengthRelatedVariables();
        mNumOfCircles = numOfCircles;
        mAllDotsDomainLength = 2 * mRadius * mNumOfCircles + mDistance * (mNumOfCircles - 1);
        mLeftOrRightSpace = (mScreenWidth - mAllDotsDomainLength) / 2.0f;
        mXPositions = new float[mNumOfCircles];
        for (int i = 0; i < mNumOfCircles; i++) {
            mXPositions[i] = mLeftOrRightSpace + mRadius * (i * 2 + 1) + mDistance * i;
        }
    }

    private void initPaint() {
        mPaintStroke = new Paint();
        mPaintStroke.setColor(mStrokeColor);
        mPaintStroke.setAntiAlias(true);
        mPaintFill = new Paint();
        mPaintFill.setColor(mFillColor);
        mPaintFill.setAntiAlias(true);
    }

    private void initLengthRelatedVariables() {
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mDistance = mRadius * 2;
        mYPositions = mRadius;
        mOnceMovedTotalDistance = mRadius * 2 + mDistance;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawStrokeCircle(canvas);
        drawFillCircle(canvas);
    }

    private void drawStrokeCircle(Canvas canvas) {
        if (mNumOfCircles <= 0) {
            return;
        }
        for (int i = 0; i < mNumOfCircles; i++) {
            canvas.drawCircle(mXPositions[i], mYPositions, mRadius, mPaintStroke);
        }
    }

    private void drawFillCircle(Canvas canvas) {
        if (mNumOfCircles <= 0) {
            return;
        }
        float offset = mLeftOrRightSpace + mRadius + mCurrentIndex * mOnceMovedTotalDistance + mScrollRatio * mOnceMovedTotalDistance;
        if (offset < mLeftOrRightSpace + mRadius) {
            offset = mLeftOrRightSpace + mRadius;
        }
        if (offset > (mLeftOrRightSpace + mRadius) + (mNumOfCircles - 1) * mOnceMovedTotalDistance) {
            offset = mLeftOrRightSpace + mRadius + (mNumOfCircles - 1) * mOnceMovedTotalDistance;
        }
        canvas.drawCircle(offset, mYPositions, mRadius, mPaintFill);
    }

    @Override
    public void onPageScrolled(int canSeePageIndex, float v, int i2) {
        mScrollRatio = v;
        mCurrentIndex = canSeePageIndex;
        invalidate();
    }

    @Override
    public void onPageSelected(int currentIndex) {
        if (mState == ViewPager.SCROLL_STATE_IDLE) {
            mCurrentIndex = currentIndex;
            invalidate();
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {
        mState = i;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        ViewPagerDotView.SavedState savedState = (ViewPagerDotView.SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        mCurrentIndex = savedState.currentPage;
        this.requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        ViewPagerDotView.SavedState savedState = new ViewPagerDotView.SavedState(superState);
        savedState.currentPage = this.mCurrentIndex;
        return savedState;
    }

    static class SavedState extends BaseSavedState {
        int currentPage;
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public ViewPagerDotView.SavedState createFromParcel(Parcel in) {
                return new ViewPagerDotView.SavedState(in);
            }

            public ViewPagerDotView.SavedState[] newArray(int size) {
                return new ViewPagerDotView.SavedState[size];
            }
        };

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.currentPage = in.readInt();
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.currentPage);
        }
    }

    public void setPageTurnCommand(PageSwipeCommand pageSwipeCommand) {
        mPageSwipeCommand = pageSwipeCommand;
    }

    @Override
    public void setVisibility(int mode) {
        switch (mode) {
            case GONE :
                this.setVisibility(GONE);
                break;
            case INVISIBLE :
                this.setVisibility(INVISIBLE);
                break;
            case VISIBLE :
                this.setVisibility(VISIBLE);
        }
    }
}
