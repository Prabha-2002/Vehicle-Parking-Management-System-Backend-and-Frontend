package com.example.parkingmanagement.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.example.parkingmanagement.model.Booking;
import com.example.parkingmanagement.model.BookingStatus;
import com.example.parkingmanagement.model.PrepaymentStatus;

public interface BookingService {
	Booking createBooking(Booking booking);

	Booking getBookingById(Long id);

	List<Booking> getAllBookings();

	double getTotalEarnings();

	void updatePrepaymentStatus(Long bookingId, PrepaymentStatus status);

	Booking updateBookingStatus(Long id, BookingStatus status);

	List<Booking> getBookingsByUserId(Long userId);

	List<Booking> getBookingsByPrepaymentStatus(PrepaymentStatus paid);

	LocalTime calculateBufferTime(LocalTime startTime);

	List<Booking> getPaidBookings();

	Booking updateBooking(Booking booking);

	double calculateNewCost(LocalTime endTime, LocalTime checkoutTime);

	Booking getBookingByIds(Long id);

	List<Booking> getPaidBookingsstatus();

	Booking updateBooking(Long id, Booking updatedBooking);

	List<String> getAvailableSlots(LocalDate date);

	Booking getBookingByUserId(Long userId);

	void makeSlotAvailable(LocalDate date, LocalTime checkoutTime);

}