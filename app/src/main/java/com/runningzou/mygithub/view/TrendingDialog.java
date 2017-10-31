package com.runningzou.mygithub.view;

import android.app.DialogFragment;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.runningzou.mygithub.R;
import com.runningzou.mygithub.databinding.ItemTrendingChooseBinding;
import com.runningzou.mygithub.databinding.TrendingDialogBinding;
import com.runningzou.mygithub.model.Trending;
import com.runningzou.mygithub.recyclerview.BaseAdapter;

import java.util.Arrays;

/**
 * Created by zouzhihao on 2017/10/14.
 */

public class TrendingDialog extends BaseNiceDialog {

    public static final String[] languages = new String[]{
            "c", "c++", "java", "javascript", "python", "shell", "objective-c", "php"
    };

    public static final String[] times = new String[]{
            "today", "this week", "this month"
    };

    private TrendingDialogAdapter mAdapter;

    @Override
    public int intLayoutId() {
        return R.layout.trending_dialog;
    }

    @Override
    public void initView(ViewDataBinding binding) {
        setMargin(60);
        setHeight(400);

        TrendingDialogBinding bind = (TrendingDialogBinding) binding;
        bind.recyclerviewDialog.setLayoutManager(new LinearLayoutManager(bind.getRoot().getContext()));
        mAdapter = new TrendingDialogAdapter();
        mAdapter.setItems(Arrays.asList(languages));
        bind.recyclerviewDialog.setAdapter(mAdapter);

    }


    public class TrendingDialogAdapter extends BaseAdapter<String> {

        @Override
        protected ViewDataBinding createBinding(ViewGroup parent, int viewType) {
            return ItemTrendingChooseBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        }

        @Override
        protected void bind(ViewDataBinding binding, String item) {
            ItemTrendingChooseBinding bind = (ItemTrendingChooseBinding) binding;
            bind.txtItemTrendingChoose.setText(item);
        }

        @Override
        protected boolean areItemsTheSame(String oldItem, String newItem) {
            return oldItem == newItem;
        }

        @Override
        protected boolean areContentsTheSame(String oldItem, String newItem) {
            return oldItem.equals(newItem);
        }
    }


}
