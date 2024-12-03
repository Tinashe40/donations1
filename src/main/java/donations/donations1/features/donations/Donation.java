package donations.donations1.features.donations;



import donations.donations1.features.payments.Payment;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;
    private String currencyCode;


    @Column(nullable = true)
    private String paymentMethod;

    @Column(unique = true)
    private String referenceNumber;
    private String status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "paymentId")
    private Payment payment;
}
