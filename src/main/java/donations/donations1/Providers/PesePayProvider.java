package donations.donations1.Providers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.codevirtus.Pesepay;
import com.codevirtus.payments.Transaction;
import com.codevirtus.response.Response;

import donations.donations1.dtos.CheckPaymentStatusDTO;
import donations.donations1.dtos.InitiatePaymentDTO;
import donations.donations1.dtos.MakePaymentResult;
import donations.donations1.dtos.PaymentFailedException;
import donations.donations1.features.payments.Payment;
import donations.donations1.features.payments.PaymentRepository;

@Component
public class PesePayProvider {

    @Value("${pesepay.integrationKey}")
    private String intergrationKey;

    @Value("${pesepay.encryptionKey}")
    private String encryptionKey;

    @Autowired
    private PaymentRepository paymentRepository;

    public MakePaymentResult makePayment(InitiatePaymentDTO paymentRequest) throws PaymentFailedException {

        Pesepay pesepay = new Pesepay(intergrationKey, encryptionKey);
        Payment payment = new Payment();

        payment.setUuid(UUID.randomUUID().toString());


        // Set result and return URLs
        String resultUrl = "http://localhost:8081/result";
        String returnUrl = "http://localhost:8081/return?paymentId=" + payment.getUuid();

        pesepay.setResultUrl(resultUrl);
        pesepay.setReturnUrl(returnUrl);

        Transaction transaction = new Transaction(
                paymentRequest.amount(),
                paymentRequest.currencyCode(),
                "Donation"
        );

        Response res = pesepay.initiateTransaction(transaction);

        if (res.isSuccess()) {
            payment.setReferenceNumber(res.getReferenceNumber());
            return new MakePaymentResult(res, paymentRequest.amount(), paymentRequest.currencyCode(), resultUrl, returnUrl, paymentRepository.save(payment));
        } else {
            throw new PaymentFailedException();
        }
    }

    public CheckPaymentStatusDTO getPaymentStatus(String referenceNumber) {
        Pesepay pesepay = new Pesepay(intergrationKey, encryptionKey);
        Response response = pesepay.checkPayment(referenceNumber);
        return new CheckPaymentStatusDTO(response);
    }
}
