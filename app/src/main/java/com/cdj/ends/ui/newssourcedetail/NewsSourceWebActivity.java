package com.cdj.ends.ui.newssourcedetail;

/**
 * Created by Dongjin on 2017. 8. 11..
 */

import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.cdj.ends.R;
import com.cdj.ends.data.NewsSource;

import org.parceler.Parcels;

public class NewsSourceWebActivity extends AppCompatActivity {

    private NewsSource mNewsSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_web);
        mNewsSource = new NewsSource();
        getNewsSourceFromIntent(getIntent());

        NewsSourceWebFragment fragment = NewsSourceWebFragment.newInstance(mNewsSource);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.news_source, fragment).commit();

    }

    private void getNewsSourceFromIntent(Intent intent) {
        if (intent != null) {
            mNewsSource = Parcels.unwrap(getIntent().getParcelableExtra("NEWS_SOURCE"));
        }
    }

    public void onSectionAttached(int number) {

    }
}
