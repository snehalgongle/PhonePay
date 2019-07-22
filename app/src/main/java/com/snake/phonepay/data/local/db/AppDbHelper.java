package com.snake.phonepay.data.local.db;


import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class AppDbHelper implements IDbHelper {

    private final AppDatabase mAppDatabase;

    private Context context;


    private String TAG = AppDbHelper.class.getSimpleName();

    @Inject
    public AppDbHelper(AppDatabase appDatabase, Context context) {
        this.mAppDatabase = appDatabase;
        this.context = context;
        //encryptDB();
    }

}
