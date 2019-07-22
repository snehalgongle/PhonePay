package com.snake.phonepay.di.builder;

import com.snake.phonepay.ui.follower.FollowerActivity;
import com.snake.phonepay.ui.follower.FollowerModule;
import com.snake.phonepay.ui.main.MainActivity;
import com.snake.phonepay.ui.main.MainModule;
import com.snake.phonepay.ui.profile.ProfileActivity;
import com.snake.phonepay.ui.profile.ProfileModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = ProfileModule.class)
    abstract ProfileActivity bindProfileActivity();

    @ContributesAndroidInjector(modules = FollowerModule.class)
    abstract FollowerActivity bindFollowerActivity();
}

