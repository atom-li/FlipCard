package com.atom.flipcard;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private FrameLayout fl_container;
    private FrameLayout fl_card_front;
    private FrameLayout fl_card_back;

    private AnimatorSet mLeftInSet;//左入
    private AnimatorSet mRightOutSet;//右出

    private boolean mIsBack;//是否是背面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fl_container = (FrameLayout) findViewById(R.id.fl_container);
        fl_card_back = (FrameLayout) findViewById(R.id.fl_card_back);
        fl_card_front = (FrameLayout) findViewById(R.id.fl_card_front);

        setAnimators(); // 设置动画
        setCameraDistance(); // 设置镜头距离

        // 翻转卡片
        fl_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 正面朝上
                if (!mIsBack) {
                    mRightOutSet.setTarget(fl_card_front);
                    mLeftInSet.setTarget(fl_card_back);
                    mRightOutSet.start();
                    mLeftInSet.start();
                    mIsBack = true;
                } else {
                    // 背面朝上
                    mRightOutSet.setTarget(fl_card_back);
                    mLeftInSet.setTarget(fl_card_front);
                    mRightOutSet.start();
                    mLeftInSet.start();
                    mIsBack = false;
                }
            }
        });
    }


    /**
     * 属性动画
     */
    private void setAnimators() {
        mRightOutSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.right_out_anim);
        mLeftInSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.left_in_anim);

        // 设置点击事件
        mRightOutSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                fl_container.setClickable(false);
            }
        });
        mLeftInSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                fl_container.setClickable(true);
            }
        });
    }

    /**
     * 使用Camera 设置镜头距离
     */
    private void setCameraDistance() {
        int distance = 16000;
        float scale = getResources().getDisplayMetrics().density * distance;
        fl_card_front.setCameraDistance(scale);
        fl_card_back.setCameraDistance(scale);
    }
}
