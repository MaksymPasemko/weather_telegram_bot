package com.example.weather_telegram_bot.util;

import com.example.weather_telegram_bot.bot.Button;
import com.example.weather_telegram_bot.model.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class TelegramUtil {
    public static SendMessage createMessage(User user){
        return createMessage(user.getChatId());
    }

    private static SendMessage createMessage(Long chatId){
        final SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }

    public static InlineKeyboardButton createInlineKeyboardButton(String text, Button button){
        final InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(text);
        inlineKeyboardButton.setCallbackData(button.getText());
        return inlineKeyboardButton;
    }
}
