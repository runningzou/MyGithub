package com.runningzou.mygithub.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

/**
 * Created by zouzhihao on 2017/10/11.
 */

@Data
public class AuthModel {

    private List<String> scopes;
    private String note;
    private String client_id = "e4ab9a6a221346d92922";
    private String client_secret = "5f3986185ea0c866b97b7adec219df00fe7dec78";

}
