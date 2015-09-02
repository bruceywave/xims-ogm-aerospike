package com.xinhuagroup.ogm.aerospike;

import org.hibernate.ogm.datastore.document.cfg.DocumentStoreProperties;
import org.hibernate.ogm.datastore.keyvalue.cfg.KeyValueStoreProperties;

/**
 * aerospike 的一些特殊的属性写在此处
 * @author brucey
 *
 */
public class AerospikeProperties implements KeyValueStoreProperties,DocumentStoreProperties{
	public static final String	AEROSPIKE_HOSTS					= "hibernate.ogm.datastore.aerospike.hosts";
	public static final String	AEROSPIKE_USER					= "hibernate.ogm.datastore.aerospike.user";
	public static final String	AEROSPIKE_PASSWORD				= "hibernate.ogm.datastore.aerospike.password";
	public static final String	AEROSPIKE_TIMEOUT				= "hibernate.ogm.datastore.aerospike.timeout";
	public static final String	AEROSPIKE_MAX_THREAD			= "hibernate.ogm.datastore.aerospike.maxThreads";
	public static final String	AEROSPIKE_MAX_SOCKETS_IDLE		= "hibernate.ogm.datastore.aerospike.maxSocketIdle";
	public static final String	AEROSPIKE_TEND_INTERVAL			= "hibernate.ogm.datastore.aerospike.tendInterval";
	public static final String	AEROSPIKE_FAIL_IF_NOT_CONNECTED	= "hibernate.ogm.datastore.aerospike.failIfNotConnected";
	public static final String	AEROSPIKE_SHARED_THREAD_POOL	= "hibernate.ogm.datastore.aerospike.sharedThreadPool";
	public static final String 	AEROSPIKE_DATABASE_NAME = "hibernate.ogm.datastore.aerospike.databaseName";
}
