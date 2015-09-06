package com.xinhuagroup.ogm.aerospike.query.parsing.predicate.impl;

import org.hibernate.hql.ast.spi.predicate.RootPredicate;

import com.xinhuagroup.ogm.aerospike.dialect.value.Entity;

public class AerospikeRootPredicate extends RootPredicate<Entity>{

	@Override
	public Entity getQuery() {
		return child == null ? new Entity() : child.getQuery();
	}

}
