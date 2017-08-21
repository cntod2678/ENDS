package com.cdj.ends.ui.news.scrap.viewmodel;

import android.view.View;

/**
 * Created by Dongjin on 2017. 8. 21..
 */

public interface ScrapItemViewModel {
    String getCategory();
    String getSource();
    String getAuthor();
    String getDescription();
    String getTitle();
    String getUrl();
    String getUrlToImage();
    String getPublishedAt();
    String getScrapDate();

    void onScrapItemClick(View view);
}
