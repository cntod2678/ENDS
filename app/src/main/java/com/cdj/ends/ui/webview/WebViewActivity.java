package com.cdj.ends.ui.webview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.cdj.ends.R;

import org.parceler.Parcels;

/**
 * Created by Dongjin on 2017. 8. 12..
 */

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class WebViewActivity extends AppCompatActivity {

    private static final String TAG = "WebViewActivity";

    private String mUrl ="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        getNewsSourceFromIntent(getIntent());

        WebViewFragment fragment = WebViewFragment.newInstance(mUrl);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.news_web, fragment).commit();
    }

    private void getNewsSourceFromIntent(Intent intent) {
        if (intent != null) {
            mUrl = intent.getStringExtra("URL");
            Log.d(TAG, mUrl);
        }
    }

    public void onSectionAttached(int number) {

    }
}
