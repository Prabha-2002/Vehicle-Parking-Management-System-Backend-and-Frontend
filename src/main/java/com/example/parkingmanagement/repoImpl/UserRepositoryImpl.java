package com.example.parkingmanagement.repoImpl;

import org.springframework.stereotype.Repository;
import com.example.parkingmanagement.model.User;
import com.example.parkingmanagement.repo.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

	private final EntityManager entityManager;

	public UserRepositoryImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public User findById(Long userId) {
		return entityManager.find(User.class, userId);
	}

	@Override
	public User save(User user) {
		if (user.getId() == null) {
			entityManager.persist(user);
			return user;
		} else {
			return entityManager.merge(user);
		}
	}

	@Override
	public void delete(User user) {
		if (entityManager.contains(user)) {
			entityManager.remove(user);
		} else {
			entityManager.remove(entityManager.merge(user));
		}
	}

	@Override
	public User findByUsername(String username) {
		try {
			return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
					.setParameter("username", username).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean existsById(Long userId) {
		return entityManager
				.createQuery("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.id = :userId",
						Boolean.class)
				.setParameter("userId", userId).getSingleResult();
	}

}
