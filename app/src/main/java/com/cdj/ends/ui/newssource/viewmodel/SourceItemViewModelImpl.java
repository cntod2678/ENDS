package com.cdj.ends.ui.newssource.viewmodel;

import android.view.View;

import com.cdj.ends.base.util.ChromeTabActionBuilder;
import com.cdj.ends.data.NewsSource;

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
        ChromeTabActionBuilder.openChromTab(view.getContext(), mNewsSource.getUrl());
    }
}
