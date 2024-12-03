package donations.donations1.dtos;


import com.codevirtus.response.Response;
import lombok.Data;

@Data
public class CheckPaymentStatusDTO {

    private String status;
    private String reference;
    private String pollUrl;
    private Double amount;
    private String currencyCode;



    public CheckPaymentStatusDTO(Response res) {
        this.status = res.getTransactionStatus();
        this.reference = res.getReferenceNumber();
        this.pollUrl = res.getPollUrl();

    }
}
