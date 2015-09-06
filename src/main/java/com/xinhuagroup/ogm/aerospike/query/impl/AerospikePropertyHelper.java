package com.xinhuagroup.ogm.aerospike.query.impl;

import java.util.List;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.hql.ast.spi.EntityNamesResolver;
import org.hibernate.hql.ast.spi.PropertyHelper;
import org.hibernate.ogm.persister.impl.OgmEntityPersister;
import org.hibernate.ogm.query.parsing.impl.ParserPropertyHelper;
import org.hibernate.ogm.util.impl.StringHelper;

import com.xinhuagroup.ogm.aerospike.AerospikeDialect;

public class AerospikePropertyHelper extends ParserPropertyHelper implements PropertyHelper {
	public AerospikePropertyHelper(SessionFactoryImplementor sessionFactory, EntityNamesResolver entityNames) {
		super(sessionFactory, entityNames);
	}

	public String getColumnName(Class<?> entityType, List<String> propertyName) {
		return getColumnName((OgmEntityPersister) getSessionFactory().getEntityPersister(entityType.getName()), propertyName);
	}

	public String getColumnName(String entityType, List<String> propertyPath) {
		return getColumnName(getPersister(entityType), propertyPath);
	}

	public String getColumnName(OgmEntityPersister persister, List<String> propertyPath) {
		String propertyName = StringHelper.join(propertyPath, ".");
		String identifierPropertyName = persister.getIdentifierPropertyName();
		if (propertyName.equals(identifierPropertyName)) {
			return AerospikeDialect.ID_FIELDNAME;
		}
		String column = getColumn(persister, propertyPath);
		if (propertyPath.size() > 1 && propertyPath.get(0).equals(identifierPropertyName)) {
			column = AerospikeDialect.ID_FIELDNAME + "." + column.substring(propertyPath.get(0).length() + 1);
		}
		return column;
	}
}
