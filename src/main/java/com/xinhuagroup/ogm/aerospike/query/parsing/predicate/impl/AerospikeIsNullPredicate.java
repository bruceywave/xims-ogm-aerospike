package com.xinhuagroup.ogm.aerospike.query.parsing.predicate.impl;

import org.hibernate.hql.ast.spi.predicate.IsNullPredicate;
import org.hibernate.hql.ast.spi.predicate.NegatablePredicate;

import com.xinhuagroup.ogm.aerospike.dialect.value.Entity;

public class AerospikeIsNullPredicate extends IsNullPredicate<Entity> implements NegatablePredicate<Entity> {

	public AerospikeIsNullPredicate(String propertyName) {
		super(propertyName);
	}

	@Override
	public Entity getQuery() {
		Entity express = new Entity();
		express.set( "$exists", false);
		Entity entity = new Entity();
		entity.set(propertyName, express);
		return entity;
	}

	@Override
	public Entity getNegatedQuery() {
		Entity express = new Entity();
		express.set( "$exists", true);
		Entity entity = new Entity();
		entity.set(propertyName, express);
		return entity;
	}

}
