package com.runningzou.mygithub.di.trendingFragment;

import com.runningzou.mygithub.feedFragment.FeedFragment;
import com.runningzou.mygithub.trendingFragment.TrendingFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by zouzhihao on 2017/10/13.
 */

@Subcomponent(modules = TrendingFragmentModule.class)
public interface TrendingFragmentComponent extends AndroidInjector<TrendingFragment> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<TrendingFragment> {

    }
}
