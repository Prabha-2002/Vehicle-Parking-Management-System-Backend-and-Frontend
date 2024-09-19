package com.example.parkingmanagement.serviceImpl;

import com.example.parkingmanagement.model.Booking;
import com.example.parkingmanagement.model.Payment;
import com.example.parkingmanagement.model.PrepaymentStatus;
import com.example.parkingmanagement.repo.BookingRepository;
import com.example.parkingmanagement.repo.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PaymentServiceImplTest {

	@InjectMocks
	private PaymentServiceImpl paymentService;

	@Mock
	private PaymentRepository paymentRepository;

	@Mock
	private BookingRepository bookingRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testDeletePayment() {
		Long paymentId = 1L;

		paymentService.deletePayment(paymentId);

		verify(paymentRepository, times(1)).deleteById(paymentId);
	}

	@Test
	void testProcessPaymentWithBookingAndPayment() {
		Booking booking = new Booking();
		Payment payment = new Payment();

		paymentService.processPayment(booking, payment);

		verify(paymentRepository, times(1)).save(payment);
	}

	@Test
	void testProcessPaymentOnly() {
		Payment payment = new Payment();

		paymentService.processPayment(payment);

		verify(paymentRepository, times(1)).save(payment);
	}

	@Test
	void testUpdatePrepaymentStatus() {
		Long bookingId = 1L;
		PrepaymentStatus newStatus = PrepaymentStatus.PAID;

		Booking booking = new Booking();
		booking.setId(bookingId);

		when(bookingRepository.findById(bookingId)).thenReturn(booking);

		paymentService.updatePrepaymentStatus(bookingId, newStatus);

		assertEquals(newStatus, booking.getPrepaymentStatus());
		verify(bookingRepository, times(1)).save(booking);
	}
}
