package com.example.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.MessageHandleExceptions;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
@Transactional
public class MessageService {

    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message newMessage) throws MessageHandleExceptions{
        if ((newMessage.getMessageText()!="")&&
        (newMessage.getMessageText().length()<=255) &&
        accountRepository.existsById(newMessage.getPostedBy())){
            messageRepository.save(newMessage);
            List<Message> listMessage = messageRepository
                                                .findByPostedByAndMessageTextAndTimePostedEpoch(newMessage.getPostedBy(), 
                                                                                                newMessage.getMessageText(), 
                                                                                                newMessage.getTimePostedEpoch());
            return listMessage.get(0);
        }
        return null;
    }

    public List<Message> retrieveAllMessage(){return (List<Message>) messageRepository.findAll();}
        
    public Message retrieveMessageById(Integer messageId){
        Optional<Message> messageToReturn = messageRepository.findById(messageId);
        if(messageToReturn.isPresent()){
            return messageToReturn.get();
        }else{
            return null;
        }
    }

}
