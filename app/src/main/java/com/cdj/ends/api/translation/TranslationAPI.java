package com.cdj.ends.api.translation;

import com.cdj.ends.api.RetrofitManagerAPI;
import com.cdj.ends.call.TranslateCallService;
import com.cdj.ends.dto.TranslationDTO;

import java.util.Map;

import retrofit2.Callback;

/**
 * Created by Dongjin on 2017. 8. 10..
 */

public class TranslationAPI extends RetrofitManagerAPI<TranslateCallService, TranslationDTO> {

    private TranslateCallService translateCallService;

    public TranslationAPI(String baseUrl) {
        super(TranslateCallService.class, baseUrl);
        translateCallService = createCallService();
    }

    public void requestTrnaslate(Map<String, String> map, Callback<TranslationDTO> callback) {
        translateCallService.translate(map).enqueue(callback);
    }

}
