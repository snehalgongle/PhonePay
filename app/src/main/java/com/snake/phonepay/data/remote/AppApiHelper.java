package com.snake.phonepay.data.remote;

import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.snake.phonepay.data.model.api.User;
import com.snake.phonepay.data.model.api.UserProfile;
import com.snake.phonepay.data.model.api_request.UserFollowerRequest;
import com.snake.phonepay.data.model.api_request.UserProfileRequest;
import com.snake.phonepay.data.model.api_request.UserRequest;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class AppApiHelper implements ApiHelper {

    private ApiHeader mApiHeader;

    @Inject
    public AppApiHelper(ApiHeader apiHeader) {
        mApiHeader = apiHeader;
    }

    @Override
    public Single<List<User>> getUserApiCall(UserRequest request) {
        return Rx2AndroidNetworking.get(ApiEndPoint.BASE_URL + "users")
                .build()
                .getObjectListSingle(User.class);
    }

    @Override
    public Single<UserProfile> getUserProfilerApiCall(UserProfileRequest request) {
        return Rx2AndroidNetworking.get(ApiEndPoint.BASE_URL + "users/{userName}")
                .addPathParameter("userName", request.getUserName())
                .build()
                .getObjectSingle(UserProfile.class);
    }

    @Override
    public Single<List<User>> getUserFollowerApiCall(UserFollowerRequest request) {
        return Rx2AndroidNetworking.get(ApiEndPoint.BASE_URL+"users/{userName}/followers")
                .addPathParameter("userName",request.getUserName())
                .build()
                .getObjectListSingle(User.class);
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }
}
