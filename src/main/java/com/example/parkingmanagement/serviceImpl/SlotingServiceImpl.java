package com.example.parkingmanagement.serviceImpl;

import com.example.parkingmanagement.model.Sloting;
import com.example.parkingmanagement.repo.SlotingRepository;
import com.example.parkingmanagement.service.SlotingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class SlotingServiceImpl implements SlotingService {

	@Autowired
	private SlotingRepository slotingRepository;

	public List<Sloting> getAvailableSlots(LocalDate date) {
		return slotingRepository.findByDateAndAvailable(date, true);
	}

	@Override
	public boolean checkSlotAvailability(String slotId, LocalDate date, LocalTime startTime, LocalTime endTime) {
		boolean slotExists = slotingRepository.existsByName(slotId);
		if (!slotExists) {
			return true;
		}

		List<Sloting> bookings = slotingRepository.findByNameAndDate(slotId, date);
		for (Sloting booking : bookings) {
			LocalTime existingStartTime = booking.getStartTime();
			LocalTime existingEndTime = booking.getEndTime();
			if (startTime.isBefore(existingEndTime) && endTime.isAfter(existingStartTime)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean bookSlot(String slotId, LocalDate date, LocalTime startTime, LocalTime endTime) {
		if (!checkSlotAvailability(slotId, date, startTime, endTime)) {
			return false;
		}

		Sloting sloting = new Sloting();
		sloting.setName(slotId);
		sloting.setDate(date);
		sloting.setStartTime(startTime);
		sloting.setEndTime(endTime);
		sloting.setAvailable(false);

		slotingRepository.save(sloting);
		return true;
	}

	@Override
	public boolean bookSlot(Long slotId) {
		Optional<Sloting> optionalSlot = slotingRepository.findById(slotId);
		if (optionalSlot.isEmpty()) {
			return false;
		}

		Sloting slot = optionalSlot.get();
		LocalDate date = LocalDate.now();
		LocalTime startTime = LocalTime.now();
		LocalTime endTime = startTime.plusHours(1);

		return bookSlot(slot.getName(), date, startTime, endTime);
	}

	@Override
	public boolean checkSlotAvailability(Long slotId, LocalDate date, LocalTime startTime, LocalTime endTime) {
		Optional<Sloting> optionalSlot = slotingRepository.findById(slotId);
		if (optionalSlot.isEmpty()) {
			return false;
		}

		Sloting slot = optionalSlot.get();
		return checkSlotAvailability(slot.getName(), date, startTime, endTime);
	}

}
