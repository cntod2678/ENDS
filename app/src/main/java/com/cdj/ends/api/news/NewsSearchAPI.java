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

    public void newsCategories(String category, Callback<List<News>> callback) {
        newsCallService.newsCategories(category).enqueue(callback);
    }

    public void newsCategory(String category, Callback<List<News>> callback) {
        newsCallService.newsCategory(category).enqueue(callback);
    }

}
