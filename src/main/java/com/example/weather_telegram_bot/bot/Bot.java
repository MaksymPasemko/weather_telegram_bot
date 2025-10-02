package com.example.weather_telegram_bot.bot;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.Serializable;
import java.util.List;

import static com.example.weather_telegram_bot.bot.State.*;

@Component
@RequiredArgsConstructor
@Getter
public class Bot extends TelegramLongPollingBot implements ApplicationRunner {
    @Value("${bot.name}")
    private String botUsername;
    @Value("${bot.token}")
    private String botToken;
    private final UpdateReceiver updateReceiver;


    @Override
    public void onUpdateReceived(Update update) {
        List<PartialBotApiMethod<? extends Serializable>> messages = updateReceiver.update(update);
        if (messages != null && !messages.isEmpty()){
            messages.forEach(
                    message ->
                    {
                        if (message instanceof SendMessage) {
                            executeWithExceptionCheck((SendMessage) message);
                        }
                    }
            );
        }
    }

    private void executeWithExceptionCheck(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onRegister() {
        final List<BotCommand> commands = List.of(
                new BotCommand(START.getText(),START.getText()),
                new BotCommand(ENTER_LOCATION.getText(),ENTER_LOCATION.getText()),
                new BotCommand(ENTER_NAME.getText(),ENTER_NAME.getText()),
                new BotCommand(WEATHER_FORECAST.getText(),WEATHER_FORECAST.getText())
        );
        try {
            execute(new SetMyCommands(commands,null,null));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    @PostConstruct
    public void init(){
        System.out.printf("Bot token: %s" + "%nBot name: %s%n",botToken,botUsername);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        final TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(this);
    }

}
