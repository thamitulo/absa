package za.co.bank.x.interview.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.bank.x.interview.models.request.CustomerRequest;
import za.co.bank.x.interview.models.response.CustomerDto;
import za.co.bank.x.interview.services.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    //TODO use MapStruct to map entities to DTOs & vice-versa CustomerDto.class
    @PostMapping("/onboard")
    public ResponseEntity<CustomerDto> onboardCustomer(@RequestBody CustomerRequest request) {
        return ResponseEntity.ok(customerService.onboardCustomer(request));
    }
}
