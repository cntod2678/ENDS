package com.cdj.ends.api.call;

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
    Call<NewsSourceDTO> getSources(@Query("language") String language);
}
