package com.example.parkingmanagement.repoImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.parkingmanagement.model.Sloting;
import com.example.parkingmanagement.repo.SlotingRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
@Transactional
public class SlotingRepositoryImpl implements SlotingRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Sloting> findByDateAndAvailable(LocalDate date, boolean available) {
		String query = "SELECT s FROM Sloting s WHERE s.date = :date AND s.available = :available";
		TypedQuery<Sloting> typedQuery = entityManager.createQuery(query, Sloting.class);
		typedQuery.setParameter("date", date);
		typedQuery.setParameter("available", available);
		return typedQuery.getResultList();
	}

	@Override
	public Optional<Sloting> findById(Long id) {
		Sloting slot = entityManager.find(Sloting.class, id);
		return Optional.ofNullable(slot);
	}

	@Override
	public void save(Sloting slot) {
		if (slot.getId() == null) {
			entityManager.persist(slot);
		} else {
			entityManager.merge(slot);
		}
	}

	@Override
	public Sloting findById1(Long slotId) {
		return entityManager.find(Sloting.class, slotId);
	}

	@Override
	public List<Sloting> getAvailableSlots(LocalDate date) {
		String query = "SELECT s FROM Sloting s WHERE s.date = :date AND s.available = true";
		TypedQuery<Sloting> typedQuery = entityManager.createQuery(query, Sloting.class);
		typedQuery.setParameter("date", date);
		return typedQuery.getResultList();
	}

	@Override
	public boolean existsByName(String slotId) {
		String query = "SELECT COUNT(s) FROM Sloting s WHERE s.name = :name";
		TypedQuery<Long> typedQuery = entityManager.createQuery(query, Long.class);
		typedQuery.setParameter("name", slotId);
		Long count = typedQuery.getSingleResult();
		return count > 0;
	}

	@Override
	public List<Sloting> findByNameAndDate(String name, LocalDate date) {
		String query = "SELECT s FROM Sloting s WHERE s.name = :name AND s.date = :date";
		TypedQuery<Sloting> typedQuery = entityManager.createQuery(query, Sloting.class);
		typedQuery.setParameter("name", name);
		typedQuery.setParameter("date", date);
		return typedQuery.getResultList();
	}

	@Override
	public List<Sloting> findAll() {
		String query = "SELECT s FROM Sloting s";
		TypedQuery<Sloting> typedQuery = entityManager.createQuery(query, Sloting.class);
		return typedQuery.getResultList();
	}

	@Override
	public boolean isSlotAvailable(String slotId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		Sloting slot = entityManager.find(Sloting.class, slotId);
		if (slot == null) {
			return false;
		}

		String query = "SELECT COUNT(b) FROM Booking b WHERE b.slotId = :slotId "
				+ "AND ((b.startDateTime < :endDateTime) AND (b.endDateTime > :startDateTime))";
		TypedQuery<Long> typedQuery = entityManager.createQuery(query, Long.class);
		typedQuery.setParameter("slotId", slotId);
		typedQuery.setParameter("startDateTime", startDateTime);
		typedQuery.setParameter("endDateTime", endDateTime);

		Long count = typedQuery.getSingleResult();
		return count == 0;
	}
}
