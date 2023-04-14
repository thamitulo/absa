package za.co.bank.x.interview.mapper;

import org.mapstruct.*;
import za.co.bank.x.interview.services.impl.entities.Transaction;
import za.co.bank.x.interview.models.response.TransactionDto;
import za.co.bank.x.interview.models.response.TransactionResponse;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionsMapper {

    @Mappings({
            @Mapping(target = "reference", source = "transaction.reference"),
            @Mapping(target = "transactionId", ignore = true),
            @Mapping(target = "amount", source = "transaction.amount"),
            @Mapping(target = "fromAccount", source = "transaction.account.accountNumber"),
            @Mapping(target = "toAccount", source = "transaction.account.accountNumber")
    })
    TransactionResponse transactionResponseFromTransaction(Transaction transaction);

    @Mappings({
            @Mapping(target = "amount", source = "transaction.amount"),
            @Mapping(target = "reference", source = "transaction.reference"),
            @Mapping(target = "transactionType", source = "transaction.transactionType"),
            @Mapping(target = "transactionTime", source = "transaction.transactionTime"),
            @Mapping(target = "destinationAccount", source = "transaction.account.accountNumber"),
            @Mapping(target = "sourceAccount", source = "transaction.account.accountNumber")
    })
    TransactionDto toDto(Transaction transaction);

    List<TransactionDto> toDtoList(List<Transaction> transactionList);

    @Mappings({
            @Mapping(target = "reference", source = "transaction.reference"),
           // @Mapping(target = "transactionId", source = "transaction.transactionId"),
            @Mapping(target = "amount", source = "transaction.amount"),
           // @Mapping(target = "fromAccount", source = "transaction.fromAccount"),
         //   @Mapping(target = "toAccount", source = "transaction.toAccount")
    })
   Transaction transactionResponseToTransaction(TransactionResponse transaction);

    @Named("transactionAmount")
    default BigDecimal transactionAmount(TransactionResponse transactionResponse) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        Number transactionAmount = null;
        try {
            transactionAmount = format.parse(transactionResponse.getAmount());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return new BigDecimal(transactionAmount.toString());
    }
}