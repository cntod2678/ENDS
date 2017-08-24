package com.cdj.ends.ui.scrap.viewmodel;

import android.content.Context;
import android.util.Log;

import com.cdj.ends.base.viewmodel.NotifyUpdateViewModelListener;
import com.cdj.ends.data.Scrap;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by Dongjin on 2017. 8. 21..
 */

public class ScrapViewModelImpl implements ScrapViewModel {

    private static final String TAG = "ScrapViewModelImpl";

    private Context mContext;

    private Realm mRealm;

    private NotifyUpdateViewModelListener notifyUpdateViewModelListener;

    public ScrapViewModelImpl(Context context) {
        mContext = context;
    }

    @Override
    public void fetchScraps(List<Scrap> scrapList) {

        List<ScrapItemViewModel> itemVMList = new ArrayList<ScrapItemViewModel>();

        for(Scrap scrap : scrapList) {
            itemVMList.add(new ScrapItemViewModelImpl(scrap));
        }

        Log.d(TAG, "itemView model size : " + itemVMList.size());
        if(notifyUpdateViewModelListener != null) {
            notifyUpdateViewModelListener.onUpdatedViewModel(itemVMList);
        }

    }

    @Override
    public void setUpdateViewModelListener(NotifyUpdateViewModelListener listener) {
        notifyUpdateViewModelListener = listener;
    }
}
