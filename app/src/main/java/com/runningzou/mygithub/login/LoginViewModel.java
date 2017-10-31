package com.runningzou.mygithub.login;

import android.arch.lifecycle.ViewModel;
import android.content.SharedPreferences;

import android.support.annotation.NonNull;
import android.util.Log;

import com.runningzou.mygithub.GitHubApp;
import com.runningzou.mygithub.model.AccessToken;
import com.runningzou.mygithub.model.AuthModel;
import com.runningzou.mygithub.net.LoginService;
import com.runningzou.mygithub.util.RxCommand;
import com.runningzou.mygithub.util.Variable;

import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;

/**
 * Created by zouzhihao on 2017/10/11.
 */

@Singleton
public class LoginViewModel extends ViewModel {

    private RxCommand<AccessToken> loginCommand;

    public Variable<String> userName;
    public Variable<String> passWord;

    private Observable<Boolean> inputValid;
    private LoginService loginService;


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Inject
    public LoginViewModel(@NonNull LoginService loginService, SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {
        this.loginService = loginService;

        userName = new Variable<>("");
        passWord = new Variable<>("");

        inputValid = Observable.combineLatest(userName.asObservable(), passWord.asObservable(), new BiFunction<String, String, Boolean>() {
            @Override
            public Boolean apply(@io.reactivex.annotations.NonNull String s, @io.reactivex.annotations.NonNull String s2) throws Exception {
                if (s.length() > 0 && s2.length() > 0) {
                    return true;
                }

                return false;
            }
        });

        this.sharedPreferences = sharedPreferences;
        this.editor = editor;

    }


    public RxCommand<AccessToken> loginCommand() {
        if (loginCommand == null) {
            loginCommand = RxCommand.create(inputValid, new Function<Object, Observable<AccessToken>>() {
                @Override
                public Observable<AccessToken> apply(@io.reactivex.annotations.NonNull Object o) throws Exception {

                    final String auth = Credentials.basic(userName.value(), passWord.value());
                    AuthModel authModel = new AuthModel();
                    authModel.setScopes(Arrays.asList("user", "repo", "gist", "notifications", "read:org"));
                    authModel.setNote(userName.value());

                    return loginService.login(auth, authModel)
                            .map(new Function<AccessToken, AccessToken>() {
                                @Override
                                public AccessToken apply(@io.reactivex.annotations.NonNull AccessToken accessToken) throws Exception {

                                    String token = "Token " + accessToken.getToken();
                                    accessToken.setToken(token);
                                    editor.putString(AccessToken.BASIC_AUTH_TAG, auth);
                                    editor.putLong(AccessToken.ID_TAG, accessToken.getId());
                                    editor.putString(AccessToken.TOKEN_TAG, token);
                                    editor.putString(AccessToken.USER_TAG,userName.value());
                                    editor.commit();
                                    accessToken.setBasicAuth(auth);
                                    accessToken.setUser(userName.value());
                                    GitHubApp.mAccessToken = accessToken;
                                    return accessToken;
                                }
                            })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread());
                }
            });
        }

        return loginCommand;
    }

    public boolean isLogined() {

        long id = sharedPreferences.getLong(AccessToken.ID_TAG, -1);
        String accessToken = sharedPreferences.getString(AccessToken.TOKEN_TAG, "");
        String userName = sharedPreferences.getString(AccessToken.USER_TAG, "");
        String basicAuth = sharedPreferences.getString(AccessToken.BASIC_AUTH_TAG, "");

        if (id != -1 && !accessToken.equals("") && !userName.equals("") && !basicAuth.equals("")) {
            Log.d("id", id + "");
            GitHubApp.mAccessToken.setId(id);
            GitHubApp.mAccessToken.setToken(accessToken);
            GitHubApp.mAccessToken.setUser(userName);
            GitHubApp.mAccessToken.setBasicAuth(basicAuth);
            return true;
        }

        return false;
    }
}
