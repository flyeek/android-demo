package com.flyeek.dev.demo;

import android.app.Fragment;

import com.flyeek.dev.demo.util.AnalyticsUtil;

/**
 * Created by flyeek on 5/30/15.
 */
public class BaseFragment extends Fragment {

    @Override
    public void onResume() {
        super.onResume();

        // UMeng page analytics.
        AnalyticsUtil.onPageStart(getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();

        // UMeng page analytics.
        AnalyticsUtil.onPageEnd(getClass().getSimpleName());
    }
}
