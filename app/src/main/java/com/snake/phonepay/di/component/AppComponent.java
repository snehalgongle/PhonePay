package com.snake.phonepay.di.component;

import android.app.Application;
import com.snake.phonepay.PhonePayApp;
import com.snake.phonepay.di.builder.ActivityBuilder;
import com.snake.phonepay.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class, ActivityBuilder.class})
public interface AppComponent {

    void inject(PhonePayApp app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
