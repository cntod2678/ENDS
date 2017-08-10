package com.cdj.ends.data;

/**
 * Created by Dongjin on 2017. 8. 9..
 */

public class Translation {
    String model;
    String translatedText;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    @Override
    public String toString() {
        return "Translation{" +
                "model='" + model + '\'' +
                ", translatedText='" + translatedText + '\'' +
                '}';
    }
}
