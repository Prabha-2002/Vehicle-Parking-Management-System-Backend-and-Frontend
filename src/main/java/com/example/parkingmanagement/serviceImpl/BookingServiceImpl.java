package com.example.parkingmanagement.serviceImpl;

import com.example.parkingmanagement.model.Booking;
import com.example.parkingmanagement.model.BookingStatus;
import com.example.parkingmanagement.model.PrepaymentStatus;
import com.example.parkingmanagement.repo.BookingRepository;
import com.example.parkingmanagement.service.BookingService;
import com.example.parkingmanagement.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private EmailService emailService;

	@Scheduled(cron = "0 */5 * * * *")
	public void checkForUpcomingBookings() {
		LocalDateTime now = LocalDateTime.now();
		List<Booking> bookings = bookingRepository.findAll();

		for (Booking booking : bookings) {
			LocalDateTime endDateTime = booking.getDate().atTime(booking.getEndTime());

			if (now.isAfter(endDateTime.minusMinutes(30)) && now.isBefore(endDateTime)
					&& !booking.isNotificationSent()) {
				sendNotification(booking);
				booking.setNotificationSent(true);
				bookingRepository.save(booking);
			}
		}
	}

	private void sendNotification(Booking booking) {
		String to = booking.getDriverEmail();
		String subject = "Reminder: Upcoming Booking End Time";
		String text = String.format(
				"Dear %s,\n\nThis is a reminder that your booking in slot %s is ending soon at %s.\n\nThank you.",
				booking.getDriverName(), booking.getSlotName(), booking.getEndTime().toString());

		emailService.sendNotificationEmail(to, subject, text);
	}

	@Override
	public List<String> getAvailableSlots(LocalDate date) {
		List<Booking> bookings = bookingRepository.findByDate(date);
		return bookings.stream().map(Booking::getSlotName).distinct().collect(Collectors.toList());
	}

	public LocalTime calculateBufferTime(LocalTime startTime) {
		return startTime.plusMinutes(10);
	}

	@Override
	public List<Booking> getPaidBookingsstatus() {
		return bookingRepository.findByStatus("paid");
	}

	@Override
	public List<Booking> getPaidBookings() {
		return bookingRepository.findAll();
	}

	@Override
	public Booking getBookingByIds(Long id) {
		return bookingRepository.findById(id);
	}

	@Override
	public Booking updateBooking(Booking booking) {
		return bookingRepository.save(booking);
	}

	@Override
	public double calculateNewCost(LocalTime endTime, LocalTime checkoutTime) {
		if (checkoutTime.isBefore(endTime)) {
			return 0;
		}

		long hours = Duration.between(endTime, checkoutTime).toHours();
		if (hours > 0) {
			return hours * 40;
		}
		return 0;
	}

	@Override
	public Booking updateBooking(Long id, Booking updatedBooking) {
		Booking existingBooking = bookingRepository.findById(id);

		if (existingBooking != null) {
			existingBooking.setSlotName(updatedBooking.getSlotName());
			existingBooking.setVehicleNumber(updatedBooking.getVehicleNumber());
			existingBooking.setDriverName(updatedBooking.getDriverName());
			existingBooking.setDriverEmail(updatedBooking.getDriverEmail());
			existingBooking.setDriverPhone(updatedBooking.getDriverPhone());
			existingBooking.setDate(updatedBooking.getDate());
			existingBooking.setStartTime(updatedBooking.getStartTime());
			existingBooking.setEndTime(updatedBooking.getEndTime());
			existingBooking.setNumberOfHours(updatedBooking.getNumberOfHours());
			existingBooking.setAmount(updatedBooking.getAmount());
			existingBooking.setStatus(updatedBooking.getStatus());
			existingBooking.setPrepaymentStatus(updatedBooking.getPrepaymentStatus());
			existingBooking.setArrivalTime(updatedBooking.getArrivalTime());
			existingBooking.setCheckout(updatedBooking.getCheckout());

			if (updatedBooking.getEndTime() != null && updatedBooking.getCheckout() != null) {
				double newCost = calculateNewCost(updatedBooking.getEndTime(), updatedBooking.getCheckout());
				existingBooking.setNewCost(newCost);
			}

			return bookingRepository.save(existingBooking);
		}

		return null;
	}

	@Override
	public Booking createBooking(Booking booking) {
		return bookingRepository.save(booking);
	}

	@Override
	public double getTotalEarnings() {
		return bookingRepository.findTotalEarnings();
	}

	@Override
	public Booking getBookingById(Long id) {
		return bookingRepository.findById(id);
	}

	@Override
	public List<Booking> getBookingsByUserId(Long userId) {
		return bookingRepository.findByUserId(userId);
	}

	@Override
	public List<Booking> getAllBookings() {
		return bookingRepository.findAll();
	}

	@Override
	public void updatePrepaymentStatus(Long bookingId, PrepaymentStatus status) {
		Booking booking = bookingRepository.findById(bookingId);
		if (booking != null) {
			booking.setPrepaymentStatus(status);
			bookingRepository.save(booking);
		}
	}

	@Override
	public Booking updateBookingStatus(Long id, BookingStatus status) {
		Booking booking = bookingRepository.findById(id);
		if (booking != null) {
			booking.setStatus(status);
			return bookingRepository.save(booking);
		}
		return null;
	}

	@Override
	public List<Booking> getBookingsByPrepaymentStatus(PrepaymentStatus paid) {
		return bookingRepository.findByPrepaymentStatus(paid);
	}

	@Override
	public Booking getBookingByUserId(Long userId) {
		return bookingRepository.findByUserId(userId).stream().findFirst().orElse(null);
	}

	@Override
	public void makeSlotAvailable(LocalDate date, LocalTime checkoutTime) {
		
	}
}
