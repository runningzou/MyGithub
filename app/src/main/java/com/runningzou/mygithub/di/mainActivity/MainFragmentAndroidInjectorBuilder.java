package com.runningzou.mygithub.di.mainActivity;

import android.support.v4.app.Fragment;

import com.runningzou.mygithub.di.trendingFragment.TrendingFragmentComponent;
import com.runningzou.mygithub.feedFragment.FeedFragment;
import com.runningzou.mygithub.di.feedFragment.FeedFragmentComponent;
import com.runningzou.mygithub.trendingFragment.TrendingFragment;


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
    @dagger.android.support.FragmentKey(FeedFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> bindMainFragment(FeedFragmentComponent.Builder builder);

    @Binds
    @IntoMap
    @dagger.android.support.FragmentKey(TrendingFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> bindTrendingFragment(TrendingFragmentComponent.Builder builder);
}
