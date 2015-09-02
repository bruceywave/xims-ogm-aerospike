package com.xinhuagroup.ogm.aerospike.impl;

import org.hibernate.ogm.cfg.OgmProperties;
import org.hibernate.ogm.cfg.impl.HostParser;
import org.hibernate.ogm.cfg.spi.Hosts;
import org.hibernate.ogm.util.configurationreader.spi.ConfigurationPropertyReader;

import com.xinhuagroup.ogm.aerospike.AerospikeProperties;

public class AerospikeConfiguration{
	private final static String DEFAULT_HOST = "localhost";
	private final static int DEFAULT_PORT = 3000;
	private final static String DEFAULT_DATABASE_NAME = "bar";
	private final static int DEFAULT_TIMEOUT = 5000;
	private final static int DEFAULT_MAX_THREAD = 300;
	private final static int DEFAULT_MAX_SOCKETS_IDLES = 14;
	private final static int DEFAULT_AEROSPIKE_TEND_INTERVAL = 1000;
	private final static boolean DEFAULT_AEROSPIKE_FAIL_IF_NOT_CONNECTED = false;
	private final static boolean DEFAULT_DEFAULT_AEROSPIKE_SHARED_THREAD_POOL = true;
	
	private Hosts hosts;
	private String user;
	private String password;
	private int timeout;
	private int maxThread;
	private int maxSocketsIdle;
	private int tendInterval;
	private boolean failIfNotConnected;
	private boolean sharedThreadPool;
	private String databaseName;
	
	private AerospikeClientPolicy clientPolicy;
	
	private final ConfigurationPropertyReader propertyReader;
	
	public AerospikeConfiguration(ConfigurationPropertyReader propertyReader){
		this.propertyReader = propertyReader;
		this.init();
	}
	
	/**
	 * 初始化参数
	 */
	public void init(){
		/**获取主机信息*/
		String host = propertyReader.property( AerospikeProperties.AEROSPIKE_HOSTS, String.class )
				.withDefault( DEFAULT_HOST ).getValue();
		this.hosts = HostParser.parse( host, null, DEFAULT_PORT );
		//取得数据库名称
		this.databaseName = this.propertyReader.property(AerospikeProperties.AEROSPIKE_DATABASE_NAME, String.class)
				.withDefault(DEFAULT_DATABASE_NAME).getValue();
		//获取数据库的用户名
		this.user = this.propertyReader.property(AerospikeProperties.AEROSPIKE_USER, String.class)
				.withDefault("").getValue();
		//取得数据库用户的密码
		this.password = this.propertyReader.property(AerospikeProperties.AEROSPIKE_PASSWORD, String.class)
				.withDefault("").getValue();
		this.timeout = this.propertyReader.property(AerospikeProperties.AEROSPIKE_TIMEOUT, Integer.class)
				.withDefault(DEFAULT_TIMEOUT).getValue();
		this.maxThread = this.propertyReader.property(AerospikeProperties.AEROSPIKE_MAX_THREAD, Integer.class)
				.withDefault(DEFAULT_MAX_THREAD).getValue();
		this.maxSocketsIdle  = this.propertyReader.property(AerospikeProperties.AEROSPIKE_MAX_SOCKETS_IDLE, Integer.class)
				.withDefault(DEFAULT_MAX_SOCKETS_IDLES).getValue();
		this.tendInterval = this.propertyReader.property(AerospikeProperties.AEROSPIKE_TEND_INTERVAL, Integer.class)
				.withDefault(DEFAULT_AEROSPIKE_TEND_INTERVAL).getValue();
		this.failIfNotConnected = this.propertyReader.property(AerospikeProperties.AEROSPIKE_FAIL_IF_NOT_CONNECTED, Boolean.class)
				.withDefault(DEFAULT_AEROSPIKE_FAIL_IF_NOT_CONNECTED).getValue();
		this.sharedThreadPool = this.propertyReader.property(AerospikeProperties.AEROSPIKE_SHARED_THREAD_POOL, Boolean.class)
				.withDefault(DEFAULT_DEFAULT_AEROSPIKE_SHARED_THREAD_POOL).getValue();
		
	}
	
	/**
	 * 获取aerospike 客户端默认配置项
	 * @return
	 */
	public AerospikeClientPolicy getClientPolicy(){
		clientPolicy = new AerospikeClientPolicy();
		clientPolicy.user = this.user;
		clientPolicy.password = this.password;
		clientPolicy.maxSocketIdle = this.maxSocketsIdle;
		clientPolicy.maxThreads= this.maxThread;
		clientPolicy.failIfNotConnected = this.failIfNotConnected;
		clientPolicy.sharedThreadPool = this.sharedThreadPool;
		clientPolicy.timeout = this.timeout;
		clientPolicy.tendInterval = this.tendInterval;
		clientPolicy.databaseName = this.databaseName;
		return clientPolicy;
	}
	
	/**
	 * 获取主机信息
	 * @return
	 */
	public Hosts getHosts(){
		return this.hosts;
	}
}
