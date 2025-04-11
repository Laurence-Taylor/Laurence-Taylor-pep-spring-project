package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 @RestController
 @RequestMapping
public class SocialMediaController {

    private AccountService accountService;
    private AccountRepository accountRepository;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, AccountRepository accountRepository, MessageService messageService){
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.messageService = messageService;
    }

    @PostMapping("register")
    public ResponseEntity<Account> userRegistration(@RequestBody Account newAccount){
        List<Account> listAccount = accountRepository.findByUsername(newAccount.getUsername());
        if(!listAccount.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        Account createdAccount = accountService.register(newAccount);
        if(createdAccount!=null){
            return ResponseEntity.status(HttpStatus.OK).body(createdAccount);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @PostMapping("login")
    public ResponseEntity<Account> userLogin(@RequestBody Account account){
        Account loginAccount = accountService.login(account.getUsername(), account.getPassword());
        if (loginAccount!= null){
            return ResponseEntity.status(HttpStatus.OK).body(loginAccount);
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message newMessage){
        Message createdMessage = messageService.createMessage(newMessage);
        if (createdMessage!= null){
            return ResponseEntity.status(HttpStatus.OK).body(createdMessage);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("messages")
    public ResponseEntity <List<Message>> retrieveAllMessage(){ 
        return ResponseEntity.status(HttpStatus.OK).body(messageService.retrieveAllMessage());
    }

    @GetMapping("messages/{messageId}")
    public ResponseEntity <Message> retrieveMEssageByMessageId(@PathVariable int messageId){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.retrieveMessageById(messageId));
    }

    

}
