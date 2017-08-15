package com.cdj.ends.api.news;

import com.cdj.ends.api.RetrofitManagerAPI;
import com.cdj.ends.call.NewsSourceCallService;
import com.cdj.ends.dto.NewsSourceDTO;

import retrofit2.Callback;

/**
 * Created by Dongjin on 2017. 8. 10..
 */

public class NewsSourceAPI extends RetrofitManagerAPI<NewsSourceCallService, NewsSourceDTO> {

    NewsSourceCallService newsSourceCallService;
    private final String SOURCE_MODE = "en";

    public NewsSourceAPI(String baseUrl) {
        super(NewsSourceCallService.class, baseUrl);
        newsSourceCallService = createCallService();
    }

    public void requestSourceItems(Callback<NewsSourceDTO> callback) {
        newsSourceCallService.getSources(SOURCE_MODE).enqueue(callback);
    }
}
