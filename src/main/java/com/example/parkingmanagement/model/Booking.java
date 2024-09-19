package com.example.parkingmanagement.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	private String slotName;
	private String vehicleNumber;
	private String driverName;
	private String driverEmail;
	private String driverPhone;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private int numberOfHours;
	private double amount;
	@Enumerated(EnumType.STRING)
	private BookingStatus status;
	@Enumerated(EnumType.STRING)
	private PrepaymentStatus prepaymentStatus;
	private LocalTime arrivalTime;
	private LocalTime checkout;
	private Double newCost;
	private String newCostStatus;

	public Booking() {
		super();
	}

	public Booking(Long id, User user, String slotName, String vehicleNumber, String driverName, String driverEmail,
			String driverPhone, LocalDate date, LocalTime startTime, LocalTime endTime, int numberOfHours,
			double amount, BookingStatus status, PrepaymentStatus prepaymentStatus, LocalTime arrivalTime,
			LocalTime checkout, Double newCost, String newCostStatus) {
		super();
		this.id = id;
		this.user = user;
		this.slotName = slotName;
		this.vehicleNumber = vehicleNumber;
		this.driverName = driverName;
		this.driverEmail = driverEmail;
		this.driverPhone = driverPhone;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.numberOfHours = numberOfHours;
		this.amount = amount;
		this.status = status;
		this.prepaymentStatus = prepaymentStatus;
		this.arrivalTime = arrivalTime;
		this.checkout = checkout;
		this.newCost = newCost;
		this.newCostStatus = newCostStatus;

	}

	@Override
	public String toString() {
		return "Booking [id=" + id + ", user=" + user + ", slotName=" + slotName + ", vehicleNumber=" + vehicleNumber
				+ ", driverName=" + driverName + ", driverEmail=" + driverEmail + ", driverPhone=" + driverPhone
				+ ", date=" + date + ", startTime=" + startTime + ", endTime=" + endTime + ", numberOfHours="
				+ numberOfHours + ", amount=" + amount + ", status=" + status + ", prepaymentStatus=" + prepaymentStatus
				+ ", arrivalTime=" + arrivalTime + ", checkout=" + checkout + ", newCost=" + newCost + ", bufferTime="
				+ "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSlotName() {
		return slotName;
	}

	public void setSlotName(String slotName) {
		this.slotName = slotName;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverEmail() {
		return driverEmail;
	}

	public void setDriverEmail(String driverEmail) {
		this.driverEmail = driverEmail;
	}

	public String getDriverPhone() {
		return driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public int getNumberOfHours() {
		return numberOfHours;
	}

	public void setNumberOfHours(int numberOfHours) {
		this.numberOfHours = numberOfHours;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;
	}

	public PrepaymentStatus getPrepaymentStatus() {
		return prepaymentStatus;
	}

	public void setPrepaymentStatus(PrepaymentStatus prepaymentStatus) {
		this.prepaymentStatus = prepaymentStatus;
	}

	public LocalTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public LocalTime getCheckout() {
		return checkout;
	}

	public void setCheckout(LocalTime checkout) {
		this.checkout = checkout;
	}

	public Double getNewCost() {
		return newCost;
	}

	public void setNewCost(Double newCost) {
		this.newCost = newCost;
	}

	public boolean isPresent() {
		return false;
	}

	public Booking get() {
		return null;
	}

	public String getNewCostStatus() {
		return newCostStatus;
	}

	public void setNewCostStatus(String newCostStatus) {
		this.newCostStatus = newCostStatus;
	}

	public void setCheckoutTime(LocalTime checkoutLocalTime) {

	}

	private boolean notificationSent;

	public boolean isNotificationSent() {
		return notificationSent;
	}

	public void setNotificationSent(boolean notificationSent) {
		this.notificationSent = notificationSent;
	}

}
