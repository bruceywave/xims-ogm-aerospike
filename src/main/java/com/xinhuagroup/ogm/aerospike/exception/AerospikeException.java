package com.xinhuagroup.ogm.aerospike.exception;

public class AerospikeException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public AerospikeException(String message){
		super(message);
	}
	
	public AerospikeException(String message,Throwable throwable){
		super(message, throwable);
	}
}
