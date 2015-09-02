package com.xinhuagroup.ogm.aerospike.dialect.mapper;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 实体结构解析
 * @author brucey
 *
 */
public class EntityMapper {
	private static enum StructureRejectionReason{
		NOT_AEROSPIKE_ENTITY, //不是aerospike 实体
		FAILED_IN_PARSING	  //解析实体失败
	}
	private final ConcurrentHashMap<Class,EntityStructure> entityStructure = new ConcurrentHashMap<Class, EntityStructure>();
	private final ConcurrentHashMap<Class,StructureRejectionReason> structureRejections = new ConcurrentHashMap<Class,StructureRejectionReason>();
	
	
}
