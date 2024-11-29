package donations.donations1.Providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.codevirtus.Pesepay;
import com.codevirtus.payments.Transaction;
import com.codevirtus.response.Response;
import donations.donations1.dtos.CheckPaymentStatusDTO;
import donations.donations1.dtos.InitiatePaymentDTO;
import donations.donations1.dtos.MakePaymentResult;
import donations.donations1.dtos.PaymentFailedException;

@Component
public class PesePayProvider {

    @Value("${pesepay.integrationKey}")
    private String intergrationKey;

    @Value("${pesepay.encryptionKey}")
    private String encryptionKey;

    public MakePaymentResult makePayment(InitiatePaymentDTO paymentRequest) throws PaymentFailedException {

        Pesepay pesepay = new Pesepay(intergrationKey, encryptionKey);

        // Set result and return URLs
        String resultUrl = "http://localhost:5173/result";
        String returnUrl = "http://localhost:5173/return?paymentId=23";

        pesepay.setResultUrl(resultUrl);
        pesepay.setReturnUrl(returnUrl);

        Transaction transaction = new Transaction(
                paymentRequest.amount(),
                paymentRequest.currencyCode(),
                "Donation"
        );

        Response res = pesepay.initiateTransaction(transaction);

        if (res.isSuccess()) {
            return new MakePaymentResult(res, paymentRequest.amount(), paymentRequest.currencyCode(), resultUrl, returnUrl);
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
