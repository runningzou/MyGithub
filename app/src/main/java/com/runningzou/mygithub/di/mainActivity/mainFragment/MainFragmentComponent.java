package com.runningzou.mygithub.di.mainActivity.mainFragment;

import com.runningzou.mygithub.MainFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by runningzou on 2017/9/17.
 */

@Subcomponent(modules = MainFragmentModule.class)
public interface MainFragmentComponent extends AndroidInjector<MainFragment> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainFragment> {

    }
}
