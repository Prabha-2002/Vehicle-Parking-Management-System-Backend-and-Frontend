package com.example.parkingmanagement.controller;

import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.*;

import com.example.parkingmanagement.model.Booking;
import com.example.parkingmanagement.model.Payment;
import com.example.parkingmanagement.repo.BookingRepository;
import com.example.parkingmanagement.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    
    private PaymentService paymentService;

    
    private BookingRepository bookingRepository;


    public PaymentController(PaymentService paymentService, BookingRepository bookingRepository) {
		super();
		this.paymentService = paymentService;
		this.bookingRepository = bookingRepository;
	}

	@PostMapping("/process")
    public String processPayment(@RequestBody Payment payment) {
        Booking booking = bookingRepository.findById(payment.getBooking().getId());
        payment.setAmount(booking.getAmount());
        payment.setPaymentDate(LocalDateTime.now());

        paymentService.processPayment(booking, payment);
        return "Payment processed successfully.";
    }

    @DeleteMapping("/{paymentId}")
    public String deletePayment(@PathVariable Long paymentId) {
        paymentService.deletePayment(paymentId);
        return "Payment deleted successfully.";
    }
}
