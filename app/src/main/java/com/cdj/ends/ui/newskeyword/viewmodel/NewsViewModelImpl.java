package com.cdj.ends.ui.newskeyword.viewmodel;

import android.content.Context;
import android.util.Log;

import com.cdj.ends.R;
import com.cdj.ends.api.news.NewsKeywordAPI;
import com.cdj.ends.base.viewmodel.NotifyUpdateViewModelListener;
import com.cdj.ends.data.News;
import com.cdj.ends.dto.NewsDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cdj.ends.Config.BASE_URL;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

public class NewsViewModelImpl implements NewsViewModel {

    private static final String TAG = "NewsViewModelImpl";

    private Context mContext;

    private NewsKeywordAPI newsKeywordAPI;

    private NotifyUpdateViewModelListener notifyUpdateViewModelListener;

    private Map<String, String> filter = new HashMap<>();

    public NewsViewModelImpl() {
        newsKeywordAPI = new NewsKeywordAPI(BASE_URL);
    }

    public NewsViewModelImpl(Context context) {
        this.mContext = context;
        newsKeywordAPI = new NewsKeywordAPI(BASE_URL);
    }

    @Override
    public void refreshNews() {
        fetchNews();
    }

    @Override
    public void fetchNews() {
        filter.put("source", "techcrunch");
        filter.put("apiKey", mContext.getString(R.string.NEWS_KEY));

        newsKeywordAPI.requestNewsItem(filter, new Callback<NewsDTO>() {
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
                Log.d(TAG, t.getMessage());
            }
        });
    }

    @Override
    public void setUpdateViewModelListener(NotifyUpdateViewModelListener listener) {
        notifyUpdateViewModelListener = listener;
    }
}
