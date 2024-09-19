package com.example.parkingmanagement.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.example.parkingmanagement.model.Booking;
import com.example.parkingmanagement.model.BookingStatus;
import com.example.parkingmanagement.model.PrepaymentStatus;
import com.example.parkingmanagement.service.BookingService;
import com.example.parkingmanagement.service.EmailService;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {

	private BookingService bookingService;
	private EmailService emailService;

	

	public BookingController(BookingService bookingService, EmailService emailService) {
		super();
		this.bookingService = bookingService;
		this.emailService = emailService;
	}

	@PutMapping("/updateArrival/{id}")
	public Booking updateArrival(@PathVariable Long id, @RequestParam String arrivalTime) {
		Booking booking = bookingService.getBookingById(id);
		if (booking == null) {
			return null;
		}

		LocalTime arrivalLocalTime = LocalTime.parse(arrivalTime);
		booking.setArrivalTime(arrivalLocalTime);
		return bookingService.updateBooking(booking);
	}

	@PutMapping("/updateCheckout/{id}")
	public Booking updateCheckout(@PathVariable Long id, @RequestParam String checkout) {
		if (checkout == null || checkout.isEmpty()) {
			return null;
		}

		Booking booking = bookingService.getBookingById(id);
		if (booking == null) {
			return null;
		}

		LocalTime checkoutLocalTime = LocalTime.parse(checkout);
		booking.setCheckout(checkoutLocalTime);
		return bookingService.updateBooking(booking);
	}

	@PutMapping("/{id}/update-amount")
	public Booking updateAmount(@PathVariable Long id, @RequestParam double amount) {
		Booking booking = bookingService.getBookingById(id);
		if (booking == null) {
			return null;
		}

		booking.setAmount(amount);
		return bookingService.updateBooking(booking);
	}

	@PutMapping("/updateNewCost/{id}")
	public Booking updateNewCost(@PathVariable Long id, @RequestParam double newCost) {
		Booking booking = bookingService.getBookingById(id);
		if (booking == null) {
			return null;
		}

		booking.setNewCost(newCost);
		return bookingService.updateBooking(booking);
	}

	@PutMapping("/updateNewcostStatus/{id}")
	public Booking updateNewCostStatus(@PathVariable Long id, @RequestParam String newCostStatus) {
		Booking booking = bookingService.getBookingById(id);
		if (booking == null) {
			return null;
		}

		booking.setNewCostStatus(newCostStatus);
		return bookingService.updateBooking(booking);
	}

	@PostMapping("/processNewCostStatus/{id}")
	public Booking processNewCostStatus(@PathVariable Long id, @RequestParam String newCostStatus) {
		Booking booking = bookingService.getBookingById(id);
		if (booking == null) {
			return null;
		}

		booking.setNewCostStatus(newCostStatus);
		return bookingService.updateBooking(booking);
	}

	@GetMapping("/newCost/{userId}")
	public Map<String, Double> getNewCost(@PathVariable Long userId) {
		Booking booking = bookingService.getBookingByUserId(userId);
		if (booking == null) {
			return null;
		}

		return Collections.singletonMap("newCost", booking.getNewCost());
	}

	@PostMapping("/create")
	public Booking createBooking(@RequestBody Booking booking) {
		return bookingService.createBooking(booking);
	}

	@GetMapping("/{id}")
	public Booking getBookingById(@PathVariable Long id) {
		return bookingService.getBookingById(id);
	}

	@GetMapping
	public List<Booking> getAllBookings() {
		return bookingService.getAllBookings();
	}

	@GetMapping("/availableSlots")
	public List<String> getAvailableSlots(@RequestParam("date") LocalDate date) {
		return bookingService.getAvailableSlots(date);
	}

	@PostMapping("/{id}/approve")
	public Booking approveBooking(@PathVariable Long id) {
		Booking updatedBooking = bookingService.updateBookingStatus(id, BookingStatus.APPROVED);
		if (updatedBooking != null) {
			sendApprovalEmail(updatedBooking.getDriverEmail());
		}
		return updatedBooking;
	}

	@PostMapping("/{id}/cancel")
	public Booking cancelBooking(@PathVariable Long id) {
		Booking updatedBooking = bookingService.updateBookingStatus(id, BookingStatus.CANCELLED);
		if (updatedBooking != null) {
			sendRejectEmail(updatedBooking.getDriverEmail());
		}
		return updatedBooking;
	}

	@PutMapping("/{id}/update-prepayment-status")
	public String updatePrepaymentStatus(@PathVariable Long id, @RequestParam PrepaymentStatus status) {
		bookingService.updatePrepaymentStatus(id, status);
		return "Prepayment status updated successfully";
	}

	@GetMapping("/user/{userId}")
	public List<Booking> getBookingsByUserId(@PathVariable Long userId) {
		return bookingService.getBookingsByUserId(userId);
	}

	@GetMapping("/totalEarnings")
	public double getTotalEarnings() {
		return bookingService.getTotalEarnings();
	}

	@GetMapping("/paid")
	public List<Booking> getPaidBookings() {
		return bookingService.getBookingsByPrepaymentStatus(PrepaymentStatus.PAID);
	}

	private void sendApprovalEmail(String driverEmail) {
		String subject = " 🎉 Your Booking Request is Approved!";
		String body = "Dear Driver,\n\nWe are excited to inform you that your booking request has been approved! 🚀\r\n"
				+ "\r\n"
				+ "You can now proceed with the prepayment to secure your reservation. Thank you for choosing us! We appreciate your trust and look forward to serving you.\r\n"
				+ "\r\n" + "If you have any questions or need assistance, please don't hesitate to reach out.";
		try {
			emailService.sendEmail(driverEmail, subject, body);
		} catch (Exception e) {
			System.err.println("Failed to send approval email: " + e.getMessage());
		}
	}

	private void sendRejectEmail(String driverEmail) {
		String subject = "⚠️ Issue Sending Your Booking Approval";
		String body = "Dear Driver,\n\n Sorry to inform you that your booking request has been Rejected. Please reach out admin for further information.";
		try {
			emailService.sendEmail(driverEmail, subject, body);
		} catch (Exception e) {
			System.err.println("Failed to send reject email: " + e.getMessage());
		}
	}
}
