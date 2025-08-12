package com.example.weather_telegram_bot.repository;

import com.example.weather_telegram_bot.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City,Long> {
    boolean existsByName(String name);


    City findByName(String name);
}
