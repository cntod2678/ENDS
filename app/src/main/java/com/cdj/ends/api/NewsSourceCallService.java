package com.cdj.ends.api;

import java.util.Map;
import com.cdj.ends.dto.NewsSourceDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

public interface NewsSourceCallService {
    @GET("sources/")
//    Call<NewsSourceDTO> newsSource(@QueryMap(encoded = true) Map<String, String> map);
    Call<NewsSourceDTO> newsSource(@Query("language") String language);
}
