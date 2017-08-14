package com.cdj.ends.base.util;

import com.google.gson.Gson;

/**
 * Created by Dongjin on 2017. 8. 14..
 */

public class ParserHelperFromJson<T> {

    private Class<T> mDTOClazz;

    private Gson mGson;

    private String mJsonResponse;

    public ParserHelperFromJson() {}

    public ParserHelperFromJson(String jsonResponse) {
        mGson = new Gson();
        mJsonResponse = jsonResponse;
    }

    public Class<T> convertedData() {
        mDTOClazz = mGson.fromJson(mJsonResponse, mDTOClazz.getClass());
        return mDTOClazz;
    }
}
