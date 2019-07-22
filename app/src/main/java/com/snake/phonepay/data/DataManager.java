package com.snake.phonepay.data;


import com.snake.phonepay.data.local.db.IDbHelper;
import com.snake.phonepay.data.local.preference.IPreferencesHelper;
import com.snake.phonepay.data.remote.ApiHelper;

public interface DataManager extends IDbHelper, IPreferencesHelper, ApiHelper {

}

