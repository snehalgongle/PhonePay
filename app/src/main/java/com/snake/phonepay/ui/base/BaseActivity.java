package com.snake.phonepay.ui.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.androidnetworking.error.ANError;
import com.google.android.material.snackbar.Snackbar;
import com.snake.phonepay.NetworkUtils;
import com.snake.phonepay.PhonePayApp;
import com.snake.phonepay.R;
import com.snake.phonepay.ui.main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import dagger.android.AndroidInjection;

public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel> extends AppCompatActivity
        implements BaseFragment.Callback {

    private T mViewDataBinding;
    private V mViewModel;

    public static void connectivityChanged(boolean b) {
        if (!b) {
            Log.d("internet", "No Internet connection!");
            Toast.makeText(PhonePayApp.getInstance(), "No Internet connection!", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    public abstract V getViewModel();


    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    @Override
    public void onFragmentAttached() {
    }

    @Override
    public void onFragmentDetached(String tag) {
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
        performDataBinding();
    }


    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void showToast(int resourceID) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getString(resourceID), Snackbar.LENGTH_LONG);
        TextView snackbartext = (TextView) snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        snackbartext.setTextColor(Color.WHITE);
        snackbar.show();
    }

    public void showToastString(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        TextView snackbartext = (TextView) snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        snackbartext.setTextColor(Color.WHITE);
        snackbar.show();
    }

    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    public void openActivityOnTokenExpire() {
        finish();
    }

    public void performDependencyInjection() {
        AndroidInjection.inject(this);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }


    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }

    protected void addFragment(@IdRes int containerViewId,
                               @NonNull Fragment fragment,
                               @NonNull String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.anim_pop_enter, R.anim.anim_pop_exit)
                .add(containerViewId, fragment, fragmentTag)
                .disallowAddToBackStack()
                .commit();
    }

    protected void replaceFragment(@IdRes int containerViewId,
                                   @NonNull Fragment fragment,
                                   @NonNull String fragmentTag,
                                   @Nullable String backStackStateName) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.anim_pop_enter, R.anim.anim_pop_exit)
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateName)
                .commit();
    }

    public void handleApiError(Throwable throwable) {
        if (throwable instanceof ANError) {
            ANError anError = (ANError) throwable;
            try {
                showToastString(anError.getErrorDetail());
            } catch (Exception e) {
            }
        }
    }


    public String serverJsonParsingMessage(String errorMessage, String jsonObjectNamee) {
        String serverMessage = null;
        try {
            JSONObject mainObject = new JSONObject(errorMessage);
            serverMessage = mainObject.getString(jsonObjectNamee);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return serverMessage;
    }


    /**
     * This will displays the permission explanation alert popup
     *
     * @param title   title f the popup
     * @param message message of the popup
     */
    public void showPermissionDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_SETTINGS));

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void parseApiError(ANError error) {
        if (error.getErrorCode() == 500) {
            showToastString(error.getMessage());
        }
        if (error.getErrorBody() != null) {
            if (error.getErrorBody().contains("Invalid token.")) {
                startActivity(new Intent(BaseActivity.this, MainActivity.class));
                finish();
                return;
            } else if (error.getErrorBody().contains("For security reasons")) {
                showToastString("For security reasons, last active owner(s) access cannot be revoked.");
            } else if (error.getErrorBody().contains("Last active owner(s)")) {
                showToastString("Last active owner(s) access cannot be modified.");
            } else {
                showToastString(error.getErrorBody());
            }
        }
    }

}
