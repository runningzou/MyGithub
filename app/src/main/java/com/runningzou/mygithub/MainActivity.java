package com.runningzou.mygithub;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;

import com.roughike.bottombar.OnTabSelectListener;
import com.runningzou.mygithub.databinding.ActivityMainBinding;
import com.runningzou.mygithub.di.Injector;
import com.runningzou.mygithub.feedFragment.FeedFragment;
import com.runningzou.mygithub.trendingFragment.TrendingFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector,Injector {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentDispatchingAndroidInjector;

    private FeedFragment feedFragment;
    private TrendingFragment trendingFragment;
    private ActionBarDrawerToggle mToggle;
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //启动优化参考 http://yifeng.studio/2016/11/15/android-optimize-for-cold-start/
        setTheme(R.style.AppTheme);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

            Explode explode = null;
            explode = new Explode();
            explode.setDuration(500);
            getWindow().setExitTransition(explode);
            getWindow().setEnterTransition(explode);
        }

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mDrawer = mBinding.drawerLayout;
        mToolbar = mBinding.toolbar;

        mBinding.bottomBarMain.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (feedFragment != null && trendingFragment != null) {

                    switch (tabId) {

                        case R.id.tab_feed:
                            getSupportFragmentManager().beginTransaction()
                                    .show(feedFragment)
                                    .hide(trendingFragment)
                                    .commit();
                            break;
                        case R.id.tab_trend:
                            getSupportFragmentManager().beginTransaction()
                                    .show(trendingFragment)
                                    .hide(feedFragment)
                                    .commit();
                            break;
                    }

                }
            }
        });


        mToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();

        if (savedInstanceState == null) {
            feedFragment = (FeedFragment) FeedFragment.newInstance();
            trendingFragment = (TrendingFragment) TrendingFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_main, trendingFragment, TrendingFragment.class.getCanonicalName())
                    .add(R.id.frame_layout_main, feedFragment, FeedFragment.class.getCanonicalName())
                    .show(feedFragment)
                    .hide(trendingFragment)
                    .commit();
        } else {
            feedFragment = (FeedFragment) getSupportFragmentManager().findFragmentByTag(FeedFragment.class.getCanonicalName());
            trendingFragment = (TrendingFragment) getSupportFragmentManager().findFragmentByTag(TrendingFragment.class.getCanonicalName());
        }

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentDispatchingAndroidInjector;
    }

}
