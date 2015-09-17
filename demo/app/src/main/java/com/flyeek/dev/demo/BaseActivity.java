package com.flyeek.dev.demo;

import android.app.Activity;

import com.flyeek.dev.demo.util.AnalyticsUtil;

/**
 * Base activity, only can be inherited by activity with no fragment.
 * <p>
 * Session and Page Hopping will be automaticly analysed.
 * <p>
 * Created by flyeek on 5/30/15.
 */
public abstract class BaseActivity extends Activity{

    @Override
    protected void onResume() {
        super.onResume();

        // Disable automatic Page Hopping analytics when analyse Session.
        AnalyticsUtil.onPageStart(getClass().getSimpleName());
        AnalyticsUtil.onSessionStart(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // UMeng Session analytics.
        AnalyticsUtil.onPageEnd(getClass().getSimpleName());
        AnalyticsUtil.onSessionEnd(this);
    }
}
