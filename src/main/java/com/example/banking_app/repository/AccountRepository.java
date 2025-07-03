package com.example.banking_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.banking_app.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountNumber(String accountNumber);

    List<Account> findByAccountHolderNameContainingOrAccountNumberContainingOrEmailContainingOrPhoneContaining(
        String name, String accountNumber, String email, String phone);
}
