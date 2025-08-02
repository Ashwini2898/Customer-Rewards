package com.rewardsystem.rewardmanager.Exception;


/**
 * Custom exception to be thrown when a resource (e.g. Transaction)is not found.
 */
public class InvalidTransactionException extends Exception{

	private String message;

	public InvalidTransactionException(String message) {
		super(message);
	}

	@Override
	public String toString() {
		return "InvalidTransactionException [message=" + message + "]";
	}   
}
