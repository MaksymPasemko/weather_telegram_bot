package com.example.weather_telegram_bot.dto;


import lombok.Getter;

import java.util.List;
@Getter
public class WeatherResponse  {
    private String name;
    private Sys sys;
    private int timezone;
    private Main main;
    private List<Weather> weather;
    private Wind wind;

    @Getter
    public static class Weather{
        private String description;
        private String icon;
    }

    @Getter
    public static class Main{
        private double temp;
        private double feels_like;
        private double temp_min;
        private double temp_max;
        private int pressure;
        private int humidity;
        private int sea_level;
        private int grnd_level;
    }

    @Getter
    public static class Wind{
        private double speed;
    }

    @Getter
    public static class Sys{
        private String country;
    }
}
