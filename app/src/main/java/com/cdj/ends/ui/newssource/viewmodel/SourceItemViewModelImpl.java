package com.cdj.ends.ui.newssource.viewmodel;

import android.view.View;
import android.widget.Toast;

import com.cdj.ends.data.NewsSource;
import com.cdj.ends.data.SortedBysAvailable;

import java.util.List;

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
    public List<SortedBysAvailable> getSortedBysAvailables() {
        return mNewsSource.getSortedBysAvailables();
    }

    @Override
    public void onSourceItemClick(View view) {
        Toast.makeText(view.getContext(), mNewsSource.getName(), Toast.LENGTH_SHORT).show();
    }
}
