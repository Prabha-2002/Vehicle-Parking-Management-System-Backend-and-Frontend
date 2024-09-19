package com.example.parkingmanagement.repoImpl;
import org.springframework.stereotype.Repository;
import com.example.parkingmanagement.model.Payment;
import com.example.parkingmanagement.repo.PaymentRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class PaymentRepositoryImpl implements PaymentRepository {

    private final EntityManager entityManager;

    public PaymentRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Payment save(Payment payment) {
        if (payment != null) {
            entityManager.merge(payment);
            return payment;
        }
        return null;
    }

	@Override
	public Payment findById(Long paymentId) {
        return entityManager.find(Payment.class, paymentId);
	}

	@Override
    public void deleteById(Long paymentId) {
        Payment payment = entityManager.find(Payment.class, paymentId);
        if (payment != null) {
            entityManager.remove(payment);
        }
    }
}