package com.cdj.ends.call;

import com.cdj.ends.dto.NewsDTO;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

public interface NewsCallService {

    @GET("articles/")
    Call<NewsDTO> newsKeyword(@QueryMap Map<String, String> map);
}