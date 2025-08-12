package com.example.weather_telegram_bot.handler;

import com.example.weather_telegram_bot.bot.State;
import com.example.weather_telegram_bot.model.User;
import com.example.weather_telegram_bot.service.UserService;
import com.example.weather_telegram_bot.util.TelegramUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.List;

import static com.example.weather_telegram_bot.bot.Button.ACCEPT;
import static com.example.weather_telegram_bot.bot.Button.CHANGE_NAME;
import static com.example.weather_telegram_bot.bot.State.ENTER_LOCATION;
import static com.example.weather_telegram_bot.bot.State.ENTER_NAME;
import static com.example.weather_telegram_bot.util.TelegramUtil.createMessage;

@Component
@RequiredArgsConstructor
public class NameInputHandler implements Handler{
    private final UserService userService;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message) {
        if(message.equalsIgnoreCase(ACCEPT.getText())){
            return accept(user);
        } else if (message.equalsIgnoreCase(CHANGE_NAME.getText())) {
            return changeName(user);
        }
        return checkName(user,message);
    }

    private List<PartialBotApiMethod<? extends Serializable>> accept(User user){
        final SendMessage sendMessage = createMessage(user);
        sendMessage.setText(String.format("Your name saved as %s%nPlease enter your location:",user.getName()));

        user.setBotState(ENTER_LOCATION);
        userService.createOrUpdateUser(user);
        return List.of(sendMessage);
    }

    private List<PartialBotApiMethod<? extends Serializable>> changeName(User user){
        final SendMessage sendMessage = createMessage(user);
        sendMessage.setText("Please enter your name:");
        return List.of(sendMessage);
    }

    private List<PartialBotApiMethod<? extends Serializable>> checkName(User user,String message){
        user.setName(message);
        userService.createOrUpdateUser(user);

        final InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        final InlineKeyboardButton acceptButton =
                TelegramUtil.createInlineKeyboardButton(ACCEPT.getText(), ACCEPT);
        final InlineKeyboardButton changeNameButton =
                TelegramUtil.createInlineKeyboardButton(CHANGE_NAME.getText(), CHANGE_NAME);

        final List<InlineKeyboardButton> inlineKeyboardButtons = List.of(acceptButton,changeNameButton);
        inlineKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtons));

        final SendMessage sendMessage = createMessage(user);
        sendMessage.setText(String.format("Your current name is:%s",user.getName()));
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return List.of(sendMessage);
    }


    @Override
    public State operatedBotState() {
        return ENTER_NAME;
    }
}
