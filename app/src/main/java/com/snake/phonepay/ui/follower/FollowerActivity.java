package com.snake.phonepay.ui.follower;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;


import com.snake.phonepay.AppUtils;
import com.snake.phonepay.R;
import com.snake.phonepay.databinding.ActivityFollowerBinding;
import com.snake.phonepay.ui.base.BaseActivity;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class FollowerActivity extends BaseActivity<ActivityFollowerBinding, FollowerViewModel> implements IFollowerNavigator {

    @Inject
    FollowerViewModel followerViewModel;

    ActivityFollowerBinding activityFollowerBinding;

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_follower;
    }

    @Override
    public FollowerViewModel getViewModel() {
        return followerViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFollowerBinding = getViewDataBinding();
        followerViewModel.setNavigator(this);
        getIntentExtra();
        if (isNetworkConnected()) {
            followerViewModel.getFollowers();
        }
        observerResponse();
        getViewDataBinding().getRoot();

    }

    private void getIntentExtra() {

    }

    private void observerResponse() {
        followerViewModel.userFollowerResponse.observe(this,users -> {});
        followerViewModel.errorResponse.observe(this,throwable -> handleApiError(throwable));
    }

    @Override
    public void onClickView(View var1) {

    }

    @Override
    public void goTo(@NotNull Class<?> clazz, Bundle mExtras) {
        AppUtils.NavigatTo(this, clazz, mExtras);
        finish();
    }
}
