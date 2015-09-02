package com.xinhuagroup.ogm.aerospike.dialect.mapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import com.aerospike.client.policy.BatchPolicy;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.WritePolicy;
import com.aerospike.client.query.IndexType;
/**
 * 实体结构类
 * @author brucey
 *
 */
public class EntityStructure {
	private String clazzName;
	private String nameSpace;
	private String setName;
	private PersistableField primaryKey;
	private Map<String, Object> fieldShortName = new HashMap<String, Object>();
	private Map<String, Index> secondaryIndexs = new HashMap<String,Index>();
	private List<Field> fields = new ArrayList<Field>();
	private List<PersistableField> persistableFields = new ArrayList<PersistableField>();
	private WritePolicy writePolicy;
	private Policy policy;
	private BatchPolicy batchPolicy;
	private boolean structureInitialized = false;
	private AtomicBoolean indexsInitialized = new  AtomicBoolean(false);
	
	/**
	 * 第二索引
	 * @author brucey
	 *
	 */
	public static class Index{
		private final String indexName;
		private final IndexType indexType;
		
		public Index(String indexName,IndexType indexType){
			this.indexName = indexName;
			this.indexType = indexType;
		}

		public String getIndexName() {
			return indexName;
		}

		public IndexType getIndexType() {
			return indexType;
		}
	}
//getter setter方法
	public String getClazzName() {
		return clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

	public String getNameSpace() {
		return nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	public String getSetName() {
		return setName;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}

	public PersistableField getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(PersistableField primaryKey) {
		this.primaryKey = primaryKey;
	}

	public Map<String, Object> getFieldShortName() {
		return fieldShortName;
	}

	public void setFieldShortName(Map<String, Object> fieldShortName) {
		this.fieldShortName = fieldShortName;
	}

	public Map<String, Index> getSecondaryIndexs() {
		return secondaryIndexs;
	}

	public void setSecondaryIndexs(Map<String, Index> secondaryIndexs) {
		this.secondaryIndexs = secondaryIndexs;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public List<PersistableField> getPersistableFields() {
		return persistableFields;
	}

	public void setPersistableFields(List<PersistableField> persistableFields) {
		this.persistableFields = persistableFields;
	}

	public WritePolicy getWritePolicy() {
		return writePolicy;
	}

	public void setWritePolicy(WritePolicy writePolicy) {
		this.writePolicy = writePolicy;
	}

	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	public BatchPolicy getBatchPolicy() {
		return batchPolicy;
	}

	public void setBatchPolicy(BatchPolicy batchPolicy) {
		this.batchPolicy = batchPolicy;
	}

	public boolean isStructureInitialized() {
		return structureInitialized;
	}

	public void setStructureInitialized(boolean structureInitialized) {
		this.structureInitialized = structureInitialized;
	}

	public AtomicBoolean getIndexsInitialized() {
		return indexsInitialized;
	}

	public void setIndexsInitialized(AtomicBoolean indexsInitialized) {
		this.indexsInitialized = indexsInitialized;
	}
}
