package com.runningzou.mygithub.di.app;

import android.app.Application;

import com.runningzou.mygithub.di.mainActivity.MainActivityComponent;
import com.runningzou.mygithub.net.GithubService;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by runningzou on 2017/9/17.
 */

@Module(subcomponents = MainActivityComponent.class)
public class AppModule {

    @Singleton
    @Provides
    public Retrofit provideRetrofit(Application context) {

        File cacheFile = new File(context.getCacheDir(), "httpCache");
        Cache cache = new Cache(cacheFile, 10 * 1024 * 1024);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit;
    }


    @Singleton
    @Provides
    public GithubService provideGithubService(Retrofit retrofit) {
        return retrofit.create(GithubService.class);
    }


}
