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
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.cdj.ends.R;
import com.cdj.ends.base.util.RealmBuilder;
import com.cdj.ends.data.News;
import com.cdj.ends.data.Scrap;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class NewsDetailActivity extends AppCompatActivity implements NewsDetailFragment.ScrapWordListener {

    private static final String TAG = "NewsDetailActivity";

    private News mNews;

    private Realm mRealm;

    private String strDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        Realm.init(this);
        mRealm = RealmBuilder.getRealmInstance();

        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy년 MM월 dd일", java.util.Locale.getDefault());
        strDate = dateFormat.format(date);

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

    @Override
    public void scrap(News news) {
        Log.d(TAG, news.toString());

        RealmResults<Scrap> scraps = mRealm.where(Scrap.class).equalTo("title", news.getTitle())
                .equalTo("publishedAt", news.getPublishedAt()).findAll();

        if(scraps.size() == 0) {
            mRealm.beginTransaction();
            Scrap scrap = mRealm.createObject(Scrap.class);
            scrap.setTitle(news.getTitle());
            scrap.setAuthor(news.getAuthor());
            scrap.setCategory(news.getCategory());
            scrap.setSource(news.getSource());
            scrap.setDescription(news.getDescription());
            scrap.setPublishedAt(news.getPublishedAt());
            scrap.setUrl(news.getUrl());
            scrap.setUrlToImage(news.getUrlToImage());
            scrap.setScrapDate(strDate);
            mRealm.commitTransaction();
        }

        Toast.makeText(getApplicationContext(), "스크랩북에 추가되었습니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
