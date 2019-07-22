package com.snake.phonepay.di.module;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.snake.phonepay.AppConstants;
import com.snake.phonepay.R;
import com.snake.phonepay.data.AppDataManager;
import com.snake.phonepay.data.DataManager;
import com.snake.phonepay.data.local.db.AppDatabase;
import com.snake.phonepay.data.local.db.AppDbHelper;
import com.snake.phonepay.data.local.db.IDbHelper;
import com.snake.phonepay.data.local.preference.AppPreferencesHelper;
import com.snake.phonepay.data.local.preference.IPreferencesHelper;
import com.snake.phonepay.data.remote.ApiHeader;
import com.snake.phonepay.data.remote.ApiHelper;
import com.snake.phonepay.data.remote.AppApiHelper;
import com.snake.phonepay.di.ApiInfo;
import com.snake.phonepay.di.DatabaseInfo;
import com.snake.phonepay.di.PreferenceInfo;
import com.snake.phonepay.rx.AppSchedulerProvider;
import com.snake.phonepay.rx.SchedulerProvider;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return /*BuildConfig.API_KEY*/ "";
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@DatabaseInfo String dbName, Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, dbName).fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @Singleton
    IDbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    IPreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }


    @Provides
    @Singleton
    ApiHeader.ProtectedApiHeader provideProtectedApiHeader(@ApiInfo String apiKey,
                                                           IPreferencesHelper preferencesHelper) {
        return new ApiHeader.ProtectedApiHeader(
                preferencesHelper.getUserName());
    }

}
