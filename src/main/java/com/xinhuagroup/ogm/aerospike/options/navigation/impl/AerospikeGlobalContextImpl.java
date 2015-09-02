package com.xinhuagroup.ogm.aerospike.options.navigation.impl;

import org.hibernate.ogm.datastore.document.options.AssociationStorageType;
import org.hibernate.ogm.datastore.document.options.MapStorageType;
import org.hibernate.ogm.datastore.document.options.spi.AssociationStorageOption;
import org.hibernate.ogm.datastore.document.options.spi.MapStorageOption;
import org.hibernate.ogm.datastore.keyvalue.options.navigation.spi.BaseKeyValueStoreGlobalContext;
import org.hibernate.ogm.options.navigation.spi.ConfigurationContext;
import org.hibernate.ogm.util.impl.Contracts;

import com.xinhuagroup.ogm.aerospike.options.navigation.AerospikeEntityContext;
import com.xinhuagroup.ogm.aerospike.options.navigation.AerospikeGlobalContext;

public class AerospikeGlobalContextImpl extends BaseKeyValueStoreGlobalContext<AerospikeGlobalContext, AerospikeEntityContext> implements AerospikeGlobalContext{

	public AerospikeGlobalContextImpl(ConfigurationContext context) {
		super(context);
	}

	@Override
	public AerospikeEntityContext entity(Class<?> type) {
		return null;
	}

	@Override
	public AerospikeGlobalContext associationStorage(AssociationStorageType associationStorage) {
		Contracts.assertParameterNotNull(associationStorage, "associationStorageType");
		addGlobalOption(new AssociationStorageOption(), associationStorage);
		return this;
	}

	@Override
	public AerospikeGlobalContext mapStorage(MapStorageType mapStorage) {
		Contracts.assertParameterNotNull(mapStorage, "mapStorage");
		addGlobalOption(new MapStorageOption(), mapStorage);
		return this;
	}

}
