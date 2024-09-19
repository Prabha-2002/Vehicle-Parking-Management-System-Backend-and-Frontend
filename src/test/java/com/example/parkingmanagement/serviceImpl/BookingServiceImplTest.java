package com.example.parkingmanagement.serviceImpl;

import com.example.parkingmanagement.model.Booking;
import com.example.parkingmanagement.model.BookingStatus;
import com.example.parkingmanagement.model.PrepaymentStatus;
import com.example.parkingmanagement.repo.BookingRepository;
import com.example.parkingmanagement.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookingServiceImplTest {

	@InjectMocks
	private BookingServiceImpl bookingService;

	@Mock
	private BookingRepository bookingRepository;

	@Mock
	private EmailService emailService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAvailableSlots() {
		LocalDate date = LocalDate.now();
		Booking booking1 = new Booking();
		booking1.setSlotName("Slot 1");
		Booking booking2 = new Booking();
		booking2.setSlotName("Slot 2");

		when(bookingRepository.findByDate(date)).thenReturn(Arrays.asList(booking1, booking2));

		List<String> availableSlots = bookingService.getAvailableSlots(date);
		assertEquals(2, availableSlots.size());
		assertTrue(availableSlots.contains("Slot 1"));
		assertTrue(availableSlots.contains("Slot 2"));
	}

	@Test
	void testCalculateNewCost() {
		LocalTime endTime = LocalTime.of(10, 0);
		LocalTime checkoutTime = LocalTime.of(12, 0);
		double cost = bookingService.calculateNewCost(endTime, checkoutTime);
		assertEquals(80.0, cost); // 2 hours * 40
	}

	@Test
	void testUpdateBooking() {
		Booking existingBooking = new Booking();
		existingBooking.setId(1L);
		existingBooking.setSlotName("Old Slot");

		Booking updatedBooking = new Booking();
		updatedBooking.setSlotName("New Slot");

		when(bookingRepository.findById(1L)).thenReturn(existingBooking);
		when(bookingRepository.save(any(Booking.class))).thenReturn(existingBooking);

		Booking result = bookingService.updateBooking(1L, updatedBooking);
		assertEquals("New Slot", result.getSlotName());
		verify(bookingRepository).save(existingBooking);
	}

	@Test
	void testGetBookingByIds() {
		Booking booking = new Booking();
		booking.setId(1L);

		when(bookingRepository.findById(1L)).thenReturn(booking);

		Booking result = bookingService.getBookingByIds(1L);
		assertNotNull(result);
		assertEquals(1L, result.getId());
	}

	@Test
	void testUpdateBookingStatus() {
		Booking booking = new Booking();
		booking.setId(1L);

		when(bookingRepository.findById(1L)).thenReturn(booking);
		when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

		Booking updatedBooking = bookingService.updateBookingStatus(1L, BookingStatus.APPROVED);
		assertEquals(BookingStatus.APPROVED, updatedBooking.getStatus());
		verify(bookingRepository).save(booking);
	}

	@Test
	void testCheckForUpcomingBookings() {
		Booking booking = new Booking();
		booking.setDriverEmail("test@example.com");
		booking.setDriverName("Test Driver");
		booking.setSlotName("Slot 1");
		booking.setEndTime(LocalTime.now().plusMinutes(20));
		booking.setDate(LocalDate.now());
		booking.setNotificationSent(false);

		when(bookingRepository.findAll()).thenReturn(Collections.singletonList(booking));

		bookingService.checkForUpcomingBookings();

		verify(emailService).sendNotificationEmail(anyString(), anyString(), anyString());
		assertTrue(booking.isNotificationSent());
	}

	@Test
	void testGetPaidBookingsstatus() {
		Booking booking1 = new Booking();
		booking1.setStatus(BookingStatus.APPROVED);
		Booking booking2 = new Booking();
		booking2.setStatus(BookingStatus.APPROVED);

		when(bookingRepository.findByStatus("paid")).thenReturn(Arrays.asList(booking1, booking2));

		List<Booking> result = bookingService.getPaidBookingsstatus();
		assertEquals(2, result.size());
		assertTrue(result.stream().allMatch(b -> b.getStatus() == BookingStatus.APPROVED));
	}

	@Test
	void testGetPaidBookings() {
		Booking booking1 = new Booking();
		booking1.setStatus(BookingStatus.APPROVED);
		Booking booking2 = new Booking();
		booking2.setStatus(BookingStatus.APPROVED);

		when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking1, booking2));

		List<Booking> result = bookingService.getPaidBookings();
		assertEquals(2, result.size());
	}

	@Test
	void testUpdatePrepaymentStatus() {
		Booking booking = new Booking();
		booking.setId(1L);

		when(bookingRepository.findById(1L)).thenReturn(booking);
		when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

		bookingService.updatePrepaymentStatus(1L, PrepaymentStatus.PAID);
		assertEquals(PrepaymentStatus.PAID, booking.getPrepaymentStatus());
		verify(bookingRepository).save(booking);
	}

	@Test
	void testGetBookingsByUserId() {
		Long userId = 1L;
		Booking booking1 = new Booking();
		Booking booking2 = new Booking();

		when(bookingRepository.findByUserId(userId)).thenReturn(Arrays.asList(booking1, booking2));

		List<Booking> bookings = bookingService.getBookingsByUserId(userId);
		assertEquals(2, bookings.size());
	}

	@Test
	void testGetAllBookings() {
		Booking booking1 = new Booking();
		Booking booking2 = new Booking();

		when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking1, booking2));

		List<Booking> bookings = bookingService.getAllBookings();
		assertEquals(2, bookings.size());
	}

	@Test
	void testGetTotalEarnings() {
		double totalEarnings = 1000.0;

		when(bookingRepository.findTotalEarnings()).thenReturn(totalEarnings);

		double result = bookingService.getTotalEarnings();
		assertEquals(totalEarnings, result);
	}

	@Test
	void testGetBookingByUserId() {
		Long userId = 1L;
		Booking booking = new Booking();
		booking.setId(1L);

		when(bookingRepository.findByUserId(userId)).thenReturn(Collections.singletonList(booking));

		Booking result = bookingService.getBookingByUserId(userId);
		assertNotNull(result);
		assertEquals(1L, result.getId());
	}

	@Test
	void testMakeSlotAvailable() {
		assertDoesNotThrow(() -> bookingService.makeSlotAvailable(LocalDate.now(), LocalTime.now()));
	}

}
