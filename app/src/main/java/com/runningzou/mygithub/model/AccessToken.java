package com.runningzou.mygithub.model;

import lombok.Data;

/**
 * Created by zouzhihao on 2017/10/11.
 */

@Data
public class AccessToken {
    public static final String BASIC_AUTH_TAG = "basic_auth";
    public static final String ID_TAG = "id";
    public static final String TOKEN_TAG = "access_token";
    public static final String USER_TAG = "user";
    private long id;
    private String token;
    private String basicAuth;
    private String user;
}
