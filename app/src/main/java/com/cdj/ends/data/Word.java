package com.cdj.ends.data;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Dongjin on 2017. 8. 12..
 */

public class Word extends RealmObject {

    public String word;
    public String translatedWord;
    public String date;
    public boolean chkEdu;

    public Word() {}

    public Word(String word, String translatedWord, String date, boolean chkEdu) {
        this.word = word;
        this.translatedWord = translatedWord;
        this.date = date;
        this.chkEdu = chkEdu;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslatedWord() {
        return translatedWord;
    }

    public void setTranslatedWord(String translatedWord) {
        this.translatedWord = translatedWord;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isChkEdu() {
        return chkEdu;
    }

    public void setChkEdu(boolean chkEdu) {
        this.chkEdu = chkEdu;
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", translatedWord='" + translatedWord + '\'' +
                ", date='" + date + '\'' +
                ", chkEdu=" + chkEdu +
                '}';
    }
}
