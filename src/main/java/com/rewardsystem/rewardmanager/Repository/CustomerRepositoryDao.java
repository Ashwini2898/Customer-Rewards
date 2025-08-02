package com.rewardsystem.rewardmanager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.rewardsystem.rewardmanager.Entity.Customer;

/**
 * This interface extends JpaRepository, providing basic CRUD operations for managing customers
 * 
 *
 */
@Repository
public interface CustomerRepositoryDao extends JpaRepository<Customer,Long> {}
