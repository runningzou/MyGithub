package com.runningzou.mygithub;

import android.app.Activity;
import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.runningzou.mygithub.di.app.AppInjector;
import com.runningzou.mygithub.model.AccessToken;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by runningzou on 2017/9/17.
 */

public class GitHubApp extends Application implements HasActivityInjector {

    public static AccessToken mAccessToken = new AccessToken();

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private static GitHubApp sGitHubApp;

    @Inject
    DispatchingAndroidInjector<Activity> mDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        AppInjector.init(this);
        sGitHubApp = this;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mDispatchingAndroidInjector;
    }

    public static GitHubApp getApp() {
        return sGitHubApp;
    }
}
