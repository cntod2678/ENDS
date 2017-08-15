package com.cdj.ends.base.util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;

import com.cdj.ends.R;


/**
 * Created by Dongjin on 2017. 8. 14..
 */

public class ChromeTabActionBuilder {

    public static void openChromTab(Context mContext, String url) {
        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();

        intentBuilder.setToolbarColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));

        // set action button
        intentBuilder.setActionButton(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher), "Action Button",
                createPendingIntent(mContext, ChromeTabActionBroadcastReceiver.ACTION_ACTION_BUTTON));

        // set start and exit animations
        intentBuilder.setStartAnimations(mContext, R.anim.slide_in_right, R.anim.slide_out_left);
        intentBuilder.setExitAnimations(mContext, android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);

        CustomTabsIntent customTabsIntent = intentBuilder.build();

        customTabsIntent.launchUrl(mContext, Uri.parse(url));
    }


    /**
     * Creates a pending intent to send a broadcast to the {@link ChromeTabActionBroadcastReceiver}
     * @param actionSource
     * @return
     */
    private static PendingIntent createPendingIntent(Context mContext, int actionSource) {
        Intent actionIntent = new Intent(mContext, ChromeTabActionBroadcastReceiver.class);
        actionIntent.putExtra(ChromeTabActionBroadcastReceiver.KEY_ACTION_SOURCE, actionSource);
        return PendingIntent.getBroadcast(mContext, actionSource, actionIntent, 0);
    }
}
