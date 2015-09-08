package com.xinhuagroup.ogm.aerospike.query.parsing.nativequery.impl;

import java.util.Map;

import com.xinhuagroup.ogm.aerospike.dialect.value.Entity;
import com.xinhuagroup.ogm.aerospike.query.impl.AerospikeQueryDescriptor;
import com.xinhuagroup.ogm.aerospike.query.impl.AerospikeQueryDescriptor.Operation;
import com.xinhuagroup.ogm.aerospike.util.JSON;

/**
 * 实体查询描述创建器
 * @author brucey
 *
 */
public class AerospikeQueryDescriptorBuilder {
	private String collection;
	private Operation operation;
	private String criteria;
	private String projection;
	
	@SuppressWarnings("unchecked")
	public AerospikeQueryDescriptor build() {
		Entity criteriaEntity = new Entity(JSON.deserialize(criteria, Map.class));
		Entity projectionEntity = new Entity(JSON.deserialize(projection, Map.class));
		return new AerospikeQueryDescriptor(null,collection, operation,criteriaEntity,projectionEntity, null, null);
	}
	public boolean setCollection(String collection) {
		this.collection = collection.trim();
		return true;
	}

	public boolean setOperation(Operation operation) {
		this.operation = operation;
		return true;
	};

	public boolean setCriteria(String criteria) {
		this.criteria = criteria;
		return true;
	}

	public boolean setProjection(String projection) {
		this.projection = projection;
		return true;
	}
}
