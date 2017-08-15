package com.cdj.ends.ui.newsdetail;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.cdj.ends.R;
import com.cdj.ends.data.News;

import org.parceler.Parcels;

public class NewsDetailActivity extends AppCompatActivity {

    private static final String TAG = "NewsDetailActivity";

    private News mNews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        getNewsDataFromIntent(getIntent());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getNewsDataFromIntent(Intent intent) {
        if(intent != null) {
            mNews = Parcels.unwrap(getIntent().getParcelableExtra("NEWS"));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Fragment fragment = NewsDetailFragment.newInstance(mNews);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contentFrame_detail, fragment, "detail").commit();
    }
}
