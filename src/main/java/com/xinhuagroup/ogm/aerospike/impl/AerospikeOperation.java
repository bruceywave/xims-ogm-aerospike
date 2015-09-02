package com.xinhuagroup.ogm.aerospike.impl;

import java.io.Serializable;

import org.hibernate.ogm.model.key.spi.EntityKey;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Key;

public class AerospikeOperation {
	private final AerospikeClient aerospikeClient;
	private final AerospikeClientPolicy clientPolicy;
	
	public AerospikeOperation(AerospikeClient aerospikeClient,AerospikeClientPolicy clientPolicy){
		this.aerospikeClient = aerospikeClient;
		this.clientPolicy = clientPolicy;
	}
	
	public <T extends Serializable> T getEntity(EntityKey key){
		return null;
	}
	
	public Key createKey(EntityKey key){
		for (int i = 0; i < key.getColumnNames().length; i++) {
			
		}
		return null;
	}
}
