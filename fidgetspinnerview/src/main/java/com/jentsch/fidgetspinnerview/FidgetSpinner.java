package com.jentsch.fidgetspinnerview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class FidgetSpinner extends FrameLayout {

    private static final String TAG = "FidgetSpinner";

    private static final int MAX_ACTION_UP_SPEED = 20;
    private static final int MIN_DURATION = 500;
    private static final int MAX_DURATION = 8000;
    private boolean pivot = false;
    private float xActionDown;
    private float yActionDown;
    private float centerX;
    private float centerY;
    private double actionUpSpeed = 0;
    private float currentSpeed = 0;
    private ImageView fidgetSpinnerImageView;
    private long currentAngleTime = 0;
    private long lastAngleTime = 0;
    private double currentAngle = 0;
    private double offsetAngle = 0;
    private double lastAngle = 0;
    private ObjectAnimator spinAnimation;
    private long spanAnimationFrameTime = 0;

    public FidgetSpinner(@NonNull Context context) {
        super(context);
        init();
    }

    public FidgetSpinner(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FidgetSpinner(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setImageDrawable (int id)
    {
        fidgetSpinnerImageView.setImageDrawable(getContext().getResources().getDrawable(id));
    }

    private void onActionMove(float x, float y) {
        if (!isPivot())
        {
            setPivot(true);
            centerX = fidgetSpinnerImageView.getWidth()  / 2;
            centerY = fidgetSpinnerImageView.getHeight() / 2;
            fidgetSpinnerImageView.setPivotX(centerX);
            fidgetSpinnerImageView.setPivotY(centerY);
        }
        double angle = getRotationAngle(x,y);
        rotate ((float)angle);
    }

    private double getRotationAngle (float x, float y) {
        lastAngle = currentAngle;
        lastAngleTime = currentAngleTime;
        long now = System.currentTimeMillis();
        float dx1 = centerX - x;
        float dy1 = centerY - y;
        float dx2 = centerX - xActionDown;
        float dy2 = centerY - yActionDown;
        double alpha1 = adjustAlpha(Math.toDegrees(Math.atan(dx1 / dy1)), dx1, dy1);
        double alpha2 = adjustAlpha(Math.toDegrees(Math.atan(dx2 / dy2)), dx2, dy2);
        currentAngle = (alpha1 - alpha2 + offsetAngle) % 360;
        currentAngleTime = now;
        return currentAngle;
    }

    private double adjustAlpha(double alpha, float x, float y) {
        if (y > 0 ) return -alpha;       // Top
        if (y < 0 ) return 180 - alpha;  // Bottom
        return alpha ;
    }

    private void onActionUp(float x, float y) {
        offsetAngle = currentAngle;
        actionUpSpeed = Math.min(MAX_ACTION_UP_SPEED,calcActionUpSpeed());
        startFidgetSpinnerAnimation();
    }

    private void startFidgetSpinnerAnimation() {
        spanAnimationFrameTime = System.currentTimeMillis();
        setCurrentSpeed((float)actionUpSpeed);
        float start = (float)actionUpSpeed;
        float end = 0;
        long duration = Math.max(MIN_DURATION, Math.min(MAX_DURATION, Math.abs((long)(actionUpSpeed * 1000))));
        spinAnimation = ObjectAnimator.ofFloat(this, "currentSpeed", start, end);
        spinAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        spinAnimation.setDuration(duration);
        spinAnimation.setStartDelay(0);
        spinAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator p1)
            {
                rotate((float)currentAngle);
            }
        });
        spinAnimation.start();
    }

    private void onActionDown(float x, float y) {
        try {
            if (spinAnimation != null)
            {
                spinAnimation.cancel();
            }
        } catch (Exception e) {}

        currentAngleTime = System.currentTimeMillis();
        lastAngleTime = currentAngleTime;
        lastAngle = currentAngle;
        xActionDown = x;
        yActionDown = y;
    }

    private void rotate (float rotation)
    {
        fidgetSpinnerImageView.setRotation(rotation);
    }

    private void init ()
    {
        fidgetSpinnerImageView = new ImageView(getContext());
        fidgetSpinnerImageView.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT));
        addView(fidgetSpinnerImageView);

        OnTouchListener onTouchListener = new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Only the first pointer is used
                float x = event.getX();
                float y = event.getY();
                int action = event.getAction();
                switch (action)
                {
                    case MotionEvent.ACTION_DOWN:
                        onActionDown (x,y);
                        break;
                    case MotionEvent.ACTION_UP:
                        onActionUp (x,y);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        onActionMove (x,y);
                        break;
                    default:
                        break;
                }
                return true;
            }
        };
        setOnTouchListener(onTouchListener);
    }

    public double calcActionUpSpeed() {
        float angleTimeDiff = currentAngleTime - lastAngleTime;
        double angleDiff = currentAngle - lastAngle;
        return angleDiff / angleTimeDiff;
    }

    public boolean isPivot ()
    {
        return pivot;
    }

    public void setPivot(boolean pivot) {
        this.pivot = pivot;
    }

    public float getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(float currentSpeed) {
        long now = System.currentTimeMillis();
        float timeDiff = now - spanAnimationFrameTime;
        currentAngle = currentAngle + (currentSpeed * timeDiff * 0.5f);
        currentAngleTime = now;
        this.currentSpeed = currentSpeed;
        spanAnimationFrameTime = now;
    }
}
