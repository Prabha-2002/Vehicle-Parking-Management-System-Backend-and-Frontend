package com.example.parkingmanagement.repoImpl;

import com.example.parkingmanagement.model.Booking;
import com.example.parkingmanagement.model.BookingStatus;
import com.example.parkingmanagement.model.PrepaymentStatus;
import com.example.parkingmanagement.model.Sloting;
import com.example.parkingmanagement.repo.BookingRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class BookingRepositoryImpl implements BookingRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Booking> findByDate(LocalDate date) {
		TypedQuery<Booking> query = entityManager.createQuery("SELECT b FROM Booking b WHERE b.date = :date",
				Booking.class);
		query.setParameter("date", date);
		return query.getResultList();
	}

	@Override
	public List<Booking> findBookingsBySlotAndDate(String slot, LocalDateTime startDateTime,
			LocalDateTime endDateTime) {
		TypedQuery<Booking> query = entityManager.createQuery("SELECT b FROM Booking b WHERE b.slot = :slot AND "
				+ "(b.startTime < :endDateTime AND b.endTime > :startDateTime)", Booking.class);
		query.setParameter("slot", slot);
		query.setParameter("startDateTime", startDateTime);
		query.setParameter("endDateTime", endDateTime);
		return query.getResultList();
	}

	@Override
	public Booking save(Booking booking) {
		if (booking.getId() == null) {
			entityManager.persist(booking);
			return booking;
		} else {
			return entityManager.merge(booking);
		}
	}

	@Override
	public Booking findById(Long id) {
		return entityManager.find(Booking.class, id);
	}

	@Override
	public List<Booking> findAll() {
		String jpql = "SELECT b FROM Booking b";
		TypedQuery<Booking> query = entityManager.createQuery(jpql, Booking.class);
		return query.getResultList();
	}

	@Override
	public List<Booking> findByStatus(BookingStatus status) {
		TypedQuery<Booking> query = entityManager.createQuery("SELECT b FROM Booking b WHERE b.status = :status",
				Booking.class);
		query.setParameter("status", status);
		return query.getResultList();
	}

	@Override
	public List<Booking> findByUserId(Long userId) {
		TypedQuery<Booking> query = entityManager.createQuery("SELECT b FROM Booking b WHERE b.user.id = :userId",
				Booking.class);
		query.setParameter("userId", userId);
		return query.getResultList();
	}

	@Override
	public Optional<Booking> findBySlotNameAndDateAndPrepaymentStatus(String slotId, LocalDateTime date,
			PrepaymentStatus prepaymentStatus) {
		TypedQuery<Booking> query = entityManager.createQuery(
				"SELECT b FROM Booking b WHERE b.slotName = :slotId AND b.date = :date AND b.prepaymentStatus = :prepaymentStatus",
				Booking.class);
		query.setParameter("slotId", slotId);
		query.setParameter("date", date.toLocalDate());
		query.setParameter("prepaymentStatus", prepaymentStatus);

		List<Booking> results = query.getResultList();
		return results.stream().findFirst();
	}

	@Override
	public double findTotalEarnings() {
		String queryStr = "SELECT COALESCE(SUM(b.amount), 0) FROM Booking b";
		Query query = entityManager.createQuery(queryStr);
		return (double) query.getSingleResult();
	}

	@Override
	public List<Booking> findByPrepaymentStatus(PrepaymentStatus status) {
		TypedQuery<Booking> query = entityManager
				.createQuery("SELECT b FROM Booking b WHERE b.prepaymentStatus = :status", Booking.class);
		query.setParameter("status", status);
		return query.getResultList();
	}

	@Override
	public List<Booking> findByStatus(String status) {
		String jpql = "SELECT b FROM Booking b WHERE b.status = :status";
		TypedQuery<Booking> query = entityManager.createQuery(jpql, Booking.class);
		query.setParameter("status", status);
		return query.getResultList();
	}

	@Override
	public List<Sloting> findBookedSlots(LocalDate date) {
		return null;
	}

}
