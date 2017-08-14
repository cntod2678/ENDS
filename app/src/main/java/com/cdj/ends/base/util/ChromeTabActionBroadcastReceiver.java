package com.cdj.ends.base.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.cdj.ends.R;

/**
 * Created by Dongjin on 2017. 8. 13..
 */

public class ChromeTabActionBroadcastReceiver  extends BroadcastReceiver {
    public static final String KEY_ACTION_SOURCE = "org.chromium.customtabsdemos.ACTION_SOURCE";

    public static final int ACTION_ACTION_BUTTON = 3;


    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getDataString();

        if (data != null) {
            String toastText = getToastText(context, intent.getIntExtra(KEY_ACTION_SOURCE, -1), data);

            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
        }
    }

    private String getToastText(Context context, int actionSource, String message) {
        switch (actionSource) {
            case ACTION_ACTION_BUTTON:
                return "액션 버튼번";
            default:
                return "정의되지 않음";
        }
    }
}