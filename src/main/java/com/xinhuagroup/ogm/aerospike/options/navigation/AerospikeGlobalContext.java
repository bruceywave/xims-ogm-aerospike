package com.xinhuagroup.ogm.aerospike.options.navigation;

import org.hibernate.ogm.datastore.document.options.navigation.DocumentStoreGlobalContext;
import org.hibernate.ogm.datastore.keyvalue.options.navigation.KeyValueStoreGlobalContext;

public interface AerospikeGlobalContext extends KeyValueStoreGlobalContext<AerospikeGlobalContext, AerospikeEntityContext>, DocumentStoreGlobalContext<AerospikeGlobalContext, AerospikeEntityContext> {

}
