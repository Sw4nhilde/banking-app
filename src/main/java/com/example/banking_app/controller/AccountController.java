package com.example.banking_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.banking_app.model.Account;
import com.example.banking_app.service.AccountService;

import jakarta.servlet.http.HttpSession;




@Controller
@RequestMapping("/accounts")
public class AccountController {
    
    @Autowired
    private AccountService accountService;

    @GetMapping("/{id}")
    public String getAccountById(@PathVariable Long id, Model model) {
        Account account = accountService.getAccountById(id);
        if (account == null) {
            return "redirect:/accounts?error=Account+not+found";
        }
        model.addAttribute("account", account);
        return "accounts/view";
    }
    
    

    @GetMapping("/search")
    public String searchAccounts(@RequestParam(required=false) String query, Model model) {
        if (query != null && !query.isEmpty()) {
            model.addAttribute("accounts", accountService.searchAccounts(query));
        } else {
            model.addAttribute("accounts", accountService.getAllAcooAccounts());
        }
        return "accounts/list";
    }
    

    @GetMapping
    public String viewAccounts(Model model, HttpSession session) {
        model.addAttribute("accounts", accountService.getAllAcooAccounts());
        return "accounts/list";
    }

    @GetMapping("/new")
    public String showNewAccountForm (Model model) {
        model.addAttribute("account", new Account());
        return "accounts/new";
    }

    @PostMapping("/save")
    public String saveAccount(@ModelAttribute("account") Account account) {
        accountService.createAccount(account);
        return "redirect:/accounts";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("account", accountService.getAccountById(id));
        return "accounts/edit";
    }

    @PostMapping("/update/{id}")
    public String updateAccount(@PathVariable Long id, @ModelAttribute("account") Account account) {
        accountService.updateAccount(id, account);
        return "redirect:/accounts";
    }

    @GetMapping("/delete/{id}")
    public String deleteAccount(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            accountService.deleteAccount(id);
            redirectAttributes.addFlashAttribute("successMessage", "Account deleted");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting account: "+e.getMessage());
        }

        return "redirect:/accounts";
    }

    @GetMapping("/deposit/{id}")
    public String showDepositForm(@PathVariable Long id, Model model) {
        model.addAttribute("account", accountService.getAccountById(id));
        return "accounts/deposit";
    }
    
    @PostMapping("/deposit/{id}")
    public String deposit(@PathVariable Long id, @RequestParam double amount) {
        accountService.deposit(id, amount);
        return "redirect:/accounts";
    }

    @GetMapping("/withdraw/{id}")
    public String showWithdrawForm(@PathVariable Long id, Model model) {
        model.addAttribute("account", accountService.getAccountById(id));
        return "accounts/withdraw";
    }
    
    @PostMapping("/withdraw/{id}")
    public String withdraw(@PathVariable Long id, @RequestParam double amount) {
        accountService.withdraw(id, amount);
        return "redirect:/accounts";
    }
    
    @PostMapping("/update-currency")
    public String updateCurrencyPreference(@RequestParam String currency, HttpSession session) {
        session.setAttribute("userCurrency", currency);
        return "redirect:/accounts";
    }
}
