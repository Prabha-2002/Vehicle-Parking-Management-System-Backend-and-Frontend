package com.example.parkingmanagement.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.example.parkingmanagement.model.Sloting;

public interface SlotingService {

	List<Sloting> getAvailableSlots(LocalDate date);

	boolean bookSlot(Long slotId);

	boolean checkSlotAvailability(Long slotId, LocalDate date, LocalTime startTime, LocalTime endTime);

	boolean bookSlot(String slotId, LocalDate date, LocalTime startTime, LocalTime endTime);

	boolean checkSlotAvailability(String slotId, LocalDate date, LocalTime startTime, LocalTime endTime);

}
