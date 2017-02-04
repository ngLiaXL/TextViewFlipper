package ldroid.textviewflipper;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;


public class VerticalBannerTextLayout extends LinearLayout {


    private int mFlipInterval = 4000;
    private int mFlipAnimDuration = 400;
    private ArrayList<String> mData;
    private int mPosition;
    private ViewFlipper mFlipper;

    private boolean mRunning;
    private boolean mStarted;

    public VerticalBannerTextLayout(Context context) {
        super(context);
        initViews(null);
    }

    public VerticalBannerTextLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(attrs);
    }

    public VerticalBannerTextLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews(attrs);
    }


    private void initViews(AttributeSet attrs) {

        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.VerticalBannerTextLayout, 0, 0);
        if (arr != null) {
            mFlipAnimDuration = arr.getInt(R.styleable.VerticalBannerTextLayout_flipAnimDuration, mFlipAnimDuration);
            mFlipInterval = arr.getInt(R.styleable.VerticalBannerTextLayout_flipInterval, mFlipInterval);
        }


        mFlipper = (ViewFlipper) LayoutInflater.from(getContext()).inflate(R.layout.layout_vertical_text_flipper, null);


        TranslateAnimation outAnim = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0f, TranslateAnimation.RELATIVE_TO_SELF, 0f,
                TranslateAnimation.RELATIVE_TO_SELF, -1f);
        outAnim.setDuration(mFlipAnimDuration);

        TranslateAnimation inAnim = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0f, TranslateAnimation.RELATIVE_TO_SELF, 1f,
                TranslateAnimation.RELATIVE_TO_SELF, 0f);
        inAnim.setDuration(mFlipAnimDuration);

        mFlipper.setOutAnimation(outAnim);
        mFlipper.setInAnimation(inAnim);

        addView(mFlipper);
    }

    public void setLayoutData(ArrayList data) {
        stopFlipping();
        this.mData = data;
    }


    private void showOnly(boolean flipNow) {
        final LinearLayout currentView = (LinearLayout) mFlipper.getCurrentView();
        TextView tv = (TextView) currentView.getChildAt(0);
        String text = getText();
        tv.setText(text);
    }


    private void showNext() {

        final LinearLayout currentView = (LinearLayout) mFlipper.getCurrentView();
        mFlipper.showNext();
        Animation outAnimation = mFlipper.getOutAnimation();
        outAnimation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                TextView tv = (TextView) currentView.getChildAt(0);
                String text = getText();
                tv.setText(text);
            }
        });
    }


    private String getText() {
        if (mData == null || mData.isEmpty()) {
            return null;
        }
        if (mPosition >= mData.size()) {
            mPosition = 0;
        }
        return mData.get(mPosition++);
    }


    public void startFlipping() {
        mStarted = true;
        updateRunning();
    }


    public void stopFlipping() {
        mStarted = false;
        updateRunning();
        mFlipper.clearAnimation();
    }


    private void updateRunning() {
        updateRunning(true);
    }


    private void updateRunning(final boolean flipNow) {
        boolean running = mStarted;
        if (running != mRunning) {
            if (running) {
                post(mRunnable);
            } else {
                removeCallbacks(mRunnable);
            }
            mRunning = running;
        }
    }


    private AnimRunnable mRunnable = new AnimRunnable();

    private class AnimRunnable implements Runnable {

        @Override
        public void run() {
            if (mRunning) {
                showNext();
                postDelayed(this, mFlipInterval);
            }
        }
    }

    public boolean isRunning() {
        return mRunning;
    }

}
