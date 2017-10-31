package com.runningzou.mygithub.net;

import android.util.Log;

import com.runningzou.mygithub.model.Trending;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zouzhihao on 2017/10/14.
 */

public class TrendingService {

    public static final String[] language = new String[]{
            "all", "c", "c++", "java", "python", "shell", "javascript", "objective-c", "php"
    };

    public static final String[] time = new String[]{
            "?since=daily", "?since=weekly", "?since=monthly"
    };

    public static final String TAG = TrendingService.class.getSimpleName();

    public static Observable<List<Trending>> getTrending(final int languagePos, final int timePos) {

        return Observable.create(new ObservableOnSubscribe<List<Trending>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Trending>> e) throws Exception {

                String lan;
                String ti;

                if (languagePos == 0) {
                    lan = "";
                } else {
                    lan = language[languagePos];
                }

                ti = time[timePos];

                Log.d(TAG,"https://github.com/trending/" + lan + ti);

                Document doc = Jsoup.connect("https://github.com/trending/" + lan + ti).get();
                Elements repoList = doc.select(".repo-list");
                List<Trending> result = new ArrayList<>();
                for (Element it : repoList.select("li")) {
                    Trending trending = new Trending();
                    trending.setTitle(it.select(".d-inline-block > h3 > a").text());
                    trending.setDescription(it.select(".py-1 > p").text());
                    trending.setStarts(it.select(".f6 > a[href*=/stargazers]").text());
                    trending.setForks(it.select(".f6 > a[href*=/network]").text());
                    trending.setToday_Starts(it.select(".f6 > span.float-right").text());
                    if (trending.getToday_Starts() == null || trending.getToday_Starts().equals("")) {
                        trending.setToday_Starts(it.select(".f6 > span.float-sm-right").text());
                    }
                    trending.setLanguage(it.select(".f6 .mr-3 > span[itemprop=programmingLanguage]").text());
                    if (trending.getLanguage() == null || trending.getLanguage().equals("")) {
                        trending.setLanguage(it.select(".f6 span[itemprop=programmingLanguage]").text());
                    }

                    result.add(trending);
                }
                Log.d(TAG, "result" + result.size());
                e.onNext(result);
                e.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
