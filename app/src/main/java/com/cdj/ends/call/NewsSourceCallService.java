package com.cdj.ends.call;

import com.cdj.ends.dto.NewsSourceDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Dongjin on 2017. 8. 8..
 */

public interface NewsSourceCallService {

    @GET("sources/")
    Call<NewsSourceDTO> getSources(@Query("language") String language);
}
