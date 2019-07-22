package com.snake.phonepay.ui.profile;

import android.os.Bundle;
import android.view.View;

import com.snake.phonepay.AppUtils;
import com.snake.phonepay.BR;
import com.snake.phonepay.R;
import com.snake.phonepay.databinding.ActivityProfileBinding;
import com.snake.phonepay.ui.base.BaseActivity;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import static com.snake.phonepay.AppConstants.POSITION;
import static com.snake.phonepay.AppConstants.USER_ID;
import static com.snake.phonepay.AppConstants.USER_NAME;

public class ProfileActivity extends BaseActivity<ActivityProfileBinding, ProfileViewModel> implements IProfileNavigator {

    @Inject
    ProfileViewModel profileViewModel;

    ActivityProfileBinding activityProfileBinding;

    int id;
    int position;
    String userName;

    @Override
    public int getBindingVariable() {
        return BR.profileViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    public ProfileViewModel getViewModel() {
        return profileViewModel;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileBinding = getViewDataBinding();
        profileViewModel.setNavigator(this);
        getIntentExtra();
        if (isNetworkConnected()) {
            profileViewModel.getUserProfile(id, position,userName);
        }
        observerResponse();
        getViewDataBinding().getRoot();
    }

    private void getIntentExtra() {
        if (getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            id = extras.getInt(POSITION);
            position = extras.getInt(USER_ID);
            userName=extras.getString(USER_NAME);
        }
    }

    private void observerResponse() {
        profileViewModel.userProfileResponse.observe(this, userProfile -> {});
        profileViewModel.errorResponse.observe(this,throwable -> handleApiError(throwable));
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
