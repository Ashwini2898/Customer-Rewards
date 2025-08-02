package com.rewardsystem.rewardmanager.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rewardsystem.rewardmanager.Entity.Transaction;

/**
 * This interface extends JpaRepository, providing basic CRUD operations for managing transactions
 * 
 *
 */
@Repository
public interface TransactionRepositoryDao extends JpaRepository<Transaction,Long>{
	
	List<Transaction> findAllByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
	List<Transaction> findAllByCustomer_CustomerIdAndDateBetween(Long customerId, LocalDateTime start, LocalDateTime end);
}
