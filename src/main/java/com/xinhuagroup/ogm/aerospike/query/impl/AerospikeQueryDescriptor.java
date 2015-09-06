package com.xinhuagroup.ogm.aerospike.query.impl;

import java.io.Serializable;
import java.util.List;

import com.xinhuagroup.ogm.aerospike.dialect.value.Entity;

@SuppressWarnings("serial")
public class AerospikeQueryDescriptor implements Serializable {
	/**
	 * 操作枚举
	 * @author brucey
	 *
	 */
	public enum Operation {
		FIND, //查找
		COUNT,//统计
		AGGREGATE;//引用
	}
	
	private final String collectionName;
	private final Operation operation;
	private final Entity criteria;
	private final Entity projection;
	private final Entity orderBy;
	private final List<String> unwinds;
	
	public AerospikeQueryDescriptor(String collectionName, Operation operation, Entity criteria, Entity projection, Entity orderBy, List<String> unwinds) {
		this.collectionName = collectionName;
		this.operation = operation;
		this.criteria = criteria;
		this.projection = projection;
		this.orderBy = orderBy;
		this.unwinds = unwinds;
	}
	public String getCollectionName() {
		return collectionName;
	}
	public Operation getOperation() {
		return operation;
	}
	public Entity getCriteria() {
		return criteria;
	}
	public Entity getProjection() {
		return projection;
	}
	public Entity getOrderBy() {
		return orderBy;
	}
	public List<String> getUnwinds() {
		return unwinds;
	}
}
