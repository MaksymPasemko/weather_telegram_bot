package com.example.weather_telegram_bot.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "openweather.api")
@Getter
@Setter
public class OpenWeatherConfiguration {
    private String key;
    private String url;
}
