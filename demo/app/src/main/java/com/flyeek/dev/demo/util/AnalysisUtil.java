package com.flyeek.dev.demo.util;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import java.util.Map;

/**
 * Helper Class for analytics.
 * <p>
 * Created by flyeek on 5/30/15.
 */
public class AnalysisUtil {

    private static Context mContext;

    public static void init(Context context) {
        mContext = context;

        // Init UMeng analytics.
        MobclickAgent.openActivityDurationTrack(false);
    }

    /**
     * Used to analyse newly added user, active user, launch times, time of usage and so on.
     * <p>
     * Must be paired with {@link #onSessionEnd(Context)}.
     *
     * @param activityContext
     */
    public static void onSessionStart(Context activityContext) {
        MobclickAgent.onResume(activityContext);
    }

    /**
     * Used to analyse newly added user, active user, launch times, time of usage and so on.
     * <p>
     * Must be paired with {@link #onSessionStart(Context)}.
     *
     * @param activityContext
     */
    public static void onSessionEnd(Context activityContext) {
        MobclickAgent.onPause(activityContext);
    }

    /**
     * Used to analyse page hopping.
     * <p>
     * Must ve paired with {@link #onPageEnd(String)}.
     *
     * @param pageName
     */
    public static void onPageStart(String pageName) {
        MobclickAgent.onPageStart(pageName);
    }

    /**
     * Used to analyse page hopping.
     * <p>
     * Must ve paired with {@link #onPageStart(String)}.
     *
     * @param pageName
     */
    public static void onPageEnd(String pageName) {
        MobclickAgent.onPageEnd(pageName);
    }

    /**
     * Simple quantity event, which is the frequency of the event corresponding to eventId.
     * <p>
     * Must be called after {@link #onSessionStart(Context)}.
     *
     * @param eventId name of the event.
     */
    public static void sendEvent(String eventId) {
        MobclickAgent.onEvent(mContext, eventId);
    }

    /**
     * Quantity event.
     * <p>
     * Must be called after {@link #onSessionStart(Context)}.
     *
     * @param eventId name of the event.
     * @param hashMapParam (property, value) pairs of the event.
     */
    public static void sendEvent(String eventId, Map<String, String> hashMapParam) {
        MobclickAgent.onEvent(mContext, eventId, hashMapParam);
    }

    /**
     * Calculate event.
     * <p>
     * Must be called after {@link #onSessionStart(Context)}
     *
     * @param eventId name of the event.
     * @param hashMapParam
     * @param value
     */
    public static void sendEventValue(String eventId, Map<String, String> hashMapParam, int value) {
        MobclickAgent.onEventValue(mContext, eventId, hashMapParam, value);
    }

    /**
     * Update online Params.
     */
    public static void updateOnlineParams() {
        MobclickAgent.updateOnlineConfig(mContext);
    }

    public static void reportError(String error) {
        MobclickAgent.reportError(mContext, error);
    }

}
