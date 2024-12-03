package donations.donations1.features.payments;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import donations.donations1.Providers.PesePayProvider;
import donations.donations1.dtos.CheckPaymentStatusDTO;
import donations.donations1.features.donations.Donation;
import donations.donations1.features.donations.DonationService;

@Service
public class PaymentService {

    @Autowired
    private DonationService donationService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PesePayProvider pesePayProvider;

    public Optional<CheckPaymentStatusDTO> checkPaymentStatus(String referenceNumber) {
        CheckPaymentStatusDTO paymentStatus = pesePayProvider.getPaymentStatus(referenceNumber);
        Optional<Donation> donationOptional = donationService.getDonationByReferenceNumber(referenceNumber);

        if (paymentStatus.getStatus() == null && donationOptional.isEmpty()) {
            return Optional.empty();
        }

        Donation donation = donationOptional.get();

        paymentStatus.setAmount(donation.getAmount());
        paymentStatus.setCurrencyCode(donation.getCurrencyCode());

        return Optional.of(paymentStatus);

    }

    public Optional<CheckPaymentStatusDTO> checkPaymentStatusWithPaymentId(String paymentID) {

        Optional<Payment> paymentOptional = paymentRepository.findById(paymentID);

        if (paymentOptional.isEmpty()) {
            return Optional.empty();
        }

        Payment payment = paymentOptional.get();
        CheckPaymentStatusDTO paymentStatus = pesePayProvider.getPaymentStatus(payment.getReferenceNumber());
        Optional<Donation> donationOptional = donationService.getDonationByReferenceNumber(payment.getReferenceNumber());

        if (donationOptional.isEmpty()) {
            return Optional.empty();
        }

        Donation donation  = donationOptional.get();

        paymentStatus.setAmount(donation.getAmount());
        paymentStatus.setCurrencyCode(donation.getCurrencyCode());

        return Optional.of(paymentStatus);

    }
}
