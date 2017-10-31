package com.runningzou.mygithub.di.feedFragment;

import com.runningzou.mygithub.feedFragment.FeedFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by runningzou on 2017/9/17.
 */

@Subcomponent(modules = FeedFragmentModule.class)
public interface FeedFragmentComponent extends AndroidInjector<FeedFragment> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<FeedFragment> {

    }
}
