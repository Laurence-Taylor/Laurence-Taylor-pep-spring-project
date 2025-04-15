package com.example.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;
import com.example.controller.SocialMediaController;
import com.example.entity.Account;
import com.example.exception.AccountHandleExceptions;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    // Process new User Registrations
    public Account register(Account newAccount) throws AccountHandleExceptions{
        List<Account> listAccount = accountRepository.findByUsername(newAccount.getUsername());
        if(!listAccount.isEmpty()){
            SocialMediaController.existUser = true;
            return null;
        }

        if ((newAccount.getUsername()!="")&&
        (newAccount.getPassword().length()>4)){
            accountRepository.save(newAccount);
            listAccount = accountRepository.findByUsername(newAccount.getUsername());
            return listAccount.get(0);
        }
        return null;
    }

    // Process User Logins
    public Account login(String userName, String password){
        Optional<Account> loginAccount =accountRepository.findByUsernameAndPassword(userName, password);
        if(loginAccount.isPresent()){
            return loginAccount.get();
        }else{
            return null;
        }
    }

}
