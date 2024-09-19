package com.example.parkingmanagement.repo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.parkingmanagement.model.Sloting;

public interface SlotingRepository {
    List<Sloting> findByDateAndAvailable(LocalDate date, boolean available);

	Optional<Sloting> findById(Long slotId);

	void save(Sloting slot);

	Sloting findById1(Long slotId);

	boolean existsByName(String slotId);

	List<Sloting> findByNameAndDate(String slotId, LocalDate date);

	List<Sloting> findAll();

	boolean isSlotAvailable(String slotId, LocalDateTime startDateTime, LocalDateTime endDateTime);

	List<Sloting> getAvailableSlots(LocalDate date);
	
	
	

}
