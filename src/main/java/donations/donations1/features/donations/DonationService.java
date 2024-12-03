package donations.donations1.features.donations;

import donations.donations1.dtos.CheckPaymentStatusDTO;
import donations.donations1.features.payments.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import donations.donations1.Providers.PesePayProvider;
import donations.donations1.dtos.InitiatePaymentDTO;
import donations.donations1.dtos.MakePaymentResult;
import donations.donations1.dtos.PaymentFailedException;

import java.util.Optional;

@Service
public class DonationService {

    @Autowired
    private PesePayProvider pesePayProvider;

    @Autowired
    private DonationRepository donationRepository;


    public MakePaymentResult initiatePayment(InitiatePaymentDTO initiatePaymentDTO)  throws PaymentFailedException {
        // Logic to process payment

        MakePaymentResult result = pesePayProvider.makePayment(initiatePaymentDTO);


        Donation donation = new Donation();
        donation.setCurrencyCode(initiatePaymentDTO.currencyCode());
        donation.setAmount(initiatePaymentDTO.amount());
        donation.setReferenceNumber(result.getReference());
        donation.setStatus("Pending");
        donation.setPayment(result.getPaymentIntent());


        donationRepository.save(donation); // Save to database

        return  result;
    }

    public CheckPaymentStatusDTO getPaymentStatus(String referenceNumber) {
        return pesePayProvider.getPaymentStatus(referenceNumber);
    }

    public Optional<Donation> getDonationByReferenceNumber(String referenceNumber) {
        return donationRepository.findByReferenceNumber(referenceNumber);
    }


    public Optional<Donation> getDonationByPayment(Payment payment) {
        return  donationRepository.findByPayment(payment);
    }
}

