package com.xinhuagroup.ogm.aerospike.query.impl;

import java.util.Map;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.hql.QueryParser;
import org.hibernate.hql.ast.spi.EntityNamesResolver;
import org.hibernate.ogm.query.spi.BaseQueryParserService;
import org.hibernate.ogm.query.spi.QueryParsingResult;
import org.hibernate.ogm.service.impl.SessionFactoryEntityNamesResolver;

/**
 * 自定义查询解析服务
 * 
 * @author brucey
 *
 */
@SuppressWarnings("serial")
public class AerospikeQueryParserService extends BaseQueryParserService {

	private volatile EntityNamesResolver entityNamesResolver;

	@Override
	public boolean supportsParameters() {
		return false;
	}

	@Override
	public QueryParsingResult parseQuery(SessionFactoryImplementor sessionFactory, String queryString, Map<String, Object> namedParameters) {
		QueryParser queryParser = new QueryParser();
		AerospikeProcessingChain processingChain = createProcessingChain(sessionFactory, unwrap(namedParameters));
		AerospikeQueryParserResult result = queryParser.parseQuery(queryString, processingChain);
		// log.createdQuery( queryString, result );
		return result;
	}

	private AerospikeProcessingChain createProcessingChain(SessionFactoryImplementor sessionFactory, Map<String, Object> unwrap) {
		EntityNamesResolver entityNamesResolver = getDefinedEntityNames(sessionFactory);
		return new AerospikeProcessingChain(sessionFactory, entityNamesResolver, unwrap);
	}

	private EntityNamesResolver getDefinedEntityNames(SessionFactoryImplementor sessionFactory) {
		if (entityNamesResolver == null) {
			entityNamesResolver = new SessionFactoryEntityNamesResolver(sessionFactory);
		}
		return entityNamesResolver;
	}

	@Override
	public QueryParsingResult parseQuery(SessionFactoryImplementor sessionFactory, String queryString) {
		throw new UnsupportedOperationException("Aerospike does not support parameterized queries. Parameter values " + "must be passed to the query parser.");
	}

}
