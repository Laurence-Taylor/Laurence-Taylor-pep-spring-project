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

    // Create a New Message
    public Message createMessage(Message newMessage){
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

    // Returns all Messages
    public List<Message> retrieveAllMessage(){return (List<Message>) messageRepository.findAll();}
    
    // Returns a message given its message identifier.
    public Message retrieveMessageByMessageId(Integer messageId) {
        Optional<Message> messageToReturn = messageRepository.findById(messageId);
        if(messageToReturn.isPresent()){
            return messageToReturn.get();
        }else{
            return null;
        }
    }

    // Deletes a message given its message identifier.
    public void deleteMessageByMessageId(Integer messageId){
        messageRepository.deleteById(messageId);
    }

    // Update Message
    public Boolean updateMessage(Integer messageId, Message messageTex) throws MessageHandleExceptions{
        if ((messageTex.getMessageText() != "") && (messageTex.getMessageText().length() < 255)){
            Message messageToUpdate = messageRepository.findById(messageId)
                                    .orElseThrow(()->new MessageHandleExceptions(messageId + " was not found. Please Check another MessageId."));
            messageToUpdate.setMessageText(messageTex.getMessageText());
            messageRepository.save(messageToUpdate);
            return true;
        }else{
            return false;
        }
    }

    // Retrieve all message from a User
    public List<Message> retrieveAllMessageForUser(Integer accountId){
        return messageRepository.findByPostedBy(accountId);
        }
}
