package com.rewardsystem.rewardmanager.rewardRepository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rewardsystem.rewardmanager.rewardEntity.Transaction;

/**
 * This interface extends JpaRepository, providing basic CRUD operations for managing transactions
 * 
 *
 */
@Repository
public interface TransactionRepositoryDao extends JpaRepository<Transaction,Long>{

	List<Transaction> findAllByCustomer_CustomerIdAndDateBetween(Long customerId, LocalDateTime start, LocalDateTime end);

	List<Transaction> findAllByCustomer_CustomerId(Long customerId);
}
