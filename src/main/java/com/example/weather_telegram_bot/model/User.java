package com.example.weather_telegram_bot.model;

import com.example.weather_telegram_bot.bot.State;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true,nullable = false)
    private Long id;
    @Column(name = "chat_id",unique = true,nullable = false)
    @NotNull
    private Long chatId;
    @Column(name = "name",unique = true,nullable = false)
    @NotBlank
    private String name;
    @Column(name = "location",nullable = false)
    @NotBlank
    private String location;
    @Column(name = "bot_state",nullable = false)
    @NotBlank
    private State botState;

    public User(Long chatId){
        this.chatId = chatId;
        this.name = String.valueOf(chatId);
        this.location = "unknown";
        this.botState = State.START;
    }
}
