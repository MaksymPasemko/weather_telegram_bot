package com.example.weather_telegram_bot.service;

import com.example.weather_telegram_bot.model.User;
import com.example.weather_telegram_bot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> findByChatId(Long chatId){
        return userRepository.findByChatId(chatId);
    }

    public void createOrUpdateUser(User user) {
        userRepository.save(user);
    }
}
