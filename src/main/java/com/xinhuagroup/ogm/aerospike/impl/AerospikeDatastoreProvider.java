package com.xinhuagroup.ogm.aerospike.impl;

import java.util.Map;

import org.hibernate.boot.registry.classloading.spi.ClassLoaderService;
import org.hibernate.ogm.cfg.spi.Hosts;
import org.hibernate.ogm.cfg.spi.Hosts.HostAndPort;
import org.hibernate.ogm.datastore.spi.BaseDatastoreProvider;
import org.hibernate.ogm.dialect.spi.GridDialect;
import org.hibernate.ogm.util.configurationreader.spi.ConfigurationPropertyReader;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.spi.Configurable;
import org.hibernate.service.spi.ServiceRegistryAwareService;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.hibernate.service.spi.Startable;
import org.hibernate.service.spi.Stoppable;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Host;
import com.aerospike.client.policy.ClientPolicy;
import com.xinhuagroup.ogm.aerospike.AerospikeDialect;
import com.xinhuagroup.ogm.aerospike.logging.AerospikeLog;
import com.xinhuagroup.ogm.aerospike.logging.impl.LoggerFactory;

@SuppressWarnings("serial")
public class AerospikeDatastoreProvider extends BaseDatastoreProvider implements Startable, Stoppable, ServiceRegistryAwareService, Configurable {
//	private final static AerospikeLog log = LoggerFactory.getLogger();
	private ServiceRegistry serviceRegistry;
	private AerospikeConfiguration config;
	private AerospikeClient aerospikeClient;
	private AerospikeClientPolicy clientPolicy;

	/**
	 * 设置默认方言
	 */
	@Override
	public Class<? extends GridDialect> getDefaultDialect() {
		return AerospikeDialect.class;
	}

	/**
	 * 初始化配置信息
	 */
	@Override
	public void configure(@SuppressWarnings("rawtypes") Map configurationValues) {
		ClassLoaderService classLoaderService = serviceRegistry.getService(ClassLoaderService.class);
		ConfigurationPropertyReader propertyReader = new ConfigurationPropertyReader(configurationValues, classLoaderService);
		try {
			this.config = new AerospikeConfiguration(propertyReader);
			this.clientPolicy = config.getClientPolicy();
		} catch (Exception e) {
//			throw log.unableToConfigureDatastoreProvider(e);
		}
	}

	/**
	 * 注入服务
	 */
	@Override
	public void injectServices(ServiceRegistryImplementor serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

	@Override
	public void stop() {
		// 当线程停止时,关闭aerospike 链接
		if (aerospikeClient.isConnected()) {
			aerospikeClient.close();
		}
	}

	@Override
	public void start() {
		// 启动线程的时候,开启aerospike 客户端
		// 获取主机配置
		Hosts hosts = this.config.getHosts();
		Host[] hostArray = parserHost(hosts);
		this.aerospikeClient = new AerospikeClient(this.config.getClientPolicy(), hostArray);
	}

	@Override
	public boolean allowsTransactionEmulation() {
		return true;
	}

	/**
	 * 解析主机信息
	 * @param hosts
	 * @return
	 */
	private Host[] parserHost(Hosts hosts) {
		Host[] hostArray = new Host[hosts.size()];
		int index = 0;
		for (HostAndPort hostAndPort : hosts) {
			Host host = new Host(hostAndPort.getHost(), hostAndPort.getPort());
			hostArray[index] = host;
			index++;
		}
		return hostArray;
	}

	/**
	 * 获取客户端,用于数据处理
	 * @return
	 */
	public AerospikeClient getAerospikeClient(){
		return this.aerospikeClient;
	}
	
	/**
	 * 获取配置
	 * @return
	 */
	public AerospikeClientPolicy getAerospikeClientPolicy(){
		return this.clientPolicy;
	}
}
