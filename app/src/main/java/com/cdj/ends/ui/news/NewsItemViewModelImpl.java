package com.cdj.ends.ui.news;

import android.content.Intent;
import android.view.View;

import com.cdj.ends.data.News;
import com.cdj.ends.ui.newsdetail.NewsDetailActivity;

import org.parceler.Parcels;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

public class NewsItemViewModelImpl implements NewsItemViewModel {
    News mNews;

    public NewsItemViewModelImpl(News news) {
        this.mNews = news;
    }

    @Override
    public String getSource() {
        if(mNews.equals("undefined"))
            return "출처가 확인되지 않은 뉴스";
        return mNews.getSource();
    }

    @Override
    public String getAuthor() {
        return mNews.getAuthor();
    }

    @Override
    public String getDescription() {
        return mNews.getDescription();
    }

    @Override
    public String getTitle() {
        return mNews.getTitle();
    }

    @Override
    public String getUrl() {
        return mNews.getUrl();
    }

    @Override
    public String getUrlToImage() {
        return mNews.getUrlToImage();
    }

    @Override
    public String getPublishedAt() {
        return mNews.getPublishedAt();
    }

    @Override
    public String getCategory() {
        return mNews.getCategory();
    }

    @Override
    public void onNewsItemClick(View view) {
        Intent intent = new Intent(view.getContext(), NewsDetailActivity.class);
        News news = mNews;
        intent.putExtra("NEWS", Parcels.wrap(news));
        view.getContext().startActivity(intent);
    }
}
