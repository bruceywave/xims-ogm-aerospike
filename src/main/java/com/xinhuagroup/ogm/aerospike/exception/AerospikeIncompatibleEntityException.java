package com.xinhuagroup.ogm.aerospike.exception;

@SuppressWarnings("serial")
public class AerospikeIncompatibleEntityException extends AerospikeException{
	
	public AerospikeIncompatibleEntityException(String message){
		super(message);
	}
	
	public AerospikeIncompatibleEntityException(String message,Throwable th){
		super(message, th);
	}
}
