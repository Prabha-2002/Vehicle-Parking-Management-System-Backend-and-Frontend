package com.example.parkingmanagement.model;

public class SlotAvailabilityDTO {
    private String slotId;
    private boolean isAvailable;

    public SlotAvailabilityDTO() {
		super();
	}

	public SlotAvailabilityDTO(String slotId, boolean isAvailable) {
        this.slotId = slotId;
        this.isAvailable = isAvailable;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
