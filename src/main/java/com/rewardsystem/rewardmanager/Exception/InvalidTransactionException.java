package com.rewardsystem.rewardmanager.Exception;

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
