package za.co.bank.x.interview.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import za.co.bank.x.interview.services.impl.entities.Customer;
import za.co.bank.x.interview.models.request.CustomerRequest;
import za.co.bank.x.interview.models.response.CustomerDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    @Mappings({
            @Mapping(target = "email", source = "customerRequest.email"),
            @Mapping(target = "idNumber", source = "customerRequest.idNumber"),
            @Mapping(target = "idType", source = "customerRequest.idType"),
            @Mapping(target = "cellphoneNumber", source = "customerRequest.cellphoneNumber"),
            @Mapping(target = "lastName", source = "customerRequest.lastName"),
            @Mapping(target = "firstName", source = "customerRequest.firstName"),
            @Mapping(target = "dateOfBirth", source = "customerRequest.dateOfBirth"),
            @Mapping(target = "accounts", ignore = true)
    })
    Customer fromCustomerRequest(CustomerRequest customerRequest);

    @Mappings({
            @Mapping(target = "firstName", source = "customer.firstName"),
            @Mapping(target = "lastName", source = "customer.lastName"),
            @Mapping(target = "email", source = "customer.email"),
            @Mapping(target = "cellphoneNumber", source = "customer.cellphoneNumber"),
            @Mapping(target = "idNumber", source = "customer.idNumber"),
            @Mapping(target = "idType", source = "customer.idType"),
            @Mapping(target = "dateOfBirth", source = "customer.dateOfBirth"),
            @Mapping(target = "accounts", source = "customer.accounts"),
            @Mapping(target = "accounts.transactions", ignore = true)
    })
    CustomerDto toDto(Customer customer);

}
