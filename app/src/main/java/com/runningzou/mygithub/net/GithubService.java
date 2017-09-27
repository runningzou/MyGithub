package com.runningzou.mygithub.net;

import com.runningzou.mygithub.model.SearchRepoResult;

import io.reactivex.Observable;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by runningzou on 2017/9/18.
 */

public interface GithubService {

    /**
     * @param keyWorld 搜索关键字
     * @param sort     排序方式 stars forks updated
     * @param order    asd desc
     * @return
     */
    @GET("search/repositories")
    public Observable<Result<SearchRepoResult>> searchRepo(@Query("q") String keyWorld, @Query("sort") String sort, @Query("order") String order);


    public static class Sort {
        public static final String starts = "stars";
        public static final String forks = "forks";
        public static final String updated = "updated";
    }

    public static class Order {
        public static final String asc = "asc";
        public static final String desc = "desc";
    }
}
