package com.cdj.ends.ui.newssource.viewmodel;

import com.cdj.ends.base.viewmodel.NotifyUpdateViewModelListener;

import java.util.List;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

public interface SourceViewModel {
    void fetchSource();

    void setUpdateViewModelListener(NotifyUpdateViewModelListener listener);
}
