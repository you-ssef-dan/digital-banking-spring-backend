package net.danoun.ebankingbackend;

import net.danoun.ebankingbackend.entities.AccountOperation;
import net.danoun.ebankingbackend.entities.CurrentAccount;
import net.danoun.ebankingbackend.entities.Customer;
import net.danoun.ebankingbackend.entities.SavingAccount;
import net.danoun.ebankingbackend.enums.AccountStatus;
import net.danoun.ebankingbackend.enums.OperationType;
import net.danoun.ebankingbackend.repositories.AccountOperationRepository;
import net.danoun.ebankingbackend.repositories.BankAccountRepository;
import net.danoun.ebankingbackend.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EBankingBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(EBankingBackEndApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository) {
        return args -> {
            Stream.of("danoun", "chidoub", "bacha").forEach(name -> {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name + "@gmail.Com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(customer -> {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setCustomer(customer);
                currentAccount.setBalance(Math.random() * 90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setCustomer(customer);
                savingAccount.setBalance(Math.random() * 90000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);

            });
            bankAccountRepository.findAll().forEach(bankAccount -> {
                for (int i = 0; i < 10; i++) {
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random() * 12000);
                    accountOperation.setType(Math.random() > 0.5? OperationType.DEBIT: OperationType.CREDIT);
                    accountOperation.setBankAccount(bankAccount);
                    accountOperationRepository.save(accountOperation);
                }
            });
        };
    }

}
