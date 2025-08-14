package com.example.weather_telegram_bot.service;

import com.example.weather_telegram_bot.configuration.OpenWeatherConfiguration;
import com.example.weather_telegram_bot.dto.WeatherResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class WeatherService {
    private final OpenWeatherConfiguration openWeatherConfiguration;
    private final RestTemplate restTemplate;
    private final CityService cityService;

    public WeatherResponse getWeatherByCity(String city){
        final String url = String.format("%s?q=%s,%s&units=metric&appid=%s"
                ,openWeatherConfiguration.getUrl()
                ,city
                ,cityService.findCity(StringUtils.capitalize(city)).getCountry()
                ,openWeatherConfiguration.getKey());

        return restTemplate.getForObject(url,WeatherResponse.class);
    }

}
