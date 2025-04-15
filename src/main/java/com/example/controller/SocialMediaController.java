package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 @RestController
 @RequestMapping
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    public static Boolean existUser;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    // Process New User Registration...
    @PostMapping("register")
    public ResponseEntity<Account> userRegistration(@RequestBody Account newAccount){
        existUser = false;
        Account createdAccount = accountService.register(newAccount);
        if(existUser){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(null);
        }
        if(createdAccount!=null){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(createdAccount);
        }else{
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }

    //Process User Logins 
    @PostMapping("login")
    public ResponseEntity<Account> userLogin(@RequestBody Account account){
        Account loginAccount = accountService.login(account.getUsername(), account.getPassword());
        if (loginAccount!= null){
            return ResponseEntity.status(HttpStatus.OK).body(loginAccount);
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    // Process the creation of new Messages
    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message newMessage){
        Message createdMessage = messageService.createMessage(newMessage);
        if (createdMessage!= null){
            return ResponseEntity.status(HttpStatus.OK).body(createdMessage);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Process retrieve all Messages 
    @GetMapping("messages")
    public ResponseEntity <List<Message>> retrieveAllMessage(){ 
        return ResponseEntity.status(HttpStatus.OK).body(messageService.retrieveAllMessage());
    }

    // Process retrieve a Message given its ID. 
    @GetMapping("messages/{messageId}")
    public ResponseEntity <Message> retrieveMessageByMessageId(@PathVariable int messageId){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.retrieveMessageByMessageId(messageId));
    }

    // Process Delete a message given its ID
    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageByMessageId(@PathVariable Integer messageId){
        if(messageService.deleteMessageByMessageId(messageId)){
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }

    // Update a Message Text identified by a message ID
    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable Integer messageId, @RequestBody Message messageText){

        if((messageService.updateMessage(messageId, messageText))){
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
        
    // Retrieve all message written by a Particular User.
    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> retrieveAllMessagesForUser(@PathVariable Integer accountId){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.retrieveAllMessageForUser(accountId));
    }
}
