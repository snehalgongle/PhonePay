package com.snake.phonepay.ui.main;

import androidx.lifecycle.MutableLiveData;

import com.snake.phonepay.data.DataManager;
import com.snake.phonepay.data.model.api.User;
import com.snake.phonepay.data.model.api_request.UserRequest;
import com.snake.phonepay.rx.SchedulerProvider;
import com.snake.phonepay.ui.base.BaseViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends BaseViewModel<IMainNavigator> {
    MutableLiveData<List<User>> userResponse = new MutableLiveData<>();
    MutableLiveData<Throwable> errorResponse = new MutableLiveData<>();

    public MainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getUserListFromServer() {
        getCompositeDisposable().add(getDataManager().getUserApiCall(new UserRequest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userResponse::setValue, throwable -> errorResponse.setValue(throwable)));

    }
}
