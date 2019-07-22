package com.snake.phonepay.ui.main;

import android.app.Application;


import androidx.databinding.ObservableArrayList;
import com.snake.phonepay.data.DataManager;
import com.snake.phonepay.data.model.api.User;
import com.snake.phonepay.rx.SchedulerProvider;
import com.snake.phonepay.ui.base.BaseApplicationViewModel;

public class MainListViewModel extends BaseApplicationViewModel<IMainNavigator> {
    ObservableArrayList<User> usersItemList = new ObservableArrayList<>();

    MainListViewModel(Application application, DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(application,dataManager,schedulerProvider);
    }



}
