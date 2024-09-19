package com.example.parkingmanagement.model;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.example.parkingmanagement.repo.SlotingRepository;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class DataInitializer {

    @Bean
    CommandLineRunner init(SlotingRepository repository) {
        return args -> {
            LocalDate today = LocalDate.now();
            LocalDate tomorrow = today.plusDays(1);

            for (int i = 1; i <= 5; i++) {
                repository.save(new Sloting(null, today, LocalTime.of(9, 0), LocalTime.of(10, 0), true, "A" + i));
                repository.save(new Sloting(null, today, LocalTime.of(10, 0), LocalTime.of(11, 0), true, "B" + i));
            }

            for (int i = 1; i <= 5; i++) {
                repository.save(new Sloting(null, tomorrow, LocalTime.of(9, 0), LocalTime.of(10, 0), true, "A" + i));
                repository.save(new Sloting(null, tomorrow, LocalTime.of(10, 0), LocalTime.of(11, 0), true, "B" + i));
            }
        };
    }
}
