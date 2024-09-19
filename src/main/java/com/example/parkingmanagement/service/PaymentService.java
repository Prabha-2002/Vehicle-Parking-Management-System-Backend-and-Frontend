package com.example.parkingmanagement.service;

import com.example.parkingmanagement.model.Booking;
import com.example.parkingmanagement.model.Payment;
import com.example.parkingmanagement.model.PrepaymentStatus;

public interface PaymentService {
	void processPayment(Booking booking, Payment payment);

	void processPayment(Payment payment);

	void updatePrepaymentStatus(Long bookingId, PrepaymentStatus status);

	void deletePayment(Long paymentId);
}
