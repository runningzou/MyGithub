package com.runningzou.mygithub.util;

/**
 * Created by runningzou on 2017/9/20.
 */

import android.view.View;

import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RxCommandBinder {

    @Deprecated
    public static <T> Disposable bind(final View view, final RxCommand<T> command) {
        view.setClickable(true);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                command.execute(null);
            }
        });

        return command.enabled()
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean enabled) throws Exception {
                        view.setEnabled(enabled);
                    }
                });
    }

    public static <T> void bind(final View view, final RxCommand<T> command, ObservableTransformer<Boolean, Boolean> takeUntil) {
        view.setClickable(true);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                command.execute(null);
            }
        });
        command.enabled()
                .compose(takeUntil)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean enabled) throws Exception {
                        view.setEnabled(enabled);
                    }
                });
    }

}
