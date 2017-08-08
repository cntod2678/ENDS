package com.cdj.ends.ui.newsdetail;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cdj.ends.R;
import com.cdj.ends.data.News;

import org.parceler.Parcels;

public class NewsDetailActivity extends AppCompatActivity {

    private static final String TAG = "NewsDetailActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        getNewsDataFromIntent(getIntent());
    }

    private void getNewsDataFromIntent(Intent intent) {
        if(intent != null) {
            News news = Parcels.unwrap(getIntent().getParcelableExtra("NEWS"));
            Log.d(TAG, news.toString());
        }
    }
}
