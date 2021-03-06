package com.cdj.ends.ui.newssource.viewmodel;

import android.view.View;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

public interface SourceItemViewModel {
    String getId();
    String getName();
    String getDescription();
    String getUrl();
    String getCategory();
    String getCountry();
    String getImgUrl();

    void onSourceItemClick(View view);
}
