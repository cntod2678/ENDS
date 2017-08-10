package com.cdj.ends.api.call;

import com.cdj.ends.dto.TranslationDTO;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Dongjin on 2017. 8. 9..
 */

public interface TranslateCallService {

    @GET("./")
    Call<TranslationDTO> translate(@QueryMap Map<String, String> filters);
}
