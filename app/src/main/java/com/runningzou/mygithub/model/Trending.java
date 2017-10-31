package com.runningzou.mygithub.model;

import lombok.Data;

/**
 * Created by zouzhihao on 2017/10/14.
 */

@Data
public class Trending {
    public String title;
    public String description;
    public String language;
    public String starts;
    public String forks;
    public String today_Starts;
}
