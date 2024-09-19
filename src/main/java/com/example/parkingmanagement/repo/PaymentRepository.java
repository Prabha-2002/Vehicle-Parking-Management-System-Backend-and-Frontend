package com.example.parkingmanagement.repo;

import com.example.parkingmanagement.model.Payment;

public interface PaymentRepository {
    Payment save(Payment payment);

	Payment findById(Long paymentId);

	void deleteById(Long paymentId);
}
