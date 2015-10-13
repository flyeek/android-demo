package com.flyeek.dev.demo.ui.widget.webview;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by flyeek on 9/24/15.
 */
public class ChromeCustomTab {

    /**
     * Mandatory.
     */
    private static final String EXTRA_CUSTOM_TABS_SESSION = "android.support.customtabs.extra.SESSION";

    /**
     * Optional. Extra that changes the background color for the omnibox. colorInt is an int that
     * specifies a Color.
     */
    private static final String EXTRA_CUSTOM_TABS_TOOLBAR_COLOR = "android.support.customtabs" +
            ".extra.TOOLBAR_COLOR";

    /**
     * Key that specifies the Bitmap to be used as the image source for the action button.
     */
    private static final String KEY_CUSTOM_TABS_ICON = "android.support.customtabs.customaction.ICON";

    /**
     * Key that specifies the PendingIntent to launch when the action button or menu item was
     * tapped. Chrome will be calling {@link PendingIntent#send()} on taps after adding the url
     * as data. The client app can call {@link Intent#getDataString()} to get the url.
     */
    public static final String KEY_CUSTOM_TABS_PENDING_INTENT = "android.support.customtabs.customaction.PENDING_INTENT";

    /**
     * Optional. Use a bundle for parameters if an action button is specified.
     */
    public static final String EXTRA_CUSTOM_TABS_ACTION_BUTTON_BUNDLE =
            "android.support.customtabs.extra.ACTION_BUNDLE_BUTTON";

    /**
     * Key for the title string for a given custom menu item.
     */
    public static final String KEY_CUSTOM_TABS_MENU_TITLE = "android.support.customtabs.customaction.MENU_ITEM_TITLE";

    /**
     * Optional. Use an ArrayList for specifying menu related params. There should be a separate
     * Bundle for each custom menu item.
     */
    public static final String EXTRA_CUSTOM_TABS_MENU_ITEMS = "android.support.customtabs.extra.MENU_ITEMS";

    private static final int MAX_MENU_ITEMS = 3;

    // Optional. Bundle constructed out of
    //ActivityOptions that Chrome will be running when
// it finishes CustomTabActivity. If you start the Custom Tab with
// a customized animation, you can specify a matching animation when Custom Tab
// returns to your app.
    public static final String EXTRA_CUSTOM_TABS_EXIT_ANIMATION_BUNDLE =
            "android.support.customtabs.extra.EXIT_ANIMATION_BUNDLE";


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static void openBrowser(Context context, String url, boolean customColor, int color,
                                   Bitmap actionButtonIcon, PendingIntent actionButtonPendingIntent,
                                   HashMap<String, PendingIntent> menuItems,
                                   boolean openAnim, int exitClientAnimResId, int enterChromeAnimResId,
                                   boolean closeAnim, int exitChromeAnimResId, int enterClientAnimResId) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        // Config session.
        Bundle session = new Bundle();
        session.putBinder(EXTRA_CUSTOM_TABS_SESSION, null);
        intent.putExtras(session);

        // Config toolbar color.
        if (customColor) {
            intent.putExtra(EXTRA_CUSTOM_TABS_TOOLBAR_COLOR, color);
        }

        // Add action button in toolbar.
        if (actionButtonIcon != null && actionButtonPendingIntent != null) {
            Bundle actionButtonBundle = new Bundle();
            actionButtonBundle.putParcelable(KEY_CUSTOM_TABS_ICON, actionButtonIcon);
            actionButtonBundle.putParcelable(KEY_CUSTOM_TABS_PENDING_INTENT, actionButtonPendingIntent);
            intent.putExtra(EXTRA_CUSTOM_TABS_ACTION_BUTTON_BUNDLE, actionButtonBundle);
        }

        // Add menu items.
        if (menuItems != null) {
            ArrayList<Bundle> menuItemBundleList = new ArrayList<>();
            int menuCount = 0;
            for (Map.Entry<String, PendingIntent> item : menuItems.entrySet()) {
                menuCount++;

                Bundle menuItem = new Bundle();
                menuItem.putString(KEY_CUSTOM_TABS_MENU_TITLE, item.getKey());
                menuItem.putParcelable(KEY_CUSTOM_TABS_PENDING_INTENT, item.getValue());
                menuItemBundleList.add(menuItem);

                if (menuCount == 3) {
                    break;
                }
            }
            intent.putParcelableArrayListExtra(EXTRA_CUSTOM_TABS_MENU_ITEMS, menuItemBundleList);
        }

        // Config transition animation.
        if (closeAnim) {
            Bundle finishBundle = ActivityOptions.makeCustomAnimation(context, enterClientAnimResId,
                    exitChromeAnimResId).toBundle();
            intent.putExtra(EXTRA_CUSTOM_TABS_EXIT_ANIMATION_BUNDLE, finishBundle);
        }

        // Start activity.
        if (openAnim) {
            Bundle startBundle = ActivityOptions.makeCustomAnimation(context, enterChromeAnimResId,
                    exitClientAnimResId).toBundle();
            context.startActivity(intent, startBundle);
        } else {
            context.startActivity(intent);
        }
    }
}
