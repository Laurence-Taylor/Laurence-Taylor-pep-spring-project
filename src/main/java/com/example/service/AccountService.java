package com.example.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;
import com.example.entity.Account;
import com.example.exception.AccountHandleExceptions;

import java.util.List;

@Service
@Transactional
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account register(Account newAccount) throws AccountHandleExceptions{
        if ((newAccount.getUsername()!="")&&
        (newAccount.getPassword().length()>4)){
            accountRepository.save(newAccount);
            List<Account> listAccount = accountRepository.findByUsername(newAccount.getUsername());
            return listAccount.get(0);
        }else if(newAccount.getUsername()==""){
            throw new AccountHandleExceptions("The Username field must not be empty. Please try again.");
        }else if(newAccount.getPassword().length()<=4){
            throw new AccountHandleExceptions("Password must be longer than 4 characters. Please try again.");
        }
        return null;
    }

}
