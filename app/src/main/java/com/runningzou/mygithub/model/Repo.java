package com.runningzou.mygithub.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by runningzou on 2017/9/20.
 */

@Data
public class Repo {

    private int id;
    private String name;
    private String full_name;
    private RepoOwner owner;
    @SerializedName("private")
    private boolean privateX;
    private String html_url;
    private String description;
    private boolean fork;
    private String url;
    private String forks_url;
    private String keys_url;
    private String collaborators_url;
    private String teams_url;
    private String hooks_url;
    private String issue_events_url;
    private String events_url;
    private String assignees_url;
    private String branches_url;
    private String tags_url;
    private String blobs_url;
    private String git_tags_url;
    private String git_refs_url;
    private String trees_url;
    private String statuses_url;
    private String languages_url;
    private String stargazers_url;
    private String contributors_url;
    private String subscribers_url;
    private String subscription_url;
    private String commits_url;
    private String git_commits_url;
    private String comments_url;
    private String issue_comment_url;
    private String contents_url;
    private String compare_url;
    private String merges_url;
    private String archive_url;
    private String downloads_url;
    private String issues_url;
    private String pulls_url;
    private String milestones_url;
    private String notifications_url;
    private String labels_url;
    private String releases_url;
    private String deployments_url;
    private String created_at;
    private String updated_at;
    private String pushed_at;
    private String git_url;
    private String ssh_url;
    private String clone_url;
    private String svn_url;
    private Object homepage;
    private int size;
    private int stargazers_count;
    private int watchers_count;
    private String language;
    private boolean has_issues;
    private boolean has_projects;
    private boolean has_downloads;
    private boolean has_wiki;
    private boolean has_pages;
    private int forks_count;
    private Object mirror_url;
    private int open_issues_count;
    private int forks;
    private int open_issues;
    private int watchers;
    private String default_branch;
    private double score;
}
