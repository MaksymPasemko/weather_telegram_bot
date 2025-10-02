package com.example.weather_telegram_bot.handler;

import com.example.weather_telegram_bot.bot.State;
import com.example.weather_telegram_bot.model.User;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public interface Handler {
    List<PartialBotApiMethod<? extends Serializable>> handle(User user,String message);
    State operatedBotState();
}
