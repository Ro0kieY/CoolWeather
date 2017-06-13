package com.sample.coolweather.gson;

/**
 * Created by Jia on 2017/6/12.
 */

public class Aqi {

    public City city;

    public class City{

        //空气质量指数
        public String aqi;
        //一氧化碳1小时平均值(ug/m³)
        public String co;
        //二氧化氮1小时平均值(ug/m³)
        public String no2;
        //臭氧1小时平均值(ug/m³)
        public String o3;
        //PM10 1小时平均值(ug/m³)
        public String pm10;
        //PM2.5 1小时平均值(ug/m³)
        public String pm25;
        //空气质量类别，共六个级别，分别：优，良，轻度污染，中度污染，重度污染，严重污染
        public String qlty;
        //二氧化硫1小时平均值(ug/m³)
        public String so2;
    }
}
