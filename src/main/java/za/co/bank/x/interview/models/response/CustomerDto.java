package za.co.bank.x.interview.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import za.co.bank.x.interview.enums.IdType;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private IdType idType;
    private String idNumber;
    private String cellphoneNumber;
    private String email;
    private Set<AccountDto> accounts;
}
