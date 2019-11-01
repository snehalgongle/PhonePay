package com.snake.phonepay.ui.main;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.UrlQuerySanitizer;
import android.os.BaseBundle;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.snake.phonepay.AppUtils;
import com.snake.phonepay.BR;
import com.snake.phonepay.R;
import com.snake.phonepay.adapter.BaseRecyclerAdapter;
import com.snake.phonepay.adapter.OnDataBindCallback;
import com.snake.phonepay.data.model.api.User;
import com.snake.phonepay.databinding.ActivityMainBinding;
import com.snake.phonepay.databinding.UserListItemBinding;
import com.snake.phonepay.ui.base.BaseActivity;
import com.snake.phonepay.ui.profile.ProfileActivity;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.snake.phonepay.AppConstants.POSITION;
import static com.snake.phonepay.AppConstants.USER_ID;
import static com.snake.phonepay.AppConstants.USER_NAME;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements IMainNavigator, OnDataBindCallback<UserListItemBinding>, AdapterView.OnItemSelectedListener {

    @Inject
    MainViewModel mainViewModel;

    @Inject
    MainListViewModel mainListViewModel;

    ActivityMainBinding activityMainBinding;

    BaseRecyclerAdapter<User, UserListItemBinding> adapter;
    int pageNumber = 1;

    @Override
    public int getBindingVariable() {
        return BR.mainViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getViewModel() {
        return mainViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = getViewDataBinding();
        mainViewModel.setNavigator(this);
        if (isNetworkConnected()) {
            mainViewModel.getUserListFromServer();
        }else{
            showToastString("No Internet Connection..");
        }
        observerResponse();
        setUpRecycleView();
        getViewDataBinding().getRoot();
    }

    private void setUpRecycleView() {
        getViewDataBinding().recycleView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BaseRecyclerAdapter<User, UserListItemBinding>(R.layout.user_list_item,
                BR.user, mainListViewModel.usersItemList,
                null,
                this);
        Log.d(this.getClass().getSimpleName(), "list-count: " + mainListViewModel.usersItemList.size());
        getViewDataBinding().recycleView.setAdapter(adapter);
        adapter.setScrolllistener(getViewDataBinding().recycleView);
    }

    private void observerResponse() {
        mainViewModel.userResponse.observe(this, user -> {
            Log.d(this.getClass().getSimpleName(), "observerResponse: " + user.get(0).getLogin());
            mainListViewModel.usersItemList.addAll(user);
            adapter.notifyDataSetChanged();
        });
        mainViewModel.errorResponse.observe(this, this::handleApiError);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemClick(View view, int position, UserListItemBinding userListItemBinding) {
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        bundle.putInt(USER_ID, mainListViewModel.usersItemList.get(position).getId());
        bundle.putString(USER_NAME, mainListViewModel.usersItemList.get(position).getLogin());
        goTo(ProfileActivity.class, bundle);
    }

    @Override
    public void onItemLongClick(View view, int position, UserListItemBinding userListItemBinding) {

    }

    @Override
    public void onDataBind(UserListItemBinding userListItemBinding, View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListener) {

    }


    @Override
    public void onClickView(View var1) {

    }

    @Override
    public void goTo(@NotNull Class<?> clazz, Bundle mExtras) {
        AppUtils.NavigatTo(this, clazz, mExtras);
        finish();
    }

    @RequiresApi(28)
    private static class OnUnhandledKeyEventListenerWrapper implements View.OnUnhandledKeyEventListener {
        private ViewCompat.OnUnhandledKeyEventListenerCompat mCompatListener;

        OnUnhandledKeyEventListenerWrapper(ViewCompat.OnUnhandledKeyEventListenerCompat listener) {
            this.mCompatListener = listener;
        }

        public boolean onUnhandledKeyEvent(View v, KeyEvent event) {
            return this.mCompatListener.onUnhandledKeyEvent(v, event);
        }
    }
}
