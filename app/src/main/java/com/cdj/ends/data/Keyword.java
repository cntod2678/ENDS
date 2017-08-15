package com.cdj.ends.data;

import io.realm.RealmObject;

/**
 * Created by Dongjin on 2017. 8. 15..
 */

public class Keyword extends RealmObject {
    String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
