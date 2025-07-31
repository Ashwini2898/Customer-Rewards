package com.rewardsystem.rewardmanager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rewardsystem.rewardmanager.Entity.Customer;

@Repository
public interface CustomerRepositoryDao extends JpaRepository<Customer,Integer> {

}
