package com.example.weather_telegram_bot.bot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum State {
    START("/start"),
    ENTER_NAME("/enter_name"),
    ENTER_LOCATION("/enter_location"),
    WEATHER_FORECAST("/weather_forecast"),
    IDLE("/idle");

    private final String text;
}
