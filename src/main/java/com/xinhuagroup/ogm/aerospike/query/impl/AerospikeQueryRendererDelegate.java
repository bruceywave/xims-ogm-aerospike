package com.xinhuagroup.ogm.aerospike.query.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.hql.ast.origin.hql.resolve.path.PropertyPath;
import org.hibernate.hql.ast.spi.EntityNamesResolver;
import org.hibernate.hql.ast.spi.SingleEntityQueryBuilder;
import org.hibernate.hql.ast.spi.SingleEntityQueryRendererDelegate;
import org.hibernate.ogm.persister.impl.OgmEntityPersister;
import org.hibernate.ogm.util.impl.StringHelper;

import com.xinhuagroup.ogm.aerospike.dialect.value.Entity;

public class AerospikeQueryRendererDelegate extends SingleEntityQueryRendererDelegate<Entity, AerospikeQueryParserResult> {

	private final SessionFactoryImplementor sessionFactory;
	private final AerospikePropertyHelper propertyHelper;
	private Entity orderBy;
	/*
	 * The fields for which needs to be aggregated using $unwind when running
	 * the query
	 */
	private List<String> unwinds;

	public AerospikeQueryRendererDelegate(SessionFactoryImplementor sessionFactory, EntityNamesResolver entityNames, AerospikePropertyHelper propertyHelper, Map<String, Object> namedParameters) {
		super(propertyHelper, entityNames, SingleEntityQueryBuilder.getInstance(new AerospikePredicateFactory(propertyHelper), propertyHelper), namedParameters);
		this.sessionFactory = sessionFactory;
		this.propertyHelper = propertyHelper;
	}

	@Override
	public void setPropertyPath(PropertyPath propertyPath) {
		if (status == Status.DEFINING_SELECT) {
			List<String> pathWithoutAlias = resolveAlias(propertyPath);
			if (propertyHelper.isSimpleProperty(pathWithoutAlias)) {
				projections.add(propertyHelper.getColumnName(targetTypeName, propertyPath.getNodeNamesWithoutAlias()));
			} else if (propertyHelper.isNestedProperty(pathWithoutAlias)) {
				if (propertyHelper.isEmbeddedProperty(targetTypeName, pathWithoutAlias)) {
					String columnName = propertyHelper.getColumnName(targetTypeName, pathWithoutAlias);
					projections.add(columnName);
					List<String> associationPath = propertyHelper.findAssociationPath(targetTypeName, pathWithoutAlias);
					// Currently, it is possible to nest only one association
					// inside an embedded
					if (associationPath != null) {
						if (unwinds == null) {
							unwinds = new ArrayList<String>();
						}
						String field = StringHelper.join(associationPath, ".");
						if (!unwinds.contains(field)) {
							unwinds.add(field);
						}
					}
				} else {
					throw new UnsupportedOperationException("Selecting associated properties not yet implemented.");
				}
			}
		} else {
			this.propertyPath = propertyPath;
		}
	}

	@Override
	public AerospikeQueryParserResult getResult() {
		OgmEntityPersister entityPersister = (OgmEntityPersister) sessionFactory.getEntityPersister(targetType.getName());
		return new AerospikeQueryParserResult(targetType, entityPersister.getTableName(), builder.build(), getProjectionEntity(), orderBy, unwinds);
	}

	private Entity getProjectionEntity() {
		if (projections.isEmpty()) {
			return null;
		}
		Entity projectionDBObject = new Entity();
		for (String projection : projections) {
			projectionDBObject.set(projection, 1);
		}
		return projectionDBObject;
	}

	@Override
	protected void addSortField(PropertyPath propertyPath, String collateName, boolean isAscending) {
		if (orderBy == null) {
			orderBy = new Entity();
		}

		String columnName = propertyHelper.getColumnName(targetType, propertyPath.getNodeNamesWithoutAlias());

		// BasicDBObject is essentially a LinkedHashMap, so in case of several
		// sort keys they'll be evaluated in the
		// order they're inserted here, which is the order within the original
		// statement
		orderBy.set(columnName, isAscending ? 1 : -1);
	}

}
