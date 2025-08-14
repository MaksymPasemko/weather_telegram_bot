package com.example.weather_telegram_bot.handler;


import com.example.weather_telegram_bot.bot.State;
import com.example.weather_telegram_bot.dto.WeatherResponse;
import com.example.weather_telegram_bot.model.User;
import com.example.weather_telegram_bot.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.Serializable;
import java.util.List;

import static com.example.weather_telegram_bot.bot.Button.WEATHER_FORECAST_OTHER_LOCATION;
import static com.example.weather_telegram_bot.bot.Button.WEATHER_FORECAST_YOUR_LOCATION;
import static com.example.weather_telegram_bot.bot.State.WEATHER_FORECAST;
import static com.example.weather_telegram_bot.util.TelegramUtil.createMessage;
import static com.example.weather_telegram_bot.util.WeatherResponseNormalizer.normalize;

@Service
@AllArgsConstructor
public class WeatherHandler implements Handler {
    private final WeatherService weatherService;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message) {
        if (message.equalsIgnoreCase(WEATHER_FORECAST_OTHER_LOCATION.getText())) {
            return otherLocationWeatherForecast(user);
        }
        return yourLocationWeatherForecast(user,message);
    }

    private List<PartialBotApiMethod<? extends Serializable>> yourLocationWeatherForecast(User user, String message) {
        final SendMessage sendMessage = createMessage(user);
        WeatherResponse weatherResponse = weatherService.getWeatherByCity(user.getLocation());
        if (!message.equalsIgnoreCase(WEATHER_FORECAST_YOUR_LOCATION.getText())) {
            weatherResponse = weatherService.getWeatherByCity(message);
        }
        final String weatherResponseNormalized = normalize(weatherResponse);
        sendMessage.setText(weatherResponseNormalized);


        return List.of(sendMessage);
    }

    private List<PartialBotApiMethod<? extends Serializable>> otherLocationWeatherForecast(User user) {
        final SendMessage sendMessage = createMessage(user);
        sendMessage.setText("Enter location in order to check weather:");
        return List.of(sendMessage);
    }


    @Override
    public State operatedBotState() {
        return WEATHER_FORECAST;
    }
}
