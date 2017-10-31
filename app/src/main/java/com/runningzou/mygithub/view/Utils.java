package com.runningzou.mygithub.view;

import android.util.DisplayMetrics;

import com.runningzou.mygithub.GitHubApp;

public class Utils {
    public static int dp2px(float dipValue) {
        final float scale = GitHubApp.getApp().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int getScreenWidth() {
        DisplayMetrics displayMetrics = GitHubApp.getApp().getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }
}