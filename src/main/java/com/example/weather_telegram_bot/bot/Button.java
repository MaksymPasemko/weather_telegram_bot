package com.example.weather_telegram_bot.bot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Button {
    ACCEPT("Accept"),
    CHANGE_NAME("Change name"),
    CHANGE_LOCATION("Change location"),
    WEATHER_FORECAST_YOUR_LOCATION("Weather forecast for your location"),
    WEATHER_FORECAST_OTHER_LOCATION("Weather forecast for other location");

    private final String text;
}
