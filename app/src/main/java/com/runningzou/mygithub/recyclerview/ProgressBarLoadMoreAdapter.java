package com.runningzou.mygithub.recyclerview;

import android.databinding.ViewDataBinding;
import android.view.ViewGroup;

import com.runningzou.mygithub.R;
import com.runningzou.mygithub.databinding.ItemFootBinding;

/**
 * Created by zouzhihao on 2017/10/16.
 */

public abstract class ProgressBarLoadMoreAdapter<T> extends LoadMoreAdapter<T> {

    public ProgressBarLoadMoreAdapter() {
        setLoadMoreStateChangeListener(new LoadMoreStateChangeListener() {
            @Override
            public void onStateChange(LoadMoreState state, ViewDataBinding binding) {
                ItemFootBinding bind = (ItemFootBinding) binding;

                    switch (state) {
                        case LOADING:
                            bind.txtLoadMore.setText("loading");
                            break;
                        case ERROR:
                            bind.txtLoadMore.setText("error");
                            break;
                        case COMPLETE:
                            bind.txtLoadMore.setText("complete");
                            break;
                        case END:
                            bind.txtLoadMore.setText("end");
                            break;
                    }


            }
        });
    }

    @Override
    public int footerLayout() {
        return R.layout.item_foot;
    }
}
