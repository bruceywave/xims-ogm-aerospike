package com.xinhuagroup.ogm.aerospike.options.navigation;

import org.hibernate.ogm.datastore.document.options.navigation.DocumentStoreEntityContext;
import org.hibernate.ogm.datastore.keyvalue.options.navigation.KeyValueStoreEntityContext;

public interface AerospikeEntityContext extends KeyValueStoreEntityContext<AerospikeEntityContext, AerospikePropertyContext>, DocumentStoreEntityContext<AerospikeEntityContext, AerospikePropertyContext> {

}
