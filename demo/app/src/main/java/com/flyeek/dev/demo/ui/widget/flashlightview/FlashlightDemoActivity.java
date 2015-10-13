package com.flyeek.dev.demo.ui.widget.flashlightview;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class FlashlightDemoActivity extends Activity {

    FlashlightView mFlashlightView;
    Button mTurnBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        /*mFlashlightView = (FlashlightView) findViewById(R.id.view_flashlight);
        mTurnBtn = (Button) findViewById(R.id.btn_turn_flashlight);*/

        mTurnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFlashlightView.isAvailable() && !mFlashlightView.isFlashlightOn()) {
                    mFlashlightView.turnFlashlight(true);
                    mTurnBtn.setText("turn off");
                } else if (mFlashlightView.isFlashlightOn()){
                    mFlashlightView.turnFlashlight(false);
                    mTurnBtn.setText("turn on");
                }
            }
        });
    }

    private View getContentView() {
        FrameLayout contentView = new FrameLayout(this);

        mFlashlightView = new FlashlightView(this);
        FrameLayout.LayoutParams flashlightLp = new FrameLayout.LayoutParams(1, 1, Gravity.CENTER);
        contentView.addView(mFlashlightView, flashlightLp);

        mTurnBtn = new Button(this);
        FrameLayout.LayoutParams turnBtnLp = new FrameLayout.LayoutParams(FrameLayout
                .LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        mTurnBtn.setText("turn on");
        contentView.addView(mTurnBtn, turnBtnLp);

        return contentView;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mFlashlightView != null) {
            mFlashlightView.releaseFlashlight();
        }
    }
}
