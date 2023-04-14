package za.co.bank.x.interview.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import za.co.bank.x.interview.services.impl.entities.Account;
import za.co.bank.x.interview.models.response.AccountDto;
import za.co.bank.x.interview.models.response.TransactionDto;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {TransactionsMapper.class})
public interface AccountMapper {

    TransactionsMapper transactionsMapper = Mappers.getMapper(TransactionsMapper.class);

    @Mappings({
            @Mapping(target = "accountType", source = "account.accountType"),
            @Mapping(target = "balance", source = "account.balance"),
            @Mapping(target = "accountNumber", source = "account.accountNumber"),
            @Mapping(target = "createdAt", source = "account.createdAt"),
            @Mapping(target = "lastUpdatedAt", source = "account.lastUpdatedAt"),
            @Mapping(target = "transactions", source = "account", qualifiedByName= "transactions")
    })
    AccountDto toDto(Account account);

    @Named("transactions")
    default Set<TransactionDto> transactions(Account account) {
        return account.getTransactions() != null ?
                account.getTransactions().stream().map(transactionsMapper::toDto).collect(Collectors.toSet()) : Collections.emptySet();
    }

    /*
    default AccountDto toDtos(Account account) {
        BigDecimal accountBalance = account.getBalance() != null ?
                account.getBalance().setScale(2, RoundingMode.HALF_EVEN) : BigDecimal.ZERO;

        Set<TransactionDto> transactions = account.getTransactions() != null ?
                account.getTransactions().stream().map(MapperUtil::toDto).collect(Collectors.toSet()) : Collections.emptySet();

        return AccountDto.builder()
                .accountType(account.getAccountType())
                .balance(String.valueOf(accountBalance))
                .accountNumber(account.getAccountNumber())
                .createdAt(account.getCreatedAt())
                .lastUpdatedAt(account.getLastUpdatedAt())
                .transactions(transactions)
                .build();
    }
     */

}