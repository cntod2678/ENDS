package com.cdj.ends.ui.news.newssearch.viewmodel;

import com.cdj.ends.base.viewmodel.NotifyUpdateViewModelListener;

/**
 * Created by Dongjin on 2017. 8. 16..
 */

public interface CategoriesViewModel {
    void fetchLatest();
    void fetchFamous(String category);
    void setUpdateViewModelListener(NotifyUpdateViewModelListener listener);
    void setUpdateCategoryLatestListener(NotifyUpdateViewModelListener listener);
}
