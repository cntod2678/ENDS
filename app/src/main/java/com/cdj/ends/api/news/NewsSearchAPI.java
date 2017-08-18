package com.cdj.ends.api.news;

import com.cdj.ends.api.RetrofitManagerAPI;
import com.cdj.ends.call.NewsCallService;
import com.cdj.ends.data.News;

import java.util.List;

import retrofit2.Callback;


/**
 * Created by Dongjin on 2017. 8. 16..
 */

public class NewsSearchAPI extends RetrofitManagerAPI<NewsCallService, News> {

    NewsCallService newsCallService;

    public NewsSearchAPI(String baseUrl) {
        super(NewsCallService.class, baseUrl);
        newsCallService = createCallService();
    }

    public void newsCategory_latest(Callback<List<News>> callback) {
        newsCallService.newsCategory_latest().enqueue(callback);
    }

    public void newsCategory_famous(String category, Callback<List<News>> callback) {
        newsCallService.newsCategory_famous(category).enqueue(callback);
    }

}
