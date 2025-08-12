package com.example.weather_telegram_bot.handler;


import com.example.weather_telegram_bot.bot.State;
import com.example.weather_telegram_bot.dto.WeatherResponse;
import com.example.weather_telegram_bot.model.User;
import com.example.weather_telegram_bot.service.WeatherService;
import com.example.weather_telegram_bot.util.WeatherResponseNormalizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.Serializable;
import java.util.List;

import static com.example.weather_telegram_bot.bot.State.WEATHER_FORECAST;
import static com.example.weather_telegram_bot.util.TelegramUtil.createMessage;
import static com.example.weather_telegram_bot.util.WeatherResponseNormalizer.normalize;

@Service
@AllArgsConstructor
public class WeatherHandler implements Handler {
    private final WeatherService weatherService;
    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message)  {
        final WeatherResponse weatherResponse = weatherService.getWeatherByCity(user.getLocation());
        final String weatherResponseNormalized = normalize(weatherResponse);

        final SendMessage sendMessage = createMessage(user);
        sendMessage.setText(weatherResponseNormalized);

        return List.of(sendMessage);
    }

    @Override
    public State operatedBotState() {
        return WEATHER_FORECAST;
    }
}
