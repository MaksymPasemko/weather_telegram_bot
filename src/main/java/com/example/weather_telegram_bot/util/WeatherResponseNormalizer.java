package com.example.weather_telegram_bot.util;

import com.example.weather_telegram_bot.dto.WeatherResponse;
import com.example.weather_telegram_bot.dto.WeatherResponse.Main;
import com.example.weather_telegram_bot.dto.WeatherResponse.Sys;
import com.example.weather_telegram_bot.dto.WeatherResponse.Weather;
import com.example.weather_telegram_bot.dto.WeatherResponse.Wind;

import java.util.List;

public class WeatherResponseNormalizer {
    public static String normalize(WeatherResponse weatherResponse){
        final Sys sys = weatherResponse.getSys();
        final Main main = weatherResponse.getMain();
        final Weather weather = weatherResponse.getWeather().getFirst();
        final Wind wind = weatherResponse.getWind();
        return String.format("City name: %s%n" +
                        "Country: %s%n" +
                        "Timezone: %s%n" +
                        "Temperature: %s%n" +
                        "Feels like: %s%n" +
                        "Minimum temperature: %s%n" +
                        "Maximum temperature: %s%n" +
                        "Pressure: %s%n" +
                        "Humidity: %s%n" +
                        "Sea level: %s%n" +
                        "Ground level: %s%n" +
                        "Description: %s%n" +
                        "Icon: %s%n" +
                        "Wind speed: %s%n" ,
                weatherResponse.getName(),
                sys.getCountry(),
                weatherResponse.getTimezone(),
                main.getTemp(),
                main.getFeels_like(),
                main.getTemp_min(),
                main.getTemp_max(),
                main.getPressure(),
                main.getHumidity(),
                main.getSea_level(),
                main.getGrnd_level(),
                weather.getDescription(),
                weather.getIcon(),
                wind.getSpeed());
    }
}
