package com.cdj.ends.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dongjin on 2017. 8. 10..
 */

public abstract class RetrofitAPI<T> {

    private String mBaseUrl;

    private Retrofit mRetrofit;

    private Class<T> mCallClazz;

    protected RetrofitAPI(Class<T> clazz, String baseUrl) {
        mCallClazz = clazz;
        mBaseUrl = baseUrl;
        mRetrofit = createRetrofit();
    }

    private Retrofit createRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    protected T createCallService() {
        return mRetrofit.create(mCallClazz);
    }
}
