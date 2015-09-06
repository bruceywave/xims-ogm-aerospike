package com.xinhuagroup.ogm.aerospike.query.parsing.predicate.impl;

import java.util.Arrays;
import java.util.List;

import org.hibernate.hql.ast.spi.predicate.NegatablePredicate;
import org.hibernate.hql.ast.spi.predicate.RangePredicate;

import com.xinhuagroup.ogm.aerospike.dialect.value.Entity;

public class AerospikeRangePredicate extends RangePredicate<Entity> implements NegatablePredicate<Entity>{

	public AerospikeRangePredicate(String propertyName, Object lower, Object upper) {
		super(propertyName, lower, upper);
	}

	@Override
	public Entity getQuery() {
		Entity expressGte = new Entity();
		expressGte.set("$gte", upper);
		Entity expressLte = new Entity();
		expressLte.set("$lte", lower);
		List<Entity> entitys = Arrays.<Entity>asList(expressGte,expressLte);
		Entity entity = new Entity();
		entity.set("$and", entitys);
		return entity;
	}

	@Override
	public Entity getNegatedQuery() {
		Entity expressGt = new Entity();
		expressGt.set("$gt", upper);
		Entity expressLt = new Entity();
		expressLt.set("$lt", lower);
		List<Entity> entitys = Arrays.<Entity>asList(expressLt,expressGt);
		Entity entity = new Entity();
		entity.set("$or", entitys);
		return entity;
	}

}
