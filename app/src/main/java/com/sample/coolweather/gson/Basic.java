package com.sample.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jia on 2017/6/12.
 */

public class Basic {

    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String cityId;

    @SerializedName("lat")
    public String cityLatitude;

    @SerializedName("lon")
    public String cityLongitude;

    public Update update;

    public class Update{

        //更新时间、当地时间
        @SerializedName("loc")
        public String updateTime;
    }

}
