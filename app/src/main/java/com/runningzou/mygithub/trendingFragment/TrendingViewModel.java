package com.runningzou.mygithub.trendingFragment;

import android.arch.lifecycle.ViewModel;

import com.runningzou.mygithub.model.Trending;
import com.runningzou.mygithub.net.TrendingService;
import com.runningzou.mygithub.util.RxCommand;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by zouzhihao on 2017/10/14.
 */

@Singleton
public class TrendingViewModel extends ViewModel {

    private int language = 0;
    private int time = 0;

    private RxCommand<List<Trending>> mTrendingCommand;

    @Inject
    public TrendingViewModel() {

    }

    public RxCommand<List<Trending>> trendingCommand() {

        if (mTrendingCommand == null) {
            mTrendingCommand = RxCommand.create(new Function<Object, Observable<List<Trending>>>() {
                @Override
                public Observable<List<Trending>> apply(@NonNull Object o) throws Exception {
                    return TrendingService.getTrending(language, time);
                }
            });
        }
        return mTrendingCommand;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
