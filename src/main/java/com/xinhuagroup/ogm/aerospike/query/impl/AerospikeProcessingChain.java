package com.xinhuagroup.ogm.aerospike.query.impl;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.hql.ast.spi.AstProcessingChain;
import org.hibernate.hql.ast.spi.AstProcessor;
import org.hibernate.hql.ast.spi.EntityNamesResolver;
import org.hibernate.hql.ast.spi.QueryRendererProcessor;
import org.hibernate.hql.ast.spi.QueryResolverProcessor;

public class AerospikeProcessingChain implements AstProcessingChain<AerospikeQueryParserResult>{
	private final QueryResolverProcessor resolverProcessor;
	private final QueryRendererProcessor rendererProcessor;
	private final AerospikeQueryRendererDelegate rendererDelegate;

	public AerospikeProcessingChain(SessionFactoryImplementor sessionFactory, EntityNamesResolver entityNames, Map<String, Object> namedParameters) {
		this.resolverProcessor = new QueryResolverProcessor( new AerospikeResolverDelegate() );

		AerospikePropertyHelper propertyHelper = new AerospikePropertyHelper( sessionFactory, entityNames );
		AerospikeQueryRendererDelegate rendererDelegate = new AerospikeQueryRendererDelegate(
				sessionFactory,
				entityNames,
				propertyHelper,
				namedParameters );
		this.rendererProcessor = new QueryRendererProcessor( rendererDelegate );
		this.rendererDelegate = rendererDelegate;
	}

	@Override
	public Iterator<AstProcessor> iterator() {
		return Arrays.asList( resolverProcessor, rendererProcessor ).iterator();
	}

	@Override
	public AerospikeQueryParserResult getResult() {
		return rendererDelegate.getResult();
	}

}
