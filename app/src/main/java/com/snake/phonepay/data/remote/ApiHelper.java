
package com.snake.phonepay.data.remote;


import com.snake.phonepay.data.model.api.User;
import com.snake.phonepay.data.model.api.UserProfile;
import com.snake.phonepay.data.model.api_request.UserFollowerRequest;
import com.snake.phonepay.data.model.api_request.UserProfileRequest;
import com.snake.phonepay.data.model.api_request.UserRequest;

import java.util.List;

import io.reactivex.Single;


public interface ApiHelper {

    Single<List<User>> getUserApiCall(UserRequest request);

    Single<UserProfile> getUserProfilerApiCall(UserProfileRequest request);

    Single<List<User>> getUserFollowerApiCall(UserFollowerRequest request);

    ApiHeader getApiHeader();
}
