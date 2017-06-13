package com.sample.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jia on 2017/6/12.
 */

public class Suggestion {

    @SerializedName("comf")
    public Comfort comfort;

    public Sport sport;

    @SerializedName("trav")
    public Travel travel;

    public Uv uv;

    public class Comfort{
        //简介
        public String brf;

        //详细描述
        @SerializedName("txt")
        public String description;
    }

    public class Sport{
        //简介
        public String brf;

        //详细描述
        @SerializedName("txt")
        public String description;
    }

    public class Travel{
        //简介
        public String brf;

        //详细描述
        @SerializedName("txt")
        public String description;
    }

    public class Uv{
        //简介
        public String brf;

        //详细描述
        @SerializedName("txt")
        public String description;
    }

}
