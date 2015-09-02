package com.xinhuagroup.ogm.aerospike.dialect.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.hibernate.ogm.datastore.document.impl.DotPatternMapHelpers;
import org.hibernate.ogm.model.key.spi.AssociationKeyMetadata;

import com.xinhuagroup.ogm.aerospike.dialect.value.Entity;

public class EmbeddedAssociation extends AbstractAerospikeAssociation {
	private final AssociationKeyMetadata associationKeyMetadata;
	private final Entity entity;

	public EmbeddedAssociation(Entity entity, AssociationKeyMetadata associationKeyMetadata) {
		this.associationKeyMetadata = associationKeyMetadata;
		this.entity = entity;
	}

	@Override
	public Object getRows() {
		Object rows = null;
		Object fieldValue = DotPatternMapHelpers.getValueOrNull(entity.getPropertiesAsHierarchy(), associationKeyMetadata.getCollectionRole());
		if (fieldValue == null) {
			rows = Collections.emptyList();
		} else if (associationKeyMetadata.isOneToOne()) {
			rows = fieldValue;
		} else {
			rows = fieldValue;
		}

		return rows;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setRows(Object rows) {
		if (isEmpty(rows)) {
			entity.removeAssociation(associationKeyMetadata.getCollectionRole());
		} else {
			entity.removeAssociation(associationKeyMetadata.getCollectionRole());
			if (associationKeyMetadata.isOneToOne() && rows instanceof Collection) {
				Object value = ((Collection) rows).iterator().next();
				entity.set(associationKeyMetadata.getCollectionRole(), value);
			} else {
				entity.set(associationKeyMetadata.getCollectionRole(), rows);
			}
		}
	}

	@Override
	public Object getOwningDocument() {
		return entity;
	}

	protected boolean isEmpty(Object rows) {
		if (rows == null) {
			return true;
		}
		if (rows instanceof Collection<?> && ((Collection<?>) rows).isEmpty()) {
			return true;
		}
		if (rows instanceof Map<?, ?> && ((Map<?, ?>) rows).isEmpty()) {
			return true;
		}
		return false;
	}
}
