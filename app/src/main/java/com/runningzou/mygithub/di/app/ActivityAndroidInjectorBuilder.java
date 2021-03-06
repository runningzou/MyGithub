package com.runningzou.mygithub.di.app;

import android.app.Activity;

import com.runningzou.mygithub.MainActivity;
import com.runningzou.mygithub.di.login.LoginActivityComponent;
import com.runningzou.mygithub.di.mainActivity.MainActivityComponent;
import com.runningzou.mygithub.login.LoginActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Created by runningzou on 2017/9/17.
 */

@Module
public abstract class ActivityAndroidInjectorBuilder {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindMainActivity(MainActivityComponent.Builder builder);

    @Binds
    @IntoMap
    @ActivityKey(LoginActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindLoginActivity(LoginActivityComponent.Builder builder);

}
