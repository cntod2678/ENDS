package com.cdj.ends.api;

import com.cdj.ends.dto.NewsDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

public interface NewsCallService {

    @GET("articles/")
    Call<NewsDTO> newsKeyword(@Query("source") String source, @Query("apiKey") String apiKey);
}
