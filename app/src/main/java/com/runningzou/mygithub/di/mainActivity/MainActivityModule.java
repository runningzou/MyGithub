package com.runningzou.mygithub.di.mainActivity;

import com.runningzou.mygithub.di.feedFragment.FeedFragmentComponent;
import com.runningzou.mygithub.di.trendingFragment.TrendingFragmentComponent;

import dagger.Module;

/**
 * Created by runningzou on 2017/9/17.
 */

@Module(subcomponents = {FeedFragmentComponent.class, TrendingFragmentComponent.class})
public class MainActivityModule {
}
