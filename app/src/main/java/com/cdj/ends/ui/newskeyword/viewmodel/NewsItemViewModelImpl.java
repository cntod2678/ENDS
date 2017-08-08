package com.cdj.ends.ui.newskeyword.viewmodel;

import android.view.View;

import com.cdj.ends.data.News;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

public class NewsItemViewModelImpl implements NewsItemViewModel {
    News mNews;

    public NewsItemViewModelImpl(News news) {
        this.mNews = news;
    }

    @Override
    public String getId() {
        return mNews.getId();
    }

    @Override
    public int getNumber() {
        return mNews.getNumber();
    }

    @Override
    public String getOwner() {
        return mNews.getOwner();
    }

    @Override
    public String getAuthor() {
        return mNews.getAuthor();
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public String getUrlToImage() {
        return null;
    }

    @Override
    public String getPublishedAt() {
        return null;
    }

    @Override
    public void onNewsItemClick(View view) {

    }
}
