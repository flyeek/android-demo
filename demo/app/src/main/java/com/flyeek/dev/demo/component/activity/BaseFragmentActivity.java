package com.flyeek.dev.demo.component.activity;

import android.app.Activity;

import com.flyeek.dev.demo.util.AnalyticsUtil;

/**
 * Base activity, only can be inherited by activity with fragments.
 * <p>
 * Session will be automaticly analysed, and Page Hopping must be analysed by fragment.
 * Created by flyeek on 5/30/15.
 */
public abstract class BaseFragmentActivity extends Activity{

    @Override
    protected void onResume() {
        super.onResume();

        // UMeng Session analytics.
        AnalyticsUtil.onSessionStart(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // UMeng Session analytics.
        AnalyticsUtil.onSessionEnd(this);
    }
}
