package com.snake.phonepay.rx;

import io.reactivex.Scheduler;

public class AppSchedulerProvider implements SchedulerProvider{
    @Override
    public Scheduler computation() {
        return null;
    }

    @Override
    public Scheduler io() {
        return null;
    }

    @Override
    public Scheduler ui() {
        return null;
    }
}
