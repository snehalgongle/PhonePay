package com.snake.phonepay.ui.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.snake.phonepay.data.DataManager;
import com.snake.phonepay.rx.SchedulerProvider;

import java.lang.ref.WeakReference;
import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel<N> extends ViewModel {

    public MutableLiveData<String> toast=new MutableLiveData<>();

    public MutableLiveData<String> snackBar=new MutableLiveData<>();

    private final DataManager mDataManager;

    private final SchedulerProvider mSchedulerProvider;

    private CompositeDisposable mCompositeDisposable;

    private WeakReference<N> mNavigator;

    public BaseViewModel(DataManager dataManager,
                         SchedulerProvider schedulerProvider) {
        this.mDataManager = dataManager;
        this.mSchedulerProvider = schedulerProvider;
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }


    public N getNavigator() {
        return mNavigator.get();
    }

    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }


}
