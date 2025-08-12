package com.example.weather_telegram_bot.handler;

import com.example.weather_telegram_bot.bot.State;
import com.example.weather_telegram_bot.model.User;
import com.example.weather_telegram_bot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.Serializable;
import java.util.List;

import static com.example.weather_telegram_bot.bot.State.ENTER_NAME;
import static com.example.weather_telegram_bot.bot.State.START;
import static com.example.weather_telegram_bot.util.TelegramUtil.createMessage;

@Component
@RequiredArgsConstructor
public class StartHandler implements Handler{
    private final UserService userService;
    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message) {
        final SendMessage greetMessage = createMessage(user);
        final String greetText = "Your welcome to weather bot!%n" +
                "In order to begin you have to enter some details.%n" +
                "Please enter your name:";
        greetMessage.setText(String.format(greetText));

        user.setBotState(ENTER_NAME);
        userService.createOrUpdateUser(user);
        return List.of(greetMessage);
    }

    @Override
    public State operatedBotState() {
        return START;
    }
}

