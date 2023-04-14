package za.co.bank.x.interview.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.bank.x.interview.models.request.PaymentRequest;
import za.co.bank.x.interview.models.response.TransactionDto;
import za.co.bank.x.interview.models.response.TransactionResponse;
import za.co.bank.x.interview.services.TransactionsService;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {

    @Autowired
    private TransactionsService transactionsService;

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getTransactions() {
        return ResponseEntity.ok(transactionsService.retrieveTransactions());
    }

    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> processPayment(@RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.ok(transactionsService.processTransaction(paymentRequest));
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transferBetweenAccounts(@RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.ok(transactionsService.processTransaction(paymentRequest));
    }
}
