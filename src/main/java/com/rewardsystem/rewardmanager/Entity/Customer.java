package com.rewardsystem.rewardmanager.Entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="customer_master")
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
	private Long customerId;
	
	@NotNull
	@NotBlank(message="Please enter your name")
	@Size(min=2, max=30,message=" Name should have atleast 2 characters")
   @Column(name = "customer_name")
	private String custName;
	
	@NotNull
	@NotBlank(message="Please enter your phone number")
	@Pattern(regexp="([7-9][0-9]{9})")
    @Column(name = "customer_mobile")
	private String custMobile;
	
	 @Column(name = "total_spent")
		private Double totalSpent;
	 
	 @Column(name = "reward_points")
		private Integer rewardPoints =0;
	 
	 @OneToMany(mappedBy = "customer")
	 private List<Transaction> transaction;

	public Long getCustomerId() {
		return customerId;
	}

	

	public String getCustName() {
		return custName;
	}

	

	public String getCustMobile() {
		return custMobile;
	}

	

	public Double getTotalSpent() {
		return totalSpent;
	}

	public void setRewardPoints(Integer rewardPoints) {
	    this.rewardPoints = rewardPoints;
	}

	public Integer getRewardPoints() {
		return rewardPoints;
	}

	public void setTotalSpent(Double totalSpent) {
		this.totalSpent = totalSpent;
	}

	



	 
	 

}
