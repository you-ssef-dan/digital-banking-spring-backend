package net.danoun.ebankingbackend;

import net.danoun.ebankingbackend.entities.*;
import net.danoun.ebankingbackend.enums.AccountStatus;
import net.danoun.ebankingbackend.enums.OperationType;
import net.danoun.ebankingbackend.exceptions.BalanceNotSufficientException;
import net.danoun.ebankingbackend.exceptions.BankAccountNotFoundException;
import net.danoun.ebankingbackend.exceptions.CustomerNotFoundException;
import net.danoun.ebankingbackend.repositories.AccountOperationRepository;
import net.danoun.ebankingbackend.repositories.BankAccountRepository;
import net.danoun.ebankingbackend.repositories.CustomerRepository;
import net.danoun.ebankingbackend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EBankingBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(EBankingBackEndApplication.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService) {

        return args -> {
            Stream.of("danoun", "chidoub", "bacha").forEach(name -> {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name + "@gmail.Com");

                bankAccountService.saveCustomer(customer);
            });
            bankAccountService.listCustomers().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentAccount(Math.random()*90000,9000,customer.getId());
                    bankAccountService.saveSavingAccount(Math.random()*120000,5.5,customer.getId());
                    List<BankAccount> bankAccounts = bankAccountService.listBankAccounts();
                    for (BankAccount bankAccount : bankAccounts) {
                        for (int i = 0; i < 10; i++) {
                            bankAccountService.credit(bankAccount.getId(), 10000 + Math.random()*120000,"Credit");
                            bankAccountService.debit(bankAccount.getId(), 1000 + Math.random()*9000,"Debit");
                        }
                    }
                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                } catch (BankAccountNotFoundException | BalanceNotSufficientException e) {
                    e.printStackTrace();
                }
            });

        };
    }


    //@Bean
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



/*
            BankAccount bankAccount =
                    bankAccountRepository.findById("1b4bea48-a10c-4a57-8675-a13a2b7f87d8").orElse(null);
            if (bankAccount != null) {
                System.out.println("*********************");
                System.out.println("Bank Account ID: " + bankAccount.getId());
                System.out.println("balance: " + bankAccount.getBalance());
                System.out.println("customer: " + bankAccount.getCustomer().getName());
                System.out.println("Status: "+ bankAccount.getStatus());
                System.out.println("Create date: "+ bankAccount.getCreatedAt());
                System.out.println(bankAccount.getClass().getSimpleName());
                if (bankAccount instanceof CurrentAccount) {
                    System.out.println("Over Draft => " + ((CurrentAccount) bankAccount).getOverDraft());
                }else if (bankAccount instanceof SavingAccount){
                    System.out.println("Rate => " +  ((SavingAccount) bankAccount).getInterestRate());
                }
                bankAccount.getAccountOperations().forEach(op -> {
                    System.out.println(op.getType() + "\t" + op.getOperationDate() +"\t" +  op.getAmount());
                });

            }*/
