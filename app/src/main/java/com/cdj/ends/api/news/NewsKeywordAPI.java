package com.cdj.ends.api.news;

import com.cdj.ends.api.RetrofitManagerAPI;

import com.cdj.ends.call.NewsCallService;
import com.cdj.ends.data.News;
import com.cdj.ends.dto.NewsDTO;

import java.util.List;
import java.util.Map;

import retrofit2.Callback;

/**
 * Created by Dongjin on 2017. 8. 10..
 */

public class NewsKeywordAPI extends RetrofitManagerAPI<NewsCallService, NewsDTO> {

    private NewsCallService newsCallService;

    public NewsKeywordAPI(String baseUrl) {
        super(NewsCallService.class, baseUrl);
        newsCallService = createCallService();
    }

    public void requestNewsItem(Map<String, String> map, Callback<NewsDTO> callback) {
        newsCallService.newsKeyword(map).enqueue(callback);
    }

    public void requestNewsKeyword(List<String> keywords, Callback<List<News>> callback) {
        newsCallService.newsKeywords(keywords).enqueue(callback);
    }
}
