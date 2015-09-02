package com.xinhuagroup.ogm.aerospike.options.navigation.impl;

import java.lang.annotation.ElementType;

import org.hibernate.ogm.datastore.document.options.AssociationStorageType;
import org.hibernate.ogm.datastore.document.options.MapStorageType;
import org.hibernate.ogm.datastore.document.options.spi.AssociationStorageOption;
import org.hibernate.ogm.datastore.document.options.spi.MapStorageOption;
import org.hibernate.ogm.datastore.keyvalue.options.navigation.spi.BaseKeyValueStorePropertyContext;
import org.hibernate.ogm.options.navigation.spi.ConfigurationContext;
import org.hibernate.ogm.util.impl.Contracts;

import com.xinhuagroup.ogm.aerospike.options.navigation.AerospikeEntityContext;
import com.xinhuagroup.ogm.aerospike.options.navigation.AerospikePropertyContext;

public class AerospikePropertyContextImpl extends BaseKeyValueStorePropertyContext<AerospikeEntityContext, AerospikePropertyContext> implements AerospikePropertyContext {

	public AerospikePropertyContextImpl(ConfigurationContext context) {
		super(context);
	}

	@Override
	public AerospikeEntityContext entity(Class<?> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AerospikePropertyContext property(String propertyName, ElementType target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AerospikePropertyContext associationStorage(AssociationStorageType storage) {
		Contracts.assertParameterNotNull(storage, "associationStorageType");
		addPropertyOption(new AssociationStorageOption(), storage);
		return this;
	}

	@Override
	public AerospikePropertyContext mapStorage(MapStorageType mapStorage) {
		Contracts.assertParameterNotNull(mapStorage, "mapStorage");
		addPropertyOption(new MapStorageOption(), mapStorage);
		return this;
	}

}
