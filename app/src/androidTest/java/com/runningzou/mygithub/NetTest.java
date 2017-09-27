package com.runningzou.mygithub;

import android.support.test.runner.AndroidJUnit4;

import com.runningzou.mygithub.model.SearchRepoResult;
import com.runningzou.mygithub.net.GithubService;

import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.Result;

/**
 * Created by runningzou on 2017/9/21.
 */

@RunWith(AndroidJUnit4.class)
public class NetTest {

    @Inject
    GithubService mGithubService;

    @Test
    public void repoTest() {
        mGithubService.searchRepo("android", GithubService.Sort.starts, GithubService.Order.desc)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result<SearchRepoResult>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Result<SearchRepoResult> searchRepoResultResult) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
