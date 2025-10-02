package com.example.weather_telegram_bot.bot;

import com.example.weather_telegram_bot.handler.*;
import com.example.weather_telegram_bot.model.User;
import com.example.weather_telegram_bot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdateReceiver {
    private final UserService userService;
    private final List<Handler> handlers;
    private final StartHandler startHandler;
    private final NameInputHandler nameInputHandler;
    private final LocationInputHandler locationInputHandler;
    private final WeatherHandler weatherHandler;

    public List<PartialBotApiMethod<? extends Serializable>> update(Update update) {
        final Long chatId;
        final String message;
        final User user;
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                message = update.getMessage().getText();
                chatId = update.getMessage().getChatId();
            } else if (update.hasCallbackQuery()) {
                message = update.getCallbackQuery().getData();
                chatId = update.getCallbackQuery().getMessage().getChatId();
            } else {
                throw new UnsupportedOperationException();
            }
            user = userService.findByChatId(chatId).orElse(new User(chatId));

            if (message.startsWith("/")) {
                return handlersBasedOnCommand(user,message,message);
            }
            return getHandlerByState(user.getBotState()).handle(user, message);
        } catch (UnsupportedOperationException e) {
            return Collections.emptyList();
        }
    }

    private List<PartialBotApiMethod<? extends Serializable>> handlersBasedOnCommand(User user, String message
            , String command) {
        return switch (command) {
            case "/start" -> startHandler.handle(user, message);
            case "/enter_name" -> nameInputHandler.handle(user, message);
            case "/enter_location" -> locationInputHandler.handle(user, message);
            default -> weatherHandler.handle(user, message);
        };
    }

    private Handler getHandlerByState(State state) {
        return handlers.stream()
                .filter(handler -> handler.operatedBotState() != null)
                .filter(handler -> handler.operatedBotState().equals(state))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }
}
