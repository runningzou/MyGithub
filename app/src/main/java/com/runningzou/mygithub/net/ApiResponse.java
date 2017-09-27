package com.runningzou.mygithub.net;

import android.support.annotation.Nullable;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by runningzou on 2017/9/19.
 */

public class ApiResponse<T> {

    private int code = 0;

    @Nullable
    public final T body;

    @Nullable
    public final String errorMessage;

    public ApiResponse(Response<T> response) {
        code = response.code();
        if (response.isSuccessful()) {
            body = response.body();
            errorMessage = null;
        } else {
            String message = null;
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }

            errorMessage = message;
            body = null;
        }
    }

    public ApiResponse(Throwable error) {
        code = 500;
        body = null;
        errorMessage = error.getMessage();
    }

    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }
}
