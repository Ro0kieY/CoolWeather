package com.sample.coolweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sample.coolweather.gson.DailyForecast;
import com.sample.coolweather.gson.Weather;
import com.sample.coolweather.service.AutoUpdateService;
import com.sample.coolweather.util.HttpUtil;
import com.sample.coolweather.util.JsonUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    @BindView(R.id.image_view)
    ImageView imageView;
    @BindView(R.id.nav_button)
    Button navButton;
    @BindView(R.id.title_city)
    TextView titleCity;
    @BindView(R.id.title_update_time)
    TextView titleUpdateTime;
    @BindView(R.id.now_temperature)
    TextView nowTemperature;
    @BindView(R.id.now_weather)
    TextView nowWeather;
    @BindView(R.id.aqi_aqi)
    TextView aqiAqi;
    @BindView(R.id.aqi_pm25)
    TextView aqiPM25;
    @BindView(R.id.daily_forecast_layout)
    LinearLayout forecastLayout;
    @BindView(R.id.suggestion_comfort)
    TextView suggestionComfort;
    @BindView(R.id.suggestion_sport)
    TextView suggestionSport;
    @BindView(R.id.suggestion_travel)
    TextView suggestionTravel;
    @BindView(R.id.suggestion_uv)
    TextView suggestionUv;
    @BindView(R.id.weather_layout)
    LinearLayout weatherLayout;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String bingPic = prefs.getString("bingPic", null);
        if (bingPic != null) {
            Glide.with(WeatherActivity.this).load(bingPic).into(imageView);
        } else {
            loadBingPic();
        }
        String weatherString = prefs.getString("weather", null);
        final String weatherId = getIntent().getStringExtra("weather_id");
        Weather weather = JsonUtil.handleWeatherResponse(weatherString);
        if (weatherString != null && weatherId == weather.basic.cityId) {
            //有缓存时直接解析天气数据
            showWeatherInfo(weather);
        } else {
            //无缓存时到服务器查询天气信息
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(weatherId);
        }

        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(weatherId);
                loadBingPic();
            }
        });
    }


    /**
     * 加载必应每日一图
     */
    private void loadBingPic() {

        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bingPic", responseText);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(responseText).into(imageView);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });

    }


    /**
     * 根据天气id请求服务器查询天气信息
     *
     * @param weatherId
     */
    public void requestWeather(final String weatherId) {

        String url = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=99e51f8d8de34c548517199ac72820ca";
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = JsonUtil.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                            swipeRefreshLayout.setRefreshing(false);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    /**
     * 处理展示Weather实体类中的数据
     *
     * @param weather
     */
    private void showWeatherInfo(Weather weather) {

        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime.split(" ")[1];
        String degree = weather.now.Temperature + "℃";
        String weatherInfo = weather.now.condition.WeatherConditon;

        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        nowTemperature.setText(degree);
        nowWeather.setText(weatherInfo);

        forecastLayout.removeAllViews();
        try {
            for (DailyForecast dailyForecast : weather.dailyForecastList){
                    View view = LayoutInflater.from(this).inflate(R.layout.daily_forecast_item, forecastLayout, false);
                    TextView dailyForecastDate = (TextView)view.findViewById(R.id.daily_forecast_date);
                    TextView dailyForecastWeatherInfo = (TextView)view.findViewById(R.id.daily_forecast_weather_info);
                    TextView dailyForecastMinTmp = (TextView)view.findViewById(R.id.daily_forecast_min_tmp);
                    TextView dailyForecastMaxTmp = (TextView)view.findViewById(R.id.daily_forecast_max_tmp);
                    dailyForecastDate.setText(dailyForecast.date);
                    dailyForecastWeatherInfo.setText(dailyForecast.condition.weatherDay);
                    dailyForecastMinTmp.setText(dailyForecast.temperature.min);
                    dailyForecastMaxTmp.setText(dailyForecast.temperature.max);
                    forecastLayout.addView(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (weather.aqi != null) {
            aqiAqi.setText(weather.aqi.city.aqi);
            aqiPM25.setText(weather.aqi.city.pm25);
        }

        String comfort = "舒适度：" + weather.suggestion.comfort.description;
        String sport = "运动指数：" + weather.suggestion.sport.description;
        String travel = "旅行指数：" + weather.suggestion.travel.description;
        String uv = "紫外线指数：" + weather.suggestion.uv.description;
        suggestionComfort.setText(comfort);
        suggestionSport.setText(sport);
        suggestionTravel.setText(travel);
        suggestionUv.setText(uv);

        weatherLayout.setVisibility(View.VISIBLE);
        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);
    }


}
