package com.xinhuagroup.ogm.aerospike.query.parsing.predicate.impl;

import org.hibernate.hql.ast.spi.predicate.ComparisonPredicate;
import org.hibernate.hql.ast.spi.predicate.NegatablePredicate;

import com.xinhuagroup.ogm.aerospike.dialect.value.Entity;

public class AerospikeComparisonPredicate extends ComparisonPredicate<Entity> implements NegatablePredicate<Entity>{

	public AerospikeComparisonPredicate(String propertyName, ComparisonPredicate.Type type, Object value) {
		super(propertyName, type, value);
	}

	@Override
	protected Entity getStrictlyLessQuery() {
		Entity express = new Entity();
		express.set("$lt", value);
		Entity entity = new Entity();
		entity.set(propertyName, express);
		return entity;
	}

	@Override
	protected Entity getLessOrEqualsQuery() {
		Entity express = new Entity();
		express.set("$lte", value);
		Entity entity = new Entity();
		entity.set(propertyName, express);
		return entity;
	}

	@Override
	protected Entity getEqualsQuery() {
		Entity entity = new Entity();
		entity.set(propertyName, value);
		return entity;
	}

	@Override
	protected Entity getGreaterOrEqualsQuery() {
		Entity express = new Entity();
		express.set("$gte", value);
		Entity entity = new Entity();
		entity.set(propertyName, express);
		return entity;
	}

	@Override
	protected Entity getStrictlyGreaterQuery() {
		Entity express = new Entity();
		express.set("$gt", value);
		Entity entity = new Entity();
		entity.set(propertyName, express);
		return entity;
	}

	@Override
	public Entity getNegatedQuery() {
		Entity express = new Entity();
		Entity entity = new Entity();
		entity.set(propertyName, express);
		switch ( type ) {
			case LESS:
				express.set("$gte", value);
				return entity;
			case LESS_OR_EQUAL:
				express.set("$gt", value);
				return entity;
			case EQUALS:
				express.set("$ne", value);
				return entity;
			case GREATER_OR_EQUAL:
				express.set("$lt", value);
				return entity;
			case GREATER:
				express.set("$lte", value);
				return entity;
			default:
				throw new UnsupportedOperationException( "Unsupported comparison type: " + type );
		}
	}

}
