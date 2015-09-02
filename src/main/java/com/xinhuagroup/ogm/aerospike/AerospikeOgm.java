package com.xinhuagroup.ogm.aerospike;

import org.hibernate.ogm.datastore.spi.DatastoreConfiguration;
import org.hibernate.ogm.options.navigation.spi.ConfigurationContext;

import com.xinhuagroup.ogm.aerospike.options.navigation.AerospikeGlobalContext;
import com.xinhuagroup.ogm.aerospike.options.navigation.impl.AerospikeEntityContextImpl;
import com.xinhuagroup.ogm.aerospike.options.navigation.impl.AerospikeGlobalContextImpl;
import com.xinhuagroup.ogm.aerospike.options.navigation.impl.AerospikePropertyContextImpl;

public class AerospikeOgm implements DatastoreConfiguration<AerospikeGlobalContext> {
	public static final String DATASTORE_PROVIDER_NAME = "AEROSPIKE_EXPERIMENTAL";

	@Override
	public AerospikeGlobalContext getConfigurationBuilder(ConfigurationContext context) {
		return context.createGlobalContext(AerospikeGlobalContextImpl.class, AerospikeEntityContextImpl.class, AerospikePropertyContextImpl.class);
	}

}
