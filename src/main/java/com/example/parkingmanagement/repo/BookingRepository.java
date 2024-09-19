package com.example.parkingmanagement.repo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.example.parkingmanagement.model.Booking;
import com.example.parkingmanagement.model.BookingStatus;
import com.example.parkingmanagement.model.PrepaymentStatus;
import com.example.parkingmanagement.model.Sloting;

public interface BookingRepository {

	List<Booking> findBookingsBySlotAndDate(String slot, LocalDateTime startDateTime, LocalDateTime endDateTime);

	Booking save(Booking booking);

	Booking findById(Long id);

	List<Booking> findAll();

	List<Booking> findByStatus(String status);

	List<Booking> findByStatus(BookingStatus status);

	List<Booking> findByUserId(Long userId);

	Optional<Booking> findBySlotNameAndDateAndPrepaymentStatus(String slotName, LocalDateTime date,
			PrepaymentStatus prepaymentStatus);

	double findTotalEarnings();

	List<Booking> findByPrepaymentStatus(PrepaymentStatus paid);

	List<Sloting> findBookedSlots(LocalDate date);

	List<Booking> findByDate(@Param("date") LocalDate date);

}
