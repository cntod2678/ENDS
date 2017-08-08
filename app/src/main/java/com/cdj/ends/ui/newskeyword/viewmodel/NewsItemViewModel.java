package com.cdj.ends.ui.newskeyword.viewmodel;

import android.view.View;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

public interface NewsItemViewModel {
    String getId();
    int getNumber();
    String getOwner();

    String getSource();
    String getAuthor();
    String getDescription();
    String getTitle();
    String getUrl();
    String getUrlToImage();
    String getPublishedAt();

    void onNewsItemClick(View view);
}
