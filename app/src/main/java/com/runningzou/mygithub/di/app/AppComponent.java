package com.runningzou.mygithub.di.app;

import android.app.Application;

import com.runningzou.mygithub.GitHubApp;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by runningzou on 2017/9/17.
 */

@Singleton
@Component(
        modules = {

                AppModule.class,
                ActivityAndroidInjectorBuilder.class
        }
)
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance Builder application (Application application);
        AppComponent build();
    }

    void inject(GitHubApp gitHubApp);
}
