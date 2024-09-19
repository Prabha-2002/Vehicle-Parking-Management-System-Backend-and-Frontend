package com.example.parkingmanagement.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.parkingmanagement.model.Booking;
import com.example.parkingmanagement.model.Payment;
import com.example.parkingmanagement.model.PrepaymentStatus;
import com.example.parkingmanagement.repo.BookingRepository;
import com.example.parkingmanagement.repo.PaymentRepository;
import com.example.parkingmanagement.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private PaymentRepository paymentRepository;

	@Override
	public void deletePayment(Long paymentId) {
		paymentRepository.deleteById(paymentId);
	}

	@Override
	public void processPayment(Booking booking, Payment payment) {
		paymentRepository.save(payment);
	}

	@Override
	public void processPayment(Payment payment) {
		paymentRepository.save(payment);

	}

	@Override
	public void updatePrepaymentStatus(Long bookingId, PrepaymentStatus status) {
		Booking booking = bookingRepository.findById(bookingId);
		booking.setPrepaymentStatus(status);
		bookingRepository.save(booking);
	}
}
