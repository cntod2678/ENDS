package com.cdj.ends.ui.news.newssearch.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.cdj.ends.api.news.NewsSearchAPI;
import com.cdj.ends.base.viewmodel.NotifyUpdateViewModelListener;
import com.cdj.ends.data.News;
import com.cdj.ends.dto.NewsDTO;
import com.cdj.ends.ui.news.NewsItemViewModel;
import com.cdj.ends.ui.news.NewsItemViewModelImpl;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cdj.ends.Config.LOCAL_HOST_URL;

/**
 * Created by Dongjin on 2017. 8. 16..
 */

public class CategoriesViewModelImpl implements CategoriesViewModel {

    private static final String TAG = "CategoriesViewModelImpl";

    private Context mContext;

    private NewsSearchAPI newsSearchAPI;

    private NotifyUpdateViewModelListener notifyUpdateFavoriteListener;

    private NotifyUpdateViewModelListener notifyLatestListener;

    public CategoriesViewModelImpl() {}

    public CategoriesViewModelImpl(Context context) {
        mContext = context;
        newsSearchAPI = new NewsSearchAPI(LOCAL_HOST_URL);
    }

    @Override
    public void fetchLatest() {
        newsSearchAPI.newsCategory_latest(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                List<News> newsList = response.body();
                List<NewsItemViewModel> itemViewModelList = new ArrayList<NewsItemViewModel>();

                for(News news : newsList) {
                    itemViewModelList.add(new NewsItemViewModelImpl(news));
                }
                if(notifyLatestListener != null) {
                    notifyLatestListener.onUpdatedViewModel(itemViewModelList);
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    @Override
    public void fetchFamous(final String category) {
        newsSearchAPI.newsCategory_famous(category, new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                List<News> newsList = response.body();
                List<NewsItemViewModel> itemVMList = new ArrayList<NewsItemViewModel>();

                if(newsList.size() == 0) {
                    Toast.makeText(mContext, category + "에 해당하는 뉴스 항목이 없습니다.", Toast.LENGTH_SHORT).show();
                }

                for (News news : newsList) {
                    itemVMList.add(new NewsItemViewModelImpl(news));
                }

                if (notifyUpdateFavoriteListener != null) {
                    notifyUpdateFavoriteListener.onUpdatedViewModel(itemVMList);
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    @Override
    public void setUpdateViewModelListener(NotifyUpdateViewModelListener listener) {
        notifyUpdateFavoriteListener = listener;
    }

    @Override
    public void setUpdateCategoryLatestListener(NotifyUpdateViewModelListener listener) {
        notifyLatestListener = listener;
    }
}
