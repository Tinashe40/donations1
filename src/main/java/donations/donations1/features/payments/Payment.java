package donations.donations1.features.payments;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Payment {

    @Id
    private String uuid;
    private String referenceNumber = null;


}
