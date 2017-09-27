package com.runningzou.mygithub.di.mainActivity;

import android.support.v4.app.Fragment;

import com.runningzou.mygithub.MainFragment;
import com.runningzou.mygithub.di.mainActivity.mainFragment.MainFragmentComponent;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Created by runningzou on 2017/9/17.
 */

@Module
public abstract class MainFragmentAndroidInjectorBuilder {

    @Binds
    @IntoMap
    @dagger.android.support.FragmentKey(MainFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> bindMainFragment(MainFragmentComponent.Builder builder);
}
