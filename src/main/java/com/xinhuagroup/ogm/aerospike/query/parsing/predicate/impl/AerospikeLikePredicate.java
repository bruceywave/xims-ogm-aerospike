package com.xinhuagroup.ogm.aerospike.query.parsing.predicate.impl;

import java.util.regex.Pattern;

import org.hibernate.hql.ast.spi.predicate.LikePredicate;
import org.hibernate.hql.ast.spi.predicate.NegatablePredicate;
import org.hibernate.ogm.util.parser.impl.LikeExpressionToRegExpConverter;

import com.xinhuagroup.ogm.aerospike.dialect.value.Entity;

public class AerospikeLikePredicate extends LikePredicate<Entity> implements NegatablePredicate<Entity>{

	private final Pattern pattern;

	public AerospikeLikePredicate(String propertyName, String patternValue, Character escapeCharacter) {
		super(propertyName, patternValue, escapeCharacter);
		LikeExpressionToRegExpConverter converter = new LikeExpressionToRegExpConverter( escapeCharacter );
		pattern = converter.getRegExpFromLikeExpression( patternValue );
	}

	@Override
	public Entity getQuery() {
		Entity entity = new Entity();
		entity.set(propertyName, pattern);
		return entity;
	}

	@Override
	public Entity getNegatedQuery() {
		Entity express = new Entity();
		express.set("$not", pattern);
		Entity entity = new Entity();
		entity.set(propertyName, express);
		return entity;
	}

}
