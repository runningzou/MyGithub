package com.runningzou.mygithub.trendingFragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.jakewharton.rxbinding2.view.RxView;
import com.runningzou.mygithub.R;
import com.runningzou.mygithub.databinding.FragmentTrendingBinding;
import com.runningzou.mygithub.databinding.ItemTrendingBinding;
import com.runningzou.mygithub.di.Injector;
import com.runningzou.mygithub.model.Trending;
import com.runningzou.mygithub.recyclerview.BaseAdapter;
import com.runningzou.mygithub.view.TrendingDialog;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;


/**
 * Created by zouzhihao on 2017/10/13.
 */

public class TrendingFragment extends Fragment implements Injector {

    public static final String TAG = TrendingFragment.class.getSimpleName();

    @Inject
    TrendingViewModel trendingViewModel;

    private TrendingAdapter mAdapter;

    private boolean init = true;

    public static Fragment newInstance() {
        Fragment fragment = new TrendingFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentTrendingBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trending, container, false);

        binding.recyclerviewTrending.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new TrendingAdapter();
        binding.recyclerviewTrending.setAdapter(mAdapter);

        trendingViewModel
                .trendingCommand()
                .errors()
                .subscribe(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

        trendingViewModel
                .trendingCommand()
                .executing()
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.d(TAG, "excuting:" + aBoolean);
                        if (aBoolean) {
                            binding.progressTrending.setVisibility(View.VISIBLE);
                        } else {
                            binding.progressTrending.setVisibility(View.GONE);
                        }
                    }
                });

        trendingViewModel
                .trendingCommand()
                .switchToLatest()
                .subscribe(new Consumer<List<Trending>>() {
                    @Override
                    public void accept(List<Trending> trendings) throws Exception {
                        Log.d(TAG, "result:" + trendings.size());
                        mAdapter.refresh(trendings);
                    }
                });

        trendingViewModel.trendingCommand().execute(null);

        RxView.clicks(binding.txtChoose).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Log.d(TAG, "click");
                TrendingDialog trendingDialog = new TrendingDialog();
                trendingDialog.show(getChildFragmentManager());
            }
        });

        binding.spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (init) {
                    init = false;
                    return;
                }
                trendingViewModel.setLanguage(position);
                Log.d(TAG, "languageItemSelect");
                trendingViewModel.trendingCommand().execute(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.d(TAG, "TimerItemSelect");
                if (init) {
                    init = false;
                    return;
                }
                trendingViewModel.setTime(position);
                trendingViewModel.trendingCommand().execute(null);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return binding.getRoot();
    }


    public class TrendingAdapter extends BaseAdapter<Trending> {

        @Override
        protected ViewDataBinding createBinding(ViewGroup parent, int viewType) {
            ItemTrendingBinding binding = ItemTrendingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return binding;
        }

        @Override
        protected void bind(ViewDataBinding binding, Trending item) {
            ItemTrendingBinding bind = (ItemTrendingBinding) binding;
            bind.setTrending(item);
        }

        @Override
        protected boolean areItemsTheSame(Trending oldItem, Trending newItem) {
            return oldItem == newItem;
        }

        @Override
        protected boolean areContentsTheSame(Trending oldItem, Trending newItem) {
            return oldItem.equals(newItem);
        }

    }

}
