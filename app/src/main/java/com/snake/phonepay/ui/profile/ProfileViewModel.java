package com.snake.phonepay.ui.profile;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;

import com.snake.phonepay.R;
import com.snake.phonepay.data.DataManager;
import com.snake.phonepay.data.model.api.UserProfile;
import com.snake.phonepay.data.model.api_request.UserProfileRequest;
import com.snake.phonepay.rx.SchedulerProvider;
import com.snake.phonepay.ui.base.BaseViewModel;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.snake.phonepay.PhonePayApp.TAG;

public class ProfileViewModel extends BaseViewModel<IProfileNavigator> {
    MutableLiveData<UserProfile> userProfileResponse = new MutableLiveData<>();
    MutableLiveData<Throwable> errorResponse = new MutableLiveData<>();

    public ProfileViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getUserProfile(int id, int position, String userName) {
        getCompositeDisposable().add(getDataManager().getUserProfilerApiCall(new UserProfileRequest(userName))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userProfileResponse::setValue, throwable -> errorResponse.setValue(throwable)));

    }


    @BindingAdapter({"imageFromChannel", "imageResource"})
    public static void loadImage(ImageView image, String avatar, int resource) {
        if (avatar != null) {
            Picasso.with(image.getContext())
                    .load(avatar)
                    .into(image);
        } else {
            image.setImageResource(R.drawable.ic_account_circle_black_24dp);
        }
    }
}
