package com.example.banking_app.service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.banking_app.config.CurrencyConfig.CurrencyFormat;
import com.example.banking_app.model.Account;
import com.example.banking_app.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
        private Map<String, CurrencyFormat> currencyFormats;
        
        public String formatBalance(double amount, String currencyCode) {
            CurrencyFormat format = currencyFormats.getOrDefault(currencyCode, currencyFormats.get("IDR"));
            
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMinimumFractionDigits(format.getDecimalDigits());
            nf.setMaximumFractionDigits(format.getDecimalDigits()); 
            
            if (nf instanceof DecimalFormat df) {
                df.setDecimalFormatSymbols(new DecimalFormatSymbols() {{
                    setDecimalSeparator(format.getDecimalSeparator().charAt(0));
                    setGroupingSeparator(format.getGroupingSeparator().charAt(0));
                }});
            }
            
            return format.getSymbol() + " " + nf.format(amount);
        }
    
    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAllAcooAccounts() {
        return accountRepository.findAll();
    } 

    public Account getAccountById(long id) {
        return accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
    }

    public List<Account> searchAccounts(String query) {
        return accountRepository.findByAccountHolderNameContainingOrAccountNumberContainingOrEmailContainingOrPhoneContaining(
            query, query, query, query
        );
    }

    private String generateAccountNumber() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String randomPart = String.format("%05d", new Random().nextInt(100000));
        return "BANK-"+ datePart + "-" + randomPart;
    }

    public Account createAccount(Account account) {
        account.setAccountNumber(generateAccountNumber());
        return accountRepository.save(account);
    }

    public Account updateAccount(Long id, Account accountDetails) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account != null) {
            account.setAccountHolderName(accountDetails.getAccountHolderName());
            account.setBalance(accountDetails.getBalance());
            account.setAccountType(accountDetails.getAccountType());
            account.setEmail(accountDetails.getEmail());
            account.setPhone(accountDetails.getPhone());
            return accountRepository.save(account);
        }
        return null;
    }

    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(()-> new RuntimeException("Account not found"));
        if (account.getCurrency() == null) {
            throw new IllegalStateException("Account Currency cannot be null");
        }

        accountRepository.delete(account);
    }

    public Account deposit(Long id, Double amount) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account != null && amount > 0) {
            account.setBalance(account.getBalance() + amount);
            return accountRepository.save(account);
        }
        return null;
    }

    public Account withdraw(Long id, Double amount) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account != null && amount > 0 && account.getBalance() >= amount) {
            account.setBalance(account.getBalance() - amount);
            return accountRepository.save(account);
        }
        return null;
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleException(RuntimeException ex, Model model){
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

}
