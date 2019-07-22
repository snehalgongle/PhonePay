package com.snake.phonepay.ui.follower;

import com.snake.phonepay.data.DataManager;
import com.snake.phonepay.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class FollowerModule {
    @Provides
    FollowerViewModel provideFollowerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new FollowerViewModel(dataManager, schedulerProvider);
    }
}
