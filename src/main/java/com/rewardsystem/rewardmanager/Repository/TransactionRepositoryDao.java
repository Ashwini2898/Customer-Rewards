package com.rewardsystem.rewardmanager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rewardsystem.rewardmanager.Entity.Transaction;

@Repository
public interface TransactionRepositoryDao extends JpaRepository<Transaction,Integer>{

}
