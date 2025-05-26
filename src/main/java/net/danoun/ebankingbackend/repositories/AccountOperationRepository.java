package net.danoun.ebankingbackend.repositories;

import net.danoun.ebankingbackend.entities.AccountOperation;
import net.danoun.ebankingbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
}
