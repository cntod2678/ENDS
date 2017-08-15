package com.cdj.ends.base.util;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Dongjin on 2017. 8. 15..
 */

public class RealmBuilder {

    public static Realm getRealmInstance() {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded() // Migration to run instead of throwing an exception
                .build();

        Realm mRealm = Realm.getInstance(config);

        return mRealm;
    }
}
