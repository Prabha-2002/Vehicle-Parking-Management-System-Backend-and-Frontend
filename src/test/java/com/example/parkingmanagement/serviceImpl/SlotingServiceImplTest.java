package com.example.parkingmanagement.serviceImpl;

import com.example.parkingmanagement.model.Sloting;
import com.example.parkingmanagement.repo.SlotingRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SlotingServiceImplTest {

    @InjectMocks
    private SlotingServiceImpl slotingService;

    @Mock
    private SlotingRepository slotingRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAvailableSlots() {
        LocalDate date = LocalDate.now();
        Sloting slot1 = new Sloting();
        slot1.setName("Slot 1");
        slot1.setAvailable(true);
        
        Sloting slot2 = new Sloting();
        slot2.setName("Slot 2");
        slot2.setAvailable(true);
        
        when(slotingRepository.findByDateAndAvailable(date, true)).thenReturn(Arrays.asList(slot1, slot2));

        List<Sloting> availableSlots = slotingService.getAvailableSlots(date);
        assertEquals(2, availableSlots.size());
    }

    @Test
    void testCheckSlotAvailabilityWhenSlotDoesNotExist() {
        String slotId = "Slot 1";
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.now();
        LocalTime endTime = startTime.plusHours(1);
        
        when(slotingRepository.existsByName(slotId)).thenReturn(false);

        boolean isAvailable = slotingService.checkSlotAvailability(slotId, date, startTime, endTime);
        assertTrue(isAvailable);
    }

    @Test
    void testCheckSlotAvailabilityWhenSlotIsBooked() {
        String slotId = "Slot 1";
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.now();
        LocalTime endTime = startTime.plusHours(2);
        
        Sloting booking = new Sloting();
        booking.setStartTime(startTime.plusHours(1));
        booking.setEndTime(startTime.plusHours(3));

        when(slotingRepository.existsByName(slotId)).thenReturn(true);
        when(slotingRepository.findByNameAndDate(slotId, date)).thenReturn(Collections.singletonList(booking));

        boolean isAvailable = slotingService.checkSlotAvailability(slotId, date, startTime, endTime);
        assertFalse(isAvailable);
    }

    @Test
    void testBookSlotSuccessfully() {
        String slotId = "Slot 1";
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.now();
        LocalTime endTime = startTime.plusHours(1);
        
        when(slotingRepository.existsByName(slotId)).thenReturn(true);
        when(slotingRepository.findByNameAndDate(slotId, date)).thenReturn(Collections.emptyList());

        boolean result = slotingService.bookSlot(slotId, date, startTime, endTime);
        assertTrue(result);
        verify(slotingRepository, times(1)).save(any(Sloting.class));
    }

    @Test
    void testBookSlotWhenNotAvailable() {
        String slotId = "Slot 1";
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.now();
        LocalTime endTime = startTime.plusHours(1);
        
        when(slotingRepository.existsByName(slotId)).thenReturn(true);
        Sloting existingBooking = new Sloting();
        existingBooking.setStartTime(startTime);
        existingBooking.setEndTime(endTime);
        when(slotingRepository.findByNameAndDate(slotId, date)).thenReturn(Collections.singletonList(existingBooking));

        boolean result = slotingService.bookSlot(slotId, date, startTime, endTime);
        assertFalse(result);
        verify(slotingRepository, never()).save(any(Sloting.class));
    }

    @Test
    void testBookSlotByIdSuccessfully() {
        Long slotId = 1L;
        Sloting slot = new Sloting();
        slot.setName("Slot 1");

        when(slotingRepository.findById(slotId)).thenReturn(Optional.of(slot));

        boolean result = slotingService.bookSlot(slotId);
        assertTrue(result);
        verify(slotingRepository, times(1)).save(any(Sloting.class));
    }

    @Test
    void testBookSlotByIdWhenNotFound() {
        Long slotId = 1L;
        when(slotingRepository.findById(slotId)).thenReturn(Optional.empty());

        boolean result = slotingService.bookSlot(slotId);
        assertFalse(result);
        verify(slotingRepository, never()).save(any(Sloting.class));
    }

    @Test
    void testCheckSlotAvailabilityByIdWhenSlotDoesNotExist() {
        Long slotId = 1L;
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.now();
        LocalTime endTime = startTime.plusHours(1);

        when(slotingRepository.findById(slotId)).thenReturn(Optional.empty());

        boolean result = slotingService.checkSlotAvailability(slotId, date, startTime, endTime);
        assertFalse(result);
    }

    @Test
    void testCheckSlotAvailabilityByIdWhenSlotIsAvailable() {
        Long slotId = 1L;
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.now();
        LocalTime endTime = startTime.plusHours(1);
        
        Sloting slot = new Sloting();
        slot.setName("Slot 1");

        when(slotingRepository.findById(slotId)).thenReturn(Optional.of(slot));
        when(slotingRepository.existsByName(slot.getName())).thenReturn(false);

        boolean result = slotingService.checkSlotAvailability(slotId, date, startTime, endTime);
        assertTrue(result);
    }
}
