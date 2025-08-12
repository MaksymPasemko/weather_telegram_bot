package com.example.weather_telegram_bot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "cities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true,nullable = false)
    private Long id;
    @Column(name = "name",nullable = false)
    @NotBlank
    private String name;
    @Column(name = "state",nullable = false)
    @NotBlank
    private String state;
    @Column(name = "country",nullable = false)
    @NotBlank
    private String country;

}
