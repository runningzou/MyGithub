package com.runningzou.mygithub.feedFragment;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.runningzou.mygithub.GitHubApp;
import com.runningzou.mygithub.model.Event;
import com.runningzou.mygithub.net.FeedService;
import com.runningzou.mygithub.util.Live;
import com.runningzou.mygithub.util.RxCommand;
import com.runningzou.mygithub.util.Variable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zouzhihao on 2017/10/13.
 */

@Singleton
public class FeedViewModel extends ViewModel {


    public static final String TAG = FeedViewModel.class.getSimpleName();
    FeedService mFeedService;

    private RxCommand<List<Event>> mEventsLoadMoreRxCommand;
    private RxCommand<List<Event>> mEventsRefreshRxCommand;

    private int nextPage = 1;


    @Inject
    public FeedViewModel(FeedService feedService) {
        mFeedService = feedService;
    }

    public RxCommand<List<Event>> loadMoreEvents() {
        if (mEventsLoadMoreRxCommand == null) {
            mEventsLoadMoreRxCommand = RxCommand.create(new Function<Object, Observable<List<Event>>>() {
                @Override
                public Observable<List<Event>> apply(@NonNull Object o) throws Exception {
                    String user = GitHubApp.mAccessToken.getUser();
                    String token = GitHubApp.mAccessToken.getToken();
                    Observable observable = mFeedService.getUserEvent(user, token, nextPage)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread());

                    Log.d(TAG, "loadmore page = " + nextPage);
                    return observable;
                }
            });

            mEventsLoadMoreRxCommand
                    .switchToLatest()
                    .subscribe(new Consumer<List<Event>>() {
                        @Override
                        public void accept(List<Event> events) throws Exception {
                            nextPage++;
                        }
                    });
        }

        return mEventsLoadMoreRxCommand;
    }

    public RxCommand<List<Event>> refreshEvents() {
        if (mEventsRefreshRxCommand == null) {
            mEventsRefreshRxCommand = RxCommand.create(new Function<Object, Observable<List<Event>>>() {
                @Override
                public Observable<List<Event>> apply(@NonNull Object o) throws Exception {
                    String user = GitHubApp.mAccessToken.getUser();
                    String token = GitHubApp.mAccessToken.getToken();
                    Log.d(TAG, "refresh page = " + nextPage);
                    nextPage = 1;
                    return mFeedService.getUserEvent(user, token, nextPage)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread());
                }
            });
        }

        return mEventsRefreshRxCommand;
    }

}
