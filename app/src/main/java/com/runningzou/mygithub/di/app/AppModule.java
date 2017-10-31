package com.runningzou.mygithub.di.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.runningzou.mygithub.di.login.LoginActivityComponent;
import com.runningzou.mygithub.di.mainActivity.MainActivityComponent;
import com.runningzou.mygithub.net.FeedService;
import com.runningzou.mygithub.net.GithubService;
import com.runningzou.mygithub.net.LoginService;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by runningzou on 2017/9/17.
 */

@Module(subcomponents = {MainActivityComponent.class, LoginActivityComponent.class})
public class AppModule {

    @Singleton
    @Provides
    public Retrofit provideRetrofit(Application context) {

        File cacheFile = new File(context.getCacheDir(), "httpCache");
        Cache cache = new Cache(cacheFile, 10 * 1024 * 1024);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(logging)
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

    @Singleton
    @Provides
    public LoginService provideLoginService(Retrofit retrofit) {
        return retrofit.create(LoginService.class);
    }

    @Singleton
    @Provides
    public FeedService provideFeedService(Retrofit retrofit) {
        return retrofit.create(FeedService.class);
    }

    @Singleton
    @Provides
    public SharedPreferences provideSharedPreferences(Application application) {
        SharedPreferences pref = application.getSharedPreferences("data", Context.MODE_PRIVATE);
        return pref;
    }

    @Singleton
    @Provides
    public SharedPreferences.Editor provideSharedPreferencesEditor(Application application) {
        SharedPreferences pref = application.getSharedPreferences("data", Context.MODE_PRIVATE);
        return pref.edit();
    }

}
