package com.runningzou.mygithub.feedFragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout;
import com.jakewharton.rxbinding2.view.RxView;
import com.runningzou.mygithub.GitHubApp;
import com.runningzou.mygithub.R;
import com.runningzou.mygithub.databinding.FragmentFeedBinding;
import com.runningzou.mygithub.databinding.ItemEventBinding;
import com.runningzou.mygithub.di.Injector;
import com.runningzou.mygithub.model.Event;
import com.runningzou.mygithub.recyclerview.LoadMoreAdapter;
import com.runningzou.mygithub.recyclerview.LoadMoreState;
import com.runningzou.mygithub.recyclerview.ProgressBarLoadMoreAdapter;
import com.runningzou.mygithub.repo.RepoFragment;
import com.runningzou.mygithub.util.Live;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;


/**
 * Created by runningzou on 2017/9/17.
 */

public class FeedFragment extends Fragment implements Injector {

    public static final String TAG = FeedFragment.class.getSimpleName();

    private FragmentFeedBinding mBinding;

    private EventAdapter mEventAdapter;

    @Inject
    FeedViewModel feedViewModel;

    public static Fragment newInstance() {
        Fragment fragment = new FeedFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed, container, false);
        View view = mBinding.getRoot();

        mEventAdapter = new EventAdapter();

        mBinding.recyclerviewFeed.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.recyclerviewFeed.setAdapter(mEventAdapter);

        feedViewModel.refreshEvents()
                .executing()
                .compose(Live.<Boolean>bindLifecycle(this))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        mBinding.swipeRefreshFeed.setRefreshing(aBoolean);
                    }
                });
        feedViewModel.refreshEvents()
                .switchToLatest()
                .compose(Live.<List<Event>>bindLifecycle(this))
                .subscribe(new Consumer<List<Event>>() {
                    @Override
                    public void accept(List<Event> events) throws Exception {
                        mEventAdapter.refresh(events);
                    }
                });
        feedViewModel.refreshEvents()
                .errors()
                .compose(Live.<Throwable>bindLifecycle(this))
                .subscribe(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mBinding.swipeRefreshFeed.setVisibility(View.GONE);
                        mBinding.linearErrorHandler.setVisibility(View.VISIBLE);
                    }
                });

        feedViewModel.loadMoreEvents()
                .switchToLatest()
                .compose(Live.<List<Event>>bindLifecycle(this))
                .subscribe(new Consumer<List<Event>>() {
                    @Override
                    public void accept(List<Event> events) throws Exception {
                        mEventAdapter.loadMore(events);
                        mEventAdapter.setState(LoadMoreState.COMPLETE);
                        if (events == null || events.size() == 0) {
                            mEventAdapter.setState(LoadMoreState.END);
                        }
                    }
                });

        feedViewModel.loadMoreEvents()
                .errors()
                .compose(Live.<Throwable>bindLifecycle(this))
                .subscribe(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getActivity(), getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                        mEventAdapter.setState(LoadMoreState.ERROR);
                    }
                });

        mEventAdapter.setLoadMoreListener(new LoadMoreAdapter.LoadMoreListener() {
            @Override
            public void loadMoreData() {
                Log.d(TAG, "loadMore");
                feedViewModel.loadMoreEvents().execute(null);
                mBinding.swipeRefreshFeed.setRefreshing(false);
            }
        });

        RxView.clicks(mBinding.txtReload)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mBinding.swipeRefreshFeed.setVisibility(View.VISIBLE);
                        mBinding.linearErrorHandler.setVisibility(View.GONE);
                        feedViewModel.refreshEvents().execute(null);
                        mBinding.swipeRefreshFeed.setRefreshing(true);
                    }
                });

        feedViewModel.refreshEvents().execute(null);
        mBinding.swipeRefreshFeed.setRefreshing(true);

        RxSwipeRefreshLayout.refreshes(mBinding.swipeRefreshFeed)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        feedViewModel.refreshEvents().execute(null);
                    }
                });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"isVisible:" + isVisible());
    }

    public class EventAdapter extends ProgressBarLoadMoreAdapter<Event> {

        @Override
        protected ViewDataBinding createBinding(ViewGroup parent, int viewType) {
            return DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_event, parent, false);
        }

        @Override
        protected void bind(ViewDataBinding binding, Event item) {
            ItemEventBinding itemEventBinding = (ItemEventBinding) binding;

            Glide.with(GitHubApp.getApp())
                    .load(item.getActor().getAvatar_url())
                    .into(itemEventBinding.avatorItemEvent);

            StringBuilder builder = new StringBuilder();
            String action = item.getPayload().getAction();
            if (action == null) {
                action = "created";
            }
            builder.append(item.getActor().getLogin())
                    .append(" ")
                    .append(action)
                    .append(" ")
                    .append(item.getRepo().getName());


            itemEventBinding.txtItemEvent.setText(builder.toString());

            RxView.clicks(itemEventBinding.cardItemEvent)
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {

                        }
                    });
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        mBinding.swipeRefreshFeed.setRefreshing(false);
    }
}
