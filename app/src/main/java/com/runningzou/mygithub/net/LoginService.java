package com.runningzou.mygithub.net;

import android.support.annotation.NonNull;

import com.runningzou.mygithub.model.AccessToken;
import com.runningzou.mygithub.model.AuthModel;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by zouzhihao on 2017/10/11.
 */

public interface LoginService {
    @POST("authorizations")
    Observable<AccessToken> login(@Header("Authorization") String authorization, @NonNull @Body AuthModel authModel);

    @DELETE("authorizations/{id}")
    Observable<AccessToken> deleteAuth(@Header("Authorization") String authorization, @Path("id") long id);
}
