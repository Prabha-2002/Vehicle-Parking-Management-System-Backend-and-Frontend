package com.example.parkingmanagement.model;

import java.time.LocalTime;

public class CalculateCostRequest {
	private LocalTime endTime;
	private LocalTime checkoutTime;

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public LocalTime getCheckoutTime() {
		return checkoutTime;
	}

	public void setCheckoutTime(LocalTime checkoutTime) {
		this.checkoutTime = checkoutTime;
	}

}
