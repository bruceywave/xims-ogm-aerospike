package com.xinhuagroup.ogm.aerospike.dialect.model;

import org.hibernate.ogm.model.key.spi.AssociationKeyMetadata;

import com.xinhuagroup.ogm.aerospike.dialect.value.DocumentAssociation;
import com.xinhuagroup.ogm.aerospike.dialect.value.Entity;

public abstract class AbstractAerospikeAssociation {

	public static AbstractAerospikeAssociation fromEmbeddedAssociation(Entity entity, AssociationKeyMetadata associationKeyMetadata) {
		return new EmbeddedAssociation(entity, associationKeyMetadata);
	}

	/**
	 * 获取行记录
	 * @return
	 */
	public abstract Object getRows();

	/**
	 * 设置行记录
	 * @param rows
	 */
	public abstract void setRows(Object rows);

	public abstract Object getOwningDocument();

	public static DocumentBasedAssociation fromAssociationDocument(DocumentAssociation association) {
		return new DocumentBasedAssociation(association);
	}
}
