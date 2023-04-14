package za.co.bank.x.interview.services.impl.entities;

import lombok.*;
import za.co.bank.x.interview.enums.IdType;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends AbstractAuditEntity {
    private String firstName;
    private String lastName;
    private IdType idType;
    private String idNumber;
    private LocalDate dateOfBirth;
    private String email;
    private String cellphoneNumber;

    @OneToMany(mappedBy="customer", cascade = CascadeType.ALL)
    private Set<Account> accounts;
}
