package com.xinhuagroup.ogm.aerospike.options.navigation;

import org.hibernate.ogm.datastore.document.options.navigation.DocumentStorePropertyContext;
import org.hibernate.ogm.datastore.keyvalue.options.navigation.KeyValueStorePropertyContext;

public interface AerospikePropertyContext extends KeyValueStorePropertyContext<AerospikeEntityContext, AerospikePropertyContext>, DocumentStorePropertyContext<AerospikeEntityContext, AerospikePropertyContext> {

}