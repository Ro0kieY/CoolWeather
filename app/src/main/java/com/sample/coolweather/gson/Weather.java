package com.sample.coolweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jia on 2017/6/12.
 */

public class Weather {

    public String status;

    public Aqi aqi;

    public Basic basic;

    @SerializedName("daily_forecast")
    public List<DailyForecast> dailyForecastList;
    @SerializedName("hourly_forecast")
    public List<HourForecast> hourForecastList;

    public Now now;

    public Suggestion suggestion;

}
