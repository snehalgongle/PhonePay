package com.snake.phonepay.ui.profile;

import com.snake.phonepay.data.DataManager;
import com.snake.phonepay.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ProfileModule {
    @Provides
    ProfileViewModel provideProfileViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new ProfileViewModel(dataManager, schedulerProvider);
    }
}
