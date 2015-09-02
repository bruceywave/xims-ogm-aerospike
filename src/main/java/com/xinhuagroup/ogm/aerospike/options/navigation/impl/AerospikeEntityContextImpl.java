package com.xinhuagroup.ogm.aerospike.options.navigation.impl;

import java.lang.annotation.ElementType;

import org.hibernate.ogm.datastore.document.options.AssociationStorageType;
import org.hibernate.ogm.datastore.document.options.MapStorageType;
import org.hibernate.ogm.datastore.document.options.spi.AssociationStorageOption;
import org.hibernate.ogm.datastore.document.options.spi.MapStorageOption;
import org.hibernate.ogm.datastore.keyvalue.options.navigation.spi.BaseKeyValueStoreEntityContext;
import org.hibernate.ogm.options.navigation.spi.ConfigurationContext;
import org.hibernate.ogm.util.impl.Contracts;

import com.xinhuagroup.ogm.aerospike.options.navigation.AerospikeEntityContext;
import com.xinhuagroup.ogm.aerospike.options.navigation.AerospikePropertyContext;

public class AerospikeEntityContextImpl extends BaseKeyValueStoreEntityContext<AerospikeEntityContext, AerospikePropertyContext> implements AerospikeEntityContext{

	public AerospikeEntityContextImpl(ConfigurationContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public AerospikeEntityContext entity(Class<?> type) {
		return this;
	}

	@Override
	public AerospikePropertyContext property(String propertyName, ElementType target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AerospikeEntityContext associationStorage(AssociationStorageType associationStorage) {
		Contracts.assertParameterNotNull(associationStorage, "associationStorageType");
		addEntityOption(new AssociationStorageOption(), associationStorage);
		return this;
	}

	@Override
	public AerospikeEntityContext mapStorage(MapStorageType mapStorage) {
		Contracts.assertParameterNotNull(mapStorage, "mapStorage");
		addEntityOption(new MapStorageOption(), mapStorage);
		return this;
	}

}
