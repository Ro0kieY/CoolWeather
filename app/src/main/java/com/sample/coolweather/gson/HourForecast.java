package com.sample.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jia on 2017/6/12.
 */

public class HourForecast {

    //预报日期
    public String date;
    //相对湿度（%）
    public String hum;
    //降水概率
    @SerializedName("pop")
    public String percent;
    //气压
    @SerializedName("pres")
    public String pressure;
    //温度
    @SerializedName("tmp")
    public String temperature;

    public Wind wind;

    public class Wind{

        @SerializedName("deg")
        public String degree; //风向(360度)
        @SerializedName("dir")
        public String direction; //风向
        @SerializedName("sc")
        public String scale; //风力
        @SerializedName("spd")
        public String speed; //风速kmph
    }

}
