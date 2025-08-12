package com.example.weather_telegram_bot.bot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Button {
    ACCEPT("Accept"),
    CHANGE_NAME("Change name"),
    CHANGE_LOCATION("Change location"),
    WEATHER_FORECAST("Weather forecast");

    private final String text;
}
