package donations.donations1.features.payments;

import donations.donations1.dtos.CheckPaymentStatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import donations.donations1.dtos.InitiatePaymentDTO;
import donations.donations1.dtos.MakePaymentResult;
import donations.donations1.dtos.PaymentFailedException;
import donations.donations1.features.donations.DonationService;

import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private DonationService donationService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/initiate")
    public ResponseEntity<MakePaymentResult> initiatePayment(@RequestBody InitiatePaymentDTO paymentRequest) {

        try {
            MakePaymentResult paymentResponse = donationService.initiatePayment(paymentRequest);

            return ResponseEntity.ok(paymentResponse);
        } catch (PaymentFailedException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/check-payment/{referenceNumber}")
    public ResponseEntity<CheckPaymentStatusDTO> getPaymentStatus(@PathVariable String referenceNumber) {

        Optional<CheckPaymentStatusDTO> paymentStatusOptional = paymentService.checkPaymentStatus(referenceNumber);

        if (paymentStatusOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CheckPaymentStatusDTO paymentStatus = paymentStatusOptional.get();

        return ResponseEntity.ok(paymentStatus);
    }

    @GetMapping("/check-payment")
    public ResponseEntity<CheckPaymentStatusDTO> getPaymentStatusWithPaymentId(@RequestParam String paymentId) {
        Optional<CheckPaymentStatusDTO> dtoOptional = paymentService.checkPaymentStatusWithPaymentId(paymentId);

        if (dtoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(dtoOptional.get());
    }
}