package com.cdj.ends.api.news;

import com.cdj.ends.api.RetrofitManagerAPI;
import com.cdj.ends.call.NewsCallService;
import com.cdj.ends.dto.NewsSourceDTO;

import retrofit2.Callback;

/**
 * Created by Dongjin on 2017. 8. 10..
 */

public class NewsSourceAPI extends RetrofitManagerAPI<NewsCallService, NewsSourceDTO> {

    NewsCallService newsCallService;
    private final String SOURCE_MODE = "en";

    public NewsSourceAPI(String baseUrl) {
        super(NewsCallService.class, baseUrl);
        newsCallService = createCallService();
    }

    public void requestSourceItems(Callback<NewsSourceDTO> callback) {
        newsCallService.getSources(SOURCE_MODE).enqueue(callback);
    }
}
