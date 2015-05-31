package com.flyeek.dev.demo;

import android.app.Activity;

import com.flyeek.dev.demo.util.AnalysisUtil;

/**
 * For analytics.
 * Created by flyeek on 5/30/15.
 */
public abstract class BaseActivity extends Activity{

    @Override
    protected void onResume() {
        super.onResume();

        // UMeng Session analytics.
        AnalysisUtil.onPageStart(getClass().getSimpleName());
        AnalysisUtil.onSessionStart(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // UMeng Session analytics.
        AnalysisUtil.onPageEnd(getClass().getSimpleName());
        AnalysisUtil.onSessionEnd(this);
    }
}
