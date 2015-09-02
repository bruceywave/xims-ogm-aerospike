package com.xinhuagroup.ogm.aerospike.dialect.model;

import org.hibernate.ogm.datastore.document.association.spi.AssociationRows;
import org.hibernate.ogm.datastore.document.impl.MapAssociationRowsHelpers;
import org.hibernate.ogm.model.key.spi.AssociationKey;

public class AerospikeAssociationSnapshot extends AssociationRows{
	
	private final AbstractAerospikeAssociation aerospikeAssociation;
	
	public AerospikeAssociationSnapshot(AbstractAerospikeAssociation association, AssociationKey key) {
		super( key, MapAssociationRowsHelpers.getRows( association.getRows(), key ), AerospikeAssociationRowFactory.INSTANCE );
		this.aerospikeAssociation = association;
	}

	public AbstractAerospikeAssociation getAerospikeAssociation() {
		return aerospikeAssociation;
	}

}
