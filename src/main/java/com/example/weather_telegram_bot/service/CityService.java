package com.example.weather_telegram_bot.service;

import com.example.weather_telegram_bot.model.City;
import com.example.weather_telegram_bot.repository.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    public boolean isExistBy(String city){
        return cityRepository.existsByName(city);
    }
    public City findCity(String name){
        return cityRepository.findByName(name);
    }
}
