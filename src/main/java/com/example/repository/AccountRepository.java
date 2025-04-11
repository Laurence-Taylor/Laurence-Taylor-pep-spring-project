package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer>{

List<Account> findByUsername(String username);
}
