package com.cdj.ends.data;

import java.util.List;

/**
 * Created by Dongjin on 2017. 8. 10..
 */

public class TransData {
    List<Translation> translations;

    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }

    @Override
    public String toString() {
        return "TransData{" +
                "translations=" + translations +
                '}';
    }
}
