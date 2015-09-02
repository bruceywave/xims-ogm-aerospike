package com.xinhuagroup.ogm.aerospike.dialect.storage;

import java.beans.IntrospectionException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.aerospike.client.AerospikeClient;
import com.xinhuagroup.ogm.aerospike.dialect.mapper.EntityStructure;
import com.xinhuagroup.ogm.aerospike.exception.AerospikeIncompatibleEntityException;
import com.xinhuagroup.ogm.aerospike.impl.AerospikeClientPolicy;

public abstract class AbstractAerospikeStorageStrategy {
	private final AerospikeClient aerospikeClient;
	private final AerospikeClientPolicy aerospikeClientPolicy;
	private final Lock structureLock = new ReentrantLock();
	private static enum StructureRejectionReason{
		NOT_AEROSPIKE_ENTITY, //不是aerospike 实体
		FAILED_IN_PARSING	  //解析实体失败
	}
	private final ConcurrentHashMap<Class,EntityStructure> entityStructure = new ConcurrentHashMap<Class, EntityStructure>();
	private final ConcurrentHashMap<Class,StructureRejectionReason> structureRejections = new ConcurrentHashMap<Class,StructureRejectionReason>();
	/**
	 * 初始化函数
	 * @param aerospikeClient
	 * @param aerospikeClientPolicy
	 */
	public AbstractAerospikeStorageStrategy(AerospikeClient aerospikeClient,AerospikeClientPolicy aerospikeClientPolicy){
		this.aerospikeClient = aerospikeClient;
		this.aerospikeClientPolicy = aerospikeClientPolicy;
	}
	
	/**
	 * 解析实体结构
	 * @param clazz
	 * @return
	 */
	public EntityStructure parserEntityStructure(Class clazz){
		//读取实体结构
		EntityStructure structure = entityStructure.get(clazz);
		//当实体结构不存在时,开始解析实体结构
		if(structure == null && !structureRejections.contains(clazz)){
			//锁住
			try {
				structureLock.lock();
				//如果实体结构不存在或者没有被序列化
				if(structure == null || !structure.isStructureInitialized()){
					structure = this.readAerospikeEntity(clazz,null);
					//读书实体属性结构
					this.readEntityStructure(clazz);
					this.reloadClazzPolicies(clazz);
					structure.setStructureInitialized(true);
				}
			} catch (IntrospectionException e) {
				structureRejections.put(clazz, StructureRejectionReason.FAILED_IN_PARSING);
				e.printStackTrace();
			} catch (AerospikeIncompatibleEntityException e) {
				structureRejections.put(clazz, StructureRejectionReason.NOT_AEROSPIKE_ENTITY);
				e.printStackTrace();
			}finally{
				structureLock.unlock();
			}
		}
		if(structureRejections.contains(clazz)){
			//抛出异常吗
		}
		return structure;
	}
	
	/**
	 * 缓存实体结构
	 * @param clazz
	 * @param structure
	 */
	protected final void putEntityStructure(Class clazz,EntityStructure structure){
		this.entityStructure.put(clazz, structure);
	}
	
	/**
	 * 获取实体结构
	 * @param clazz
	 * @return
	 */
	protected final EntityStructure getEntityStructure(Class clazz){
		return this.entityStructure.get(clazz);
	}
	
	/**
	 * 读取实体结构
	 * @param clazz
	 * @param entityStructure
	 * @return
	 * @throws AerospikeIncompatibleEntityException
	 */
	public abstract EntityStructure readAerospikeEntity(Class clazz,EntityStructure entityStructure)throws AerospikeIncompatibleEntityException;
	
	/**
	 * 读取实体属性结构
	 * @param clazz
	 * @throws IntrospectionException
	 */
	public abstract void readEntityStructure(Class clazz)throws IntrospectionException;
	
	/**
	 * 读取处理器
	 * @param clazz
	 */
	public abstract void reloadClazzPolicies(Class clazz);
}
