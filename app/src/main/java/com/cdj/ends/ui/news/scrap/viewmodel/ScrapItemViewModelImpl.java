package com.cdj.ends.ui.news.scrap.viewmodel;

import android.content.Intent;
import android.view.View;

import com.cdj.ends.data.News;
import com.cdj.ends.data.Scrap;
import com.cdj.ends.ui.newsdetail.NewsDetailActivity;

import org.parceler.Parcels;

/**
 * Created by Dongjin on 2017. 8. 21..
 */

public class ScrapItemViewModelImpl implements ScrapItemViewModel {

    Scrap mScrap;

    public ScrapItemViewModelImpl(Scrap scrap) {
        this.mScrap = scrap;
    }

    @Override
    public String getSource() {
        if(mScrap.getSource() == null)
            return "출처가 확인되지 않은 뉴스";
        return mScrap.getSource();
    }

    @Override
    public String getAuthor() {
        if(mScrap.getAuthor() == null)
            return "출처가 확인되지 않은 뉴스";
        return mScrap.getAuthor();
    }

    @Override
    public String getDescription() {
        return mScrap.getDescription();
    }

    @Override
    public String getTitle() {
        return mScrap.getTitle();
    }

    @Override
    public String getUrl() {
        return mScrap.getUrl();
    }

    @Override
    public String getUrlToImage() {
        return mScrap.getUrlToImage();
    }

    @Override
    public String getPublishedAt() {
        return mScrap.getPublishedAt();
    }

    @Override
    public String getScrapDate() {
        return mScrap.getScrapDate();
    }

    @Override
    public String getCategory() {
        return mScrap.getCategory();
    }


    @Override
    public void onScrapItemClick(View view) {
        Intent intent = new Intent(view.getContext(), NewsDetailActivity.class);
        News news = new News();
        news.setTitle(mScrap.getTitle());
        news.setCategory(mScrap.getCategory());
        news.setDescription(mScrap.getDescription());
        news.setUrl(mScrap.getUrl());
        news.setUrlToImage(mScrap.getUrlToImage());
        news.setAuthor(mScrap.getAuthor());
        news.setSource(mScrap.getSource());
        news.setPublishedAt(mScrap.getPublishedAt());

        intent.putExtra("NEWS", Parcels.wrap(news));
        view.getContext().startActivity(intent);
    }
}
