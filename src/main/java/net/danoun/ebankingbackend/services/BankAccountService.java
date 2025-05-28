package net.danoun.ebankingbackend.services;

import net.danoun.ebankingbackend.entities.BankAccount;
import net.danoun.ebankingbackend.entities.CurrentAccount;
import net.danoun.ebankingbackend.entities.Customer;
import net.danoun.ebankingbackend.entities.SavingAccount;
import net.danoun.ebankingbackend.exceptions.BalanceNotSufficientException;
import net.danoun.ebankingbackend.exceptions.BankAccountNotFoundException;
import net.danoun.ebankingbackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {

    Customer  saveCustomer(Customer customer);
    SavingAccount saveSavingAccount(double initialBalance, double interestRate, Long customerld) throws CustomerNotFoundException;
    CurrentAccount saveCurrentAccount(double initialBalance, double overDraft, Long customerld) throws CustomerNotFoundException;
    List<Customer> listCustomers ();
    BankAccount getBankAccount(String accountld) throws BankAccountNotFoundException;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;

    List<BankAccount> listBankAccounts();
}
