package com.sample.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jia on 2017/6/12.
 */

public class DailyForecast {

    //预报日期
    public String date;
    //相对湿度（%）
    public String hum;
    //降水量（mm）
    @SerializedName("pcpn")
    public String precipitation;
    //降水概率
    @SerializedName("pop")
    public String percent;
    //气压
    @SerializedName("pres")
    public String pressure;
    //能见度（km）
    @SerializedName("vis")
    public String visibility;

    @SerializedName("astro")
    public Astro astro;

    @SerializedName("cond")
    public Condition condition;

    @SerializedName("tmp")
    public Temperature temperature;

    //风力风向
    public Wind wind;

    public class Temperature{

        public String max;
        public String min;

    }

    public class Condition{

        //白天天气状况描述
        @SerializedName("txt_d")
        public String weatherDay;
        //晚上天气状况描述
        @SerializedName("txt_")
        public String weatherNight;


    }

    //天文数值
    public class Astro{

        //日出时间
        @SerializedName("sr")
        public String sunRise;
        //日落时间
        @SerializedName("ss")
        public String sunset;

    }

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
