package donations.donations1.dtos;

public class PaymentfailedException extends RuntimeException {
    public PaymentfailedException(String message) {
        super(message);
    }
}
