package com.snake.phonepay.data.local.preference;

import android.content.Context;
import android.content.SharedPreferences;


import com.snake.phonepay.di.PreferenceInfo;

import javax.inject.Inject;

public class AppPreferencesHelper implements IPreferencesHelper {

    private static final String PREF_USER_NAME = "PREF_USER_NAME";
    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName) {

        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public String getUserName() {
        return mPrefs.getString(PREF_USER_NAME, null);
    }

    @Override
    public void setUserName(String accessToken) {
        mPrefs.edit().putString(PREF_USER_NAME, accessToken).apply();
    }

}
