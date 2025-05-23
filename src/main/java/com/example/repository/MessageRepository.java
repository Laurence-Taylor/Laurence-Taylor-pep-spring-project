package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Integer> {

    List<Message> findByPostedByAndMessageTextAndTimePostedEpoch(Integer postedBy, String messageText, Long timePostedEpoch);

    List<Message> findByPostedBy(Integer postedBy);
}
