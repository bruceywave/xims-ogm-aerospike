package com.xinhuagroup.ogm.aerospike.query.parsing.predicate.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.hql.ast.spi.predicate.ConjunctionPredicate;
import org.hibernate.hql.ast.spi.predicate.NegatablePredicate;
import org.hibernate.hql.ast.spi.predicate.Predicate;

import com.xinhuagroup.ogm.aerospike.dialect.value.Entity;

public class AerospikeConjunctionPredicate extends ConjunctionPredicate<Entity> implements NegatablePredicate<Entity> {

	@Override
	public Entity getQuery() {
		Entity entity = new Entity();
		for (Predicate<Entity> child : children) {
			for (Map.Entry<String, Object> entry: child.getQuery().getProperties().entrySet()) {
				entity.set(entry.getKey(), entry.getValue());
			}
//			entity.set(child.getQuery().getProperties().ge, value);
//			elements.add(child.getQuery());
//			child.getQuery().getPropertiesAsHierarchy()
		}
//		Entity entity = new Entity();
//		entity.set("$and", elements);
		return entity;
	}

	@Override
	public Entity getNegatedQuery() {
		List<Entity> elements = new ArrayList<Entity>();

		for (Predicate<Entity> child : children) {
			elements.add(((NegatablePredicate<Entity>) child).getNegatedQuery());
		}

		Entity entity = new Entity();
		entity.set("$or", elements);
		return entity;
	}

}
