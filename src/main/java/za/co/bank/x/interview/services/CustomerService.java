package za.co.bank.x.interview.services;

import za.co.bank.x.interview.models.request.CustomerRequest;
import za.co.bank.x.interview.models.response.CustomerDto;

public interface CustomerService {
    CustomerDto onboardCustomer(CustomerRequest request);
}
