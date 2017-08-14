package com.cdj.ends.ui.newssource.viewmodel;

import android.util.Log;

import com.cdj.ends.api.news.NewsSourceAPI;
import com.cdj.ends.base.viewmodel.NotifyUpdateViewModelListener;
import com.cdj.ends.data.NewsSource;
import com.cdj.ends.dto.NewsSourceDTO;

import java.util.ArrayList;
import java.util.List;

import static com.cdj.ends.Config.BASE_URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

public class SourceViewModelImpl implements SourceViewModel {

    private static final String TAG = "SourceViewModelImpl";

    private NotifyUpdateViewModelListener notifyUpdateViewModelListener;

    private NewsSourceAPI newsSourceAPI;

    public SourceViewModelImpl() {
        newsSourceAPI = new NewsSourceAPI(BASE_URL);
    }

    @Override
    public void fetchSource() {
        newsSourceAPI.requestSourceItems(new Callback<NewsSourceDTO>() {
            @Override
            public void onResponse(Call<NewsSourceDTO> call, Response<NewsSourceDTO> response) {
                NewsSourceDTO newsSourceDTO = response.body();

                List<SourceItemViewModel> itemVMList = new ArrayList<SourceItemViewModel>();
                for (NewsSource newsSource : newsSourceDTO.getSources()) {
                    itemVMList.add(new SourceItemViewModelImpl(newsSource));
                }

                if(notifyUpdateViewModelListener != null) {
                    notifyUpdateViewModelListener.onUpdatedViewModel(itemVMList);
                }
            }

            @Override
            public void onFailure(Call<NewsSourceDTO> call, Throwable t) {
                Log.d(TAG, "source load Fail");
            }
        });
    }

    @Override
    public void setUpdateViewModelListener(NotifyUpdateViewModelListener listener) {
        notifyUpdateViewModelListener = listener;
    }
}
