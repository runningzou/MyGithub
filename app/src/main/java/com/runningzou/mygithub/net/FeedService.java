package com.runningzou.mygithub.net;

import com.runningzou.mygithub.model.Event;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by zouzhihao on 2017/10/13.
 */

public interface FeedService {
    @GET("users/{user}/received_events")
    Observable<List<Event>> getUserEvent(@Path("user") String user, @Header("Authorization") String authorization, @Query("page") int page);

}
