package com.xinhuagroup.ogm.aerospike.query.parsing.predicate.impl;

import org.hibernate.hql.ast.spi.predicate.NegatablePredicate;
import org.hibernate.hql.ast.spi.predicate.NegationPredicate;

import com.xinhuagroup.ogm.aerospike.dialect.value.Entity;

public class AerospikeNegationPredicate extends NegationPredicate<Entity> implements NegatablePredicate<Entity>{

	@Override
	public Entity getQuery() {
		return ( (NegatablePredicate<Entity>) getChild() ).getNegatedQuery();
	}

	@Override
	public Entity getNegatedQuery() {
		return getChild().getQuery();
	}

}
