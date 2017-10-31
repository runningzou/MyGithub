package com.runningzou.mygithub.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by zouzhihao on 2017/10/13.
 */

@Data
public class Event {

    /**
     * id : 6711309842
     * type : WatchEvent
     * actor : {"id":8130553,"login":"sfsheng0322","display_login":"sfsheng0322","gravatar_id":"","url":"https://api.github.com/users/sfsheng0322","avatar_url":"https://avatars.githubusercontent.com/u/8130553?"}
     * repo : {"id":45717250,"name":"tensorflow/tensorflow","url":"https://api.github.com/repos/tensorflow/tensorflow"}
     * payload : {"action":"started"}
     * public : true
     * created_at : 2017-10-13T02:55:51Z
     * org : {"id":15658638,"login":"tensorflow","gravatar_id":"","url":"https://api.github.com/orgs/tensorflow","avatar_url":"https://avatars.githubusercontent.com/u/15658638?"}
     */

    private String id;
    private String type;
    private ActorBean actor;
    private RepoBean repo;
    private PayloadBean payload;
    @SerializedName("public")
    private boolean publicX;
    private String created_at;
    private OrgBean org;



    @Data
    public static class ActorBean {
        /**
         * id : 8130553
         * login : sfsheng0322
         * display_login : sfsheng0322
         * gravatar_id :
         * url : https://api.github.com/users/sfsheng0322
         * avatar_url : https://avatars.githubusercontent.com/u/8130553?
         */

        private int id;
        private String login;
        private String display_login;
        private String gravatar_id;
        private String url;
        private String avatar_url;

    }

    @Data
    public static class RepoBean {
        /**
         * id : 45717250
         * name : tensorflow/tensorflow
         * url : https://api.github.com/repos/tensorflow/tensorflow
         */

        private int id;
        private String name;
        private String url;

    }

    @Data
    public static class PayloadBean {
        /**
         * action : started
         */

        private String action;

    }

    @Data
    public static class OrgBean {
        /**
         * id : 15658638
         * login : tensorflow
         * gravatar_id :
         * url : https://api.github.com/orgs/tensorflow
         * avatar_url : https://avatars.githubusercontent.com/u/15658638?
         */

        private int id;
        private String login;
        private String gravatar_id;
        private String url;
        private String avatar_url;

    }
}
