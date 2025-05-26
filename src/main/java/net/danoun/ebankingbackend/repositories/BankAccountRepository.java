package net.danoun.ebankingbackend.repositories;

import net.danoun.ebankingbackend.entities.BankAccount;
import net.danoun.ebankingbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}
