package com.cdj.ends.dto;

import com.cdj.ends.data.TransData;

/**
 * Created by Dongjin on 2017. 8. 9..
 */

public class TranslationDTO {
    TransData data;

    public TransData getData() {
        return data;
    }

    public void setData(TransData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TranslationDTO{" +
                "data=" + data.toString() +
                '}';
    }
}
