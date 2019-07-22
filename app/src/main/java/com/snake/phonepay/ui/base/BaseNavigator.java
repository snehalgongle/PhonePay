package com.snake.phonepay.ui.base;

import android.os.Bundle;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import io.reactivex.annotations.Nullable;

public interface BaseNavigator {

    void onClickView(@Nullable View var1);

    void goTo(@NotNull Class<?> var1, @Nullable Bundle var2);
}
