package com.cdj.ends.ui.newssource.viewmodel;

import android.content.Intent;
import android.view.View;

import com.cdj.ends.data.NewsSource;
import com.cdj.ends.ui.webview.WebViewActivity;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

public class SourceItemViewModelImpl implements SourceItemViewModel {

    NewsSource mNewsSource;

    public SourceItemViewModelImpl(NewsSource newsSource) {
        this.mNewsSource = newsSource;
    }

    @Override
    public String getId() {
        return mNewsSource.getId();
    }

    @Override
    public String getName() {
        return mNewsSource.getName();
    }

    @Override
    public String getDescription() {
        return mNewsSource.getDescription();
    }

    @Override
    public String getUrl() {
        return mNewsSource.getUrl();
    }

    @Override
    public String getCategory() {
        return mNewsSource.getCategory();
    }

    @Override
    public String getCountry() {
        return mNewsSource.getCountry();
    }


    @Override
    public void onSourceItemClick(View view) {
        Intent intent = new Intent(view.getContext(), WebViewActivity.class);
        intent.putExtra("URL", mNewsSource.getUrl());
        view.getContext().startActivity(intent);
    }
}
