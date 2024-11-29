package donations.donations1.dtos;

import com.codevirtus.response.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MakePaymentResult {

    private String reference;
    private String pollUrl;
    private String redirectUrl;
    private String status;
    private String message;
    private Double amount;
    private String currencyCode;
    private String returnUrl;
    private String resultUrl;

    public MakePaymentResult(Response response) {
        this.status = response.getTransactionStatus();
        this.pollUrl = response.getPollUrl();
        this.redirectUrl = response.getRedirectUrl();
        this.reference = response.getReferenceNumber();
        this.message = response.getMessage();
    }

    public MakePaymentResult(Response response, Double amount, String currencyCode, String resultUrl, String returnUrl) {
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.status = response.getTransactionStatus();
        this.pollUrl = response.getPollUrl();
        this.redirectUrl = response.getRedirectUrl();
        this.reference = response.getReferenceNumber();
        this.message = response.getMessage();
        this.resultUrl = resultUrl;
        this.returnUrl = returnUrl;
    }
}
