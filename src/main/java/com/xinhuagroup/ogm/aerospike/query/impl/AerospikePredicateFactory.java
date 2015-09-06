package com.xinhuagroup.ogm.aerospike.query.impl;

import java.util.List;

import org.hibernate.hql.ast.spi.predicate.ComparisonPredicate;
import org.hibernate.hql.ast.spi.predicate.ComparisonPredicate.Type;
import org.hibernate.hql.ast.spi.predicate.ConjunctionPredicate;
import org.hibernate.hql.ast.spi.predicate.DisjunctionPredicate;
import org.hibernate.hql.ast.spi.predicate.InPredicate;
import org.hibernate.hql.ast.spi.predicate.IsNullPredicate;
import org.hibernate.hql.ast.spi.predicate.LikePredicate;
import org.hibernate.hql.ast.spi.predicate.NegationPredicate;
import org.hibernate.hql.ast.spi.predicate.PredicateFactory;
import org.hibernate.hql.ast.spi.predicate.RangePredicate;
import org.hibernate.hql.ast.spi.predicate.RootPredicate;

import com.xinhuagroup.ogm.aerospike.dialect.value.Entity;
import com.xinhuagroup.ogm.aerospike.query.parsing.predicate.impl.AerospikeComparisonPredicate;
import com.xinhuagroup.ogm.aerospike.query.parsing.predicate.impl.AerospikeConjunctionPredicate;
import com.xinhuagroup.ogm.aerospike.query.parsing.predicate.impl.AerospikeDisjunctionPredicate;
import com.xinhuagroup.ogm.aerospike.query.parsing.predicate.impl.AerospikeInPredicate;
import com.xinhuagroup.ogm.aerospike.query.parsing.predicate.impl.AerospikeIsNullPredicate;
import com.xinhuagroup.ogm.aerospike.query.parsing.predicate.impl.AerospikeLikePredicate;
import com.xinhuagroup.ogm.aerospike.query.parsing.predicate.impl.AerospikeNegationPredicate;
import com.xinhuagroup.ogm.aerospike.query.parsing.predicate.impl.AerospikeRangePredicate;
import com.xinhuagroup.ogm.aerospike.query.parsing.predicate.impl.AerospikeRootPredicate;

/**
 * 查询处理工厂
 * @author brucey
 *
 */
public class AerospikePredicateFactory implements PredicateFactory<Entity> {
	private final AerospikePropertyHelper propertyHelper;

	public AerospikePredicateFactory(AerospikePropertyHelper propertyHelper) {
		this.propertyHelper = propertyHelper;
	}

	@Override
	public RootPredicate<Entity> getRootPredicate(String entityType) {
		return new AerospikeRootPredicate();
	}

	@Override
	public ComparisonPredicate<Entity> getComparisonPredicate(String entityType, Type comparisonType, List<String> propertyPath, Object value) {
		String columnName = columnName( entityType, propertyPath );
		return new AerospikeComparisonPredicate( columnName, comparisonType, value );
	}

	private String columnName(String entityType, List<String> propertyPath) {
		return propertyHelper.getColumnName( entityType, propertyPath );
	}

	@Override
	public InPredicate<Entity> getInPredicate(String entityType, List<String> propertyPath, List<Object> typedElements) {
		String columnName = columnName( entityType, propertyPath );
		return new AerospikeInPredicate( columnName, typedElements );
	}

	@Override
	public RangePredicate<Entity> getRangePredicate(String entityType, List<String> propertyPath, Object lowerValue, Object upperValue) {
		String columnName = columnName( entityType, propertyPath );
		return new AerospikeRangePredicate( columnName, lowerValue, upperValue );
	}

	@Override
	public NegationPredicate<Entity> getNegationPredicate() {
		return new AerospikeNegationPredicate();
	}

	@Override
	public DisjunctionPredicate<Entity> getDisjunctionPredicate() {
		return new AerospikeDisjunctionPredicate();
	}

	@Override
	public ConjunctionPredicate<Entity> getConjunctionPredicate() {
		return new AerospikeConjunctionPredicate();
	}

	@Override
	public LikePredicate<Entity> getLikePredicate(String entityType, List<String> propertyPath, String patternValue, Character escapeCharacter) {
		String columnName = columnName( entityType, propertyPath );
		return new AerospikeLikePredicate( columnName, patternValue, escapeCharacter );
	}

	@Override
	public IsNullPredicate<Entity> getIsNullPredicate(String entityType, List<String> propertyPath) {
		String columnName = columnName( entityType, propertyPath );
		return new AerospikeIsNullPredicate( columnName );
	}

}
