package donations.donations1.dtos;


public class PaymentFailedException extends Exception {

    @Override
    public String getMessage() {
        return "Payment Failed try again!";

    }

}
