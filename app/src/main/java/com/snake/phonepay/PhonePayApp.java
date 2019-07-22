package com.snake.phonepay;


import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.graphics.Region;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.BuildConfig;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.snake.phonepay.di.component.AppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasServiceInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class PhonePayApp extends Application implements HasActivityInjector,HasSupportFragmentInjector, HasServiceInjector {

    protected static AppComponent sAppComponent;

    public static String TAG = "PhonePayApp";

    protected static PhonePayApp sInstance;

    Context mContext;

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Inject
    DispatchingAndroidInjector<Service> dispatchingServiceInjector;

    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentInjector;


    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        initAppComponent();
    }

    protected void initAppComponent() {
/*        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);*/
        sAppComponent = AppInjector.init(this);


        AndroidNetworking.initialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BASIC);
        }
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

    }

    public static PhonePayApp getInstance() {
        return sInstance;
    }

    public Context getContext(){
        return mContext;
    }

    public void setContext(Context aContext){
        this.mContext=aContext;
    }
    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentInjector;
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return dispatchingServiceInjector;
    }
}
