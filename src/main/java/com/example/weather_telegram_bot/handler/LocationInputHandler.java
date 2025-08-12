package com.example.weather_telegram_bot.handler;

import com.example.weather_telegram_bot.bot.Button;
import com.example.weather_telegram_bot.bot.State;
import com.example.weather_telegram_bot.model.User;
import com.example.weather_telegram_bot.service.CityService;
import com.example.weather_telegram_bot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.List;

import static com.example.weather_telegram_bot.bot.Button.*;
import static com.example.weather_telegram_bot.bot.State.ENTER_LOCATION;
import static com.example.weather_telegram_bot.util.TelegramUtil.createInlineKeyboardButton;
import static com.example.weather_telegram_bot.util.TelegramUtil.createMessage;

@Component
@RequiredArgsConstructor
public class LocationInputHandler implements Handler{
    private final UserService userService;
    private final CityService cityService;
    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message) {
        if(message.equalsIgnoreCase(ACCEPT.getText())){
            return accept(user);
        } else if (message.equalsIgnoreCase(CHANGE_LOCATION.getText())) {
            return changeLocation(user);
        }
        return checkLocation(user,message);
    }
    private List<PartialBotApiMethod<? extends Serializable>> accept(User user){
        final SendMessage locationSavedMessage = createMessage(user);
        locationSavedMessage.setText(String.format("Your location is saved as %s",user.getLocation()));

        final SendMessage weatherMessage = createMessage(user);
        final InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        final InlineKeyboardButton weatherForecastButton =
                createInlineKeyboardButton(WEATHER_FORECAST.getText(),WEATHER_FORECAST );

        final List<InlineKeyboardButton> rawInlineKeyboardButton = List.of(weatherForecastButton);
        inlineKeyboardMarkup.setKeyboard(List.of(rawInlineKeyboardButton));

        weatherMessage.setReplyMarkup(inlineKeyboardMarkup);

        user.setBotState(State.WEATHER_FORECAST);
        userService.createOrUpdateUser(user);
        return List.of(locationSavedMessage,weatherMessage);
    }
    private List<PartialBotApiMethod<? extends Serializable>> changeLocation(User user){
        final SendMessage sendMessage = createMessage(user);
        sendMessage.setText("Please enter your location:");
        return List.of(sendMessage);
    }
    private List<PartialBotApiMethod<? extends Serializable>> checkLocation(User user,String message){
        user.setLocation(message);
        userService.createOrUpdateUser(user);
        final SendMessage sendMessage = createMessage(user);
        final String messageToSend = "There is no city like %s%nPlease enter location again:";
        if(!cityService.isExistBy(WordUtils.capitalizeFully(message))){
            sendMessage.setText(String.format(messageToSend,StringUtils.capitalize(message)));
            return List.of(sendMessage);
        }

        final InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        final InlineKeyboardButton acceptButton =
                createInlineKeyboardButton(ACCEPT.getText(),ACCEPT);
        final InlineKeyboardButton changeLocationButton =
                createInlineKeyboardButton(CHANGE_LOCATION.getText(),CHANGE_LOCATION);

        final List<InlineKeyboardButton> inlineKeyboardButtons = List.of(acceptButton,changeLocationButton);
        inlineKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtons));

        sendMessage.setText(String.format("Your current location is %s",user.getLocation()));
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return List.of(sendMessage);
    }

    @Override
    public State operatedBotState() {
        return ENTER_LOCATION;
    }
}
