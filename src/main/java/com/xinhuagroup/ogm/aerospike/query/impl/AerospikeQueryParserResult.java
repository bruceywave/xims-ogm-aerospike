package com.xinhuagroup.ogm.aerospike.query.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.ogm.query.spi.QueryParsingResult;

import com.xinhuagroup.ogm.aerospike.dialect.value.Entity;
import com.xinhuagroup.ogm.aerospike.query.impl.AerospikeQueryDescriptor.Operation;

public class AerospikeQueryParserResult implements QueryParsingResult{
	private final Class<?> entityType;
	private final String collectionName;
	private final Entity query;
	private final Entity projection;
	private final Entity orderBy;
	private final List<String> unwinds;

	public AerospikeQueryParserResult(Class<?> targetType, String tableName, Entity build, Entity projectionEntity, Entity orderBy, List<String> unwinds) {
		this.entityType = targetType;
		this.collectionName = tableName;
		this.query = build;
		this.projection = projectionEntity;
		this.orderBy = orderBy;
		this.unwinds = unwinds;
	}

	@Override
	public Object getQueryObject() {
		return new AerospikeQueryDescriptor(
				collectionName,
				unwinds == null ? Operation.FIND : Operation.AGGREGATE,
				query,
				projection,
				orderBy,
				unwinds
			);
	}

	@Override
	public List<String> getColumnNames() {
		return projection != null ? new ArrayList<String>( projection.keySet() ) : Collections.<String>emptyList();
	}

	public Class<?> getEntityType() {
		return entityType;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public Entity getQuery() {
		return query;
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
