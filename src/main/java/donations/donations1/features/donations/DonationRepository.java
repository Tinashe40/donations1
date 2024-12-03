package donations.donations1.features.donations;


import java.util.Optional;

import donations.donations1.features.payments.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    Optional<Donation> findByReferenceNumber(String referenceNumber);
    Optional<Donation> findByPayment(Payment payment);
}