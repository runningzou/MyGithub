package com.runningzou.mygithub.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.util.Log;
import android.view.View;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;
import com.runningzou.mygithub.MainActivity;
import com.runningzou.mygithub.R;
import com.runningzou.mygithub.databinding.ActivityLoginBinding;
import com.runningzou.mygithub.di.Injector;
import com.runningzou.mygithub.model.AccessToken;
import com.runningzou.mygithub.util.Live;
import com.runningzou.mygithub.util.RxCommandBinder;


import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by zouzhihao on 2017/10/10.
 */

public class LoginActivity extends AppCompatActivity implements Injector {

    public static final String TAG = LoginActivity.class.getSimpleName();

    @Inject
    LoginViewModel mLoginViewModel;

    private ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mLoginViewModel.isLogined()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        RxTextView.textChangeEvents(mBinding.editUsername).subscribe(new Consumer<TextViewTextChangeEvent>() {
            @Override
            public void accept(TextViewTextChangeEvent textViewTextChangeEvent) throws Exception {
                mLoginViewModel.userName.setValue(textViewTextChangeEvent.text().toString());
            }
        });

        RxTextView.textChangeEvents(mBinding.editPassword).subscribe(new Consumer<TextViewTextChangeEvent>() {
            @Override
            public void accept(TextViewTextChangeEvent textViewTextChangeEvent) throws Exception {
                mLoginViewModel.passWord.setValue(textViewTextChangeEvent.text().toString());
            }
        });

        RxCommandBinder.bind(mBinding.btnLogin, mLoginViewModel.loginCommand(), Live.<Boolean>bindLifecycle(this));

        mLoginViewModel.loginCommand().errors().subscribe(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.d(TAG, "errors:" + throwable.toString());
                Log.d(TAG,"登录错误");
            }
        });

        mLoginViewModel.loginCommand().executing().subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                Log.d(TAG,"excuting:" + aBoolean);
                if (aBoolean) {
                    mBinding.btnLogin.setVisibility(View.GONE);
                    mBinding.progressLogin.setVisibility(View.VISIBLE);
                } else {
                    mBinding.btnLogin.setVisibility(View.VISIBLE);
                    mBinding.progressLogin.setVisibility(View.GONE);
                }
            }
        });

        mLoginViewModel.loginCommand().switchToLatest().subscribe(new Consumer<AccessToken>() {
            @Override
            public void accept(AccessToken accessToken) throws Exception {
                Log.d(TAG,"success:" + accessToken.getToken());
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });
    }


}
