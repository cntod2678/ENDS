package com.cdj.ends.ui.news.scrap.viewmodel;

import com.cdj.ends.base.viewmodel.NotifyUpdateViewModelListener;
import com.cdj.ends.data.Scrap;

import java.util.List;

/**
 * Created by Dongjin on 2017. 8. 21..
 */

public interface ScrapViewModel {
    void fetchScraps(List<Scrap> scrapList);
    void setUpdateViewModelListener (NotifyUpdateViewModelListener listener);
}
