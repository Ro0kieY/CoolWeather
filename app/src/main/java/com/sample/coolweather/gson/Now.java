package com.sample.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jia on 2017/6/12.
 */

public class Now {

    //天气状况
    @SerializedName("cond")
    public Condition condition;

    //体感温度
    @SerializedName("fl")
    public String feelTem;
    //相对湿度（%）
    public String hum;
    //降水量（mm）
    @SerializedName("pcpn")
    public String precipitation;
    //气压
    @SerializedName("pres")
    public String pressure;
    //温度
    @SerializedName("tmp")
    public String Temperature;
    //能见度（km）
    @SerializedName("vis")
    public String visibility;

    //风力风向
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

    public class Condition{

        @SerializedName("txt")
        public String WeatherConditon;
    }

}
