package com.runningzou.mygithub.di.mainActivity;

import com.runningzou.mygithub.MainActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by runningzou on 2017/9/17.
 */

@Subcomponent(modules = {MainActivityModule.class, MainFragmentAndroidInjectorBuilder.class})
public interface MainActivityComponent extends AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity> {
    }
}
