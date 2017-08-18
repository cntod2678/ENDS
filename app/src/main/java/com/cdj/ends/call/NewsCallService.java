package com.cdj.ends.call;

import com.cdj.ends.data.News;
import com.cdj.ends.dto.NewsDTO;
import com.cdj.ends.dto.NewsSourceDTO;
import com.cdj.ends.dto.SourceDTO;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

public interface NewsCallService {
    @GET("articles/")
    Call<NewsDTO> newsKeyword(@QueryMap Map<String, String> map);

    @GET("/articles")
    Call<List<News>> newsKeywords(@Query("keyword") List<String> keyword);

//    @GET("sources/")
//    Call<NewsSourceDTO> getSources(@Query("language") String language);
//
    @GET("/sources")
    Call<List<SourceDTO>> getSources();

    @GET("/category_latest")
    Call<List<News>> newsCategory_latest();

    @GET("/category_famous")
    Call<List<News>> newsCategory_famous(@Query("category") String category);
}
