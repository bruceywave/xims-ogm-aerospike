package com.xinhuagroup.ogm.aerospike.query.parsing.predicate.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.hql.ast.spi.predicate.DisjunctionPredicate;
import org.hibernate.hql.ast.spi.predicate.NegatablePredicate;
import org.hibernate.hql.ast.spi.predicate.Predicate;

import com.xinhuagroup.ogm.aerospike.dialect.value.Entity;

public class AerospikeDisjunctionPredicate extends DisjunctionPredicate<Entity> implements NegatablePredicate<Entity> {

	@Override
	public Entity getQuery() {
		List<Entity> elements = new ArrayList<Entity>();

		for (Predicate<Entity> child : children) {
			elements.add(child.getQuery());
		}

		Entity entity = new Entity();
		entity.set("$or", elements);
		return entity;
	}

	@Override
	public Entity getNegatedQuery() {
		List<Entity> elements = new ArrayList<Entity>();
		for (Predicate<Entity> child : children) {
			elements.add(((NegatablePredicate<Entity>) child).getNegatedQuery());
		}

		Entity entity = new Entity();
		entity.set("$and", elements);
		return entity;
	}

}
