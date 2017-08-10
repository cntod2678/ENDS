package com.cdj.ends.api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dongjin on 2017. 8. 10..
 */

public abstract class RetrofitManagerAPI<T, E> extends RetrofitAPI<T> implements Callback<E> {

    private Callback mRetrofitManagerAPI;

    public RetrofitManagerAPI(Class<T> clazz, String baseUrl) {
        super(clazz, baseUrl);
    }

    @Override
    public void onResponse(Call<E> call, Response<E> response) {
        mRetrofitManagerAPI.onResponse(call, response);
    }

    @Override
    public void onFailure(Call<E> call, Throwable t) {
        mRetrofitManagerAPI.onFailure(call, t);
    }
}
