package com.xinhuagroup.ogm.aerospike.query.parsing.predicate.impl;

import java.util.List;

import org.hibernate.hql.ast.spi.predicate.InPredicate;
import org.hibernate.hql.ast.spi.predicate.NegatablePredicate;

import com.xinhuagroup.ogm.aerospike.dialect.value.Entity;

public class AerospikeInPredicate extends InPredicate<Entity> implements NegatablePredicate<Entity> {

	public AerospikeInPredicate(String propertyName, List<Object> values) {
		super(propertyName, values);
	}

	@Override
	public Entity getQuery() {
		Entity express = new Entity();
		express.set("$in", values);
		Entity entity = new Entity();
		entity.set(propertyName, express);
		return entity;
	}

	@Override
	public Entity getNegatedQuery() {
		Entity express = new Entity();
		express.set("$nin", values);
		Entity entity = new Entity();
		entity.set(propertyName, express);
		return entity;
	}

}
