package com.snake.phonepay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AppUtils {
    public static void NavigatTo(Context context, Class<?> clazz, Bundle mExtras) {
        Intent intent = new Intent(context, clazz);
        intent.putExtras(mExtras);
        context.startActivity(intent);
    }
}
