package com.snake.phonepay.ui.follower;

import androidx.lifecycle.MutableLiveData;

import com.snake.phonepay.data.DataManager;
import com.snake.phonepay.data.model.api.User;
import com.snake.phonepay.data.model.api_request.UserFollowerRequest;
import com.snake.phonepay.rx.SchedulerProvider;
import com.snake.phonepay.ui.base.BaseViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FollowerViewModel extends BaseViewModel<IFollowerNavigator> {
    MutableLiveData<List<User>> userFollowerResponse=new MutableLiveData<>();
    MutableLiveData<Throwable> errorResponse=new MutableLiveData<>();

    public FollowerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getFollowers(){
        getCompositeDisposable().add(getDataManager().getUserFollowerApiCall(new UserFollowerRequest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userFollowerResponse::setValue, throwable -> errorResponse.setValue(throwable)));

    }
}
