package com.rewardsystem.rewardmanager.Exception;

/**
 * Custom exception to be thrown when a resource (e.g., Customer)is not found.
 */
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
