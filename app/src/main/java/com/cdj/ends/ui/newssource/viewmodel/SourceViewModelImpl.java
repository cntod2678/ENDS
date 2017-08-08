package com.cdj.ends.ui.newssource.viewmodel;

import android.util.Log;

import com.cdj.ends.api.NewsSourceCallService;
import com.cdj.ends.base.viewmodel.NotifyUpdateViewModelListener;
import com.cdj.ends.data.NewsSource;
import com.cdj.ends.dto.NewsSourceDTO;

import java.util.ArrayList;
import java.util.List;

import static com.cdj.ends.Config.BASE_URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

public class SourceViewModelImpl implements SourceViewModel {

    private static final String TAG = "SourceViewModelImpl";
    private final String SOURCE_MODE = "en";

    private NotifyUpdateViewModelListener notifyUpdateViewModelListener;

    public SourceViewModelImpl() {}

    @Override
    public void fetchSource() {
        Retrofit retorfit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsSourceCallService newsSourceCallService = retorfit.create(NewsSourceCallService.class);
        Call<NewsSourceDTO> call = newsSourceCallService.newsSource(SOURCE_MODE);

        call.enqueue(new Callback<NewsSourceDTO>() {
            @Override
            public void onResponse(Call<NewsSourceDTO> call, Response<NewsSourceDTO> response) {
                NewsSourceDTO newsSourceDTO = response.body();
                Log.d(TAG, newsSourceDTO.toString());

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
