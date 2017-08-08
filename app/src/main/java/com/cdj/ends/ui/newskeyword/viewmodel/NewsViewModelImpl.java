package com.cdj.ends.ui.newskeyword.viewmodel;

import android.util.Log;

import com.cdj.ends.api.NewsCallService;
import com.cdj.ends.base.viewmodel.NotifyUpdateViewModelListener;
import com.cdj.ends.data.News;
import com.cdj.ends.dto.NewsDTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.cdj.ends.Config.BASE_URL;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

public class NewsViewModelImpl implements NewsViewModel {

    private static final String TAG = "NewsViewModelImpl";

    private NotifyUpdateViewModelListener notifyUpdateViewModelListener;

    public NewsViewModelImpl() {}

    @Override
    public void refreshNews() {
        fetchNews();
    }

    @Override
    public void fetchNews() {
        //todo 통신하는 거
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsCallService newsCallService = retrofit.create(NewsCallService.class);
        Call<NewsDTO> call = newsCallService.newsKeyword("techcrunch", "a57373320bb04fe5a06e3238300b4ad2");

        call.enqueue(new Callback<NewsDTO>() {
            @Override
            public void onResponse(Call<NewsDTO> call, Response<NewsDTO> response) {
                NewsDTO newsDTO = response.body();
                Log.d(TAG, "" + response.headers());

                List<NewsItemViewModel> itemVMList = new ArrayList<NewsItemViewModel>();
                for(News news : newsDTO.getArticles()) {
                    itemVMList.add(new NewsItemViewModelImpl(news));
                }

                Log.d(TAG, itemVMList.size() + " ");
                if(notifyUpdateViewModelListener != null) {
                    notifyUpdateViewModelListener.onUpdatedViewModel(itemVMList);
                }

            }

            @Override
            public void onFailure(Call<NewsDTO> call, Throwable t) {
                Log.d(TAG, "retrofit fail");
                Log.d(TAG, t.getMessage());
            }
        });
    }

    @Override
    public void setUpdateViewModelListener(NotifyUpdateViewModelListener listener) {
        notifyUpdateViewModelListener = listener;
    }
}
