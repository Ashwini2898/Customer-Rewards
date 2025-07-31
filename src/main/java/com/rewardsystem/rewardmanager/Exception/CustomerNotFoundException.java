package com.rewardsystem.rewardmanager.Exception;

public class CustomerNotFoundException extends Exception{
	
	private String message;
	
	public CustomerNotFoundException(String message) {
        super(message);
    }

	@Override
	public String toString() {
		return "CustomerNotFoundException [message=" + message + "]";
	}
}
