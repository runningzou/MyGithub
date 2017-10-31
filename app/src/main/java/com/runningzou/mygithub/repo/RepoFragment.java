package com.runningzou.mygithub.repo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.runningzou.mygithub.R;
import com.runningzou.mygithub.databinding.FragmentRepoBinding;
import com.runningzou.mygithub.model.Repo;

/**
 * Created by zouzhihao on 2017/10/15.
 */

public class RepoFragment extends Fragment {

    public static Fragment newInstance() {
        Fragment fragment = new RepoFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentRepoBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_repo, container, false);
        return binding.getRoot();
    }
}
