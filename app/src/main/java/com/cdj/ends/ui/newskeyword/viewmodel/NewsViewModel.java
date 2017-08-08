package com.cdj.ends.ui.newskeyword.viewmodel;

import com.cdj.ends.base.viewmodel.NotifyUpdateViewModelListener;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

public interface NewsViewModel {
    void refreshNews();

    void fetchNews();

    void setUpdateViewModelListener(NotifyUpdateViewModelListener listener);
}
