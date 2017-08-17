package com.cdj.ends.ui.news.newskeyword.viewmodel;

import android.content.Context;
import android.util.Log;

import com.cdj.ends.api.news.NewsKeywordAPI;
import com.cdj.ends.base.util.RealmBuilder;
import com.cdj.ends.base.viewmodel.NotifyUpdateViewModelListener;
import com.cdj.ends.data.Keyword;
import com.cdj.ends.data.News;
import com.cdj.ends.ui.news.NewsItemViewModel;
import com.cdj.ends.ui.news.NewsItemViewModelImpl;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cdj.ends.Config.LOCAL_HOST_URL;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

public class NewsViewModelImpl implements NewsViewModel {

    private static final String TAG = "NewsViewModelImpl";

    private Context mContext;

    private NewsKeywordAPI newsKeywordAPI;

    private NotifyUpdateViewModelListener notifyUpdateViewModelListener;

    private List<String> keywords;

    private Realm mRealm;

    public NewsViewModelImpl() {
        newsKeywordAPI = new NewsKeywordAPI(LOCAL_HOST_URL);
    }

    public NewsViewModelImpl(Context context) {
        this.mContext = context;
        newsKeywordAPI = new NewsKeywordAPI(LOCAL_HOST_URL);
    }

    @Override
    public void refreshNews() {
        fetchNews();
    }

    @Override
    public void fetchNews() {

        Realm.init(mContext);
        mRealm = RealmBuilder.getRealmInstance();
        keywords = new ArrayList<>();

        RealmResults<Keyword> keywordList = mRealm.where(Keyword.class).findAll();
        for(Keyword keyword : keywordList) {
            keywords.add(keyword.getKeyword());
        }

        newsKeywordAPI.requestNewsKeyword(keywords, new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                List<News> newsList = response.body();

                List<NewsItemViewModel> itemVMList = new ArrayList<NewsItemViewModel>();

                for(News news : newsList) {
                    itemVMList.add(new NewsItemViewModelImpl(news));
                }

                if(notifyUpdateViewModelListener != null) {
                    notifyUpdateViewModelListener.onUpdatedViewModel(itemVMList);
                }

            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });

        mRealm.close();
    }

    @Override
    public void setUpdateViewModelListener(NotifyUpdateViewModelListener listener) {
        notifyUpdateViewModelListener = listener;
    }
}
