package za.co.bank.x.interview.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import za.co.bank.x.interview.enums.IdType;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CustomerRequest {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private IdType idType;
    private String idNumber;
    private String cellphoneNumber;
    private String email;
}
