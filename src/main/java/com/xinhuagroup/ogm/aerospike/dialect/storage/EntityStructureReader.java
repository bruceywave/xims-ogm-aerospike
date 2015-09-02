package com.xinhuagroup.ogm.aerospike.dialect.storage;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.ReflectionUtils.FieldFilter;

import com.aerospike.client.AerospikeClient;
import com.xinhuagroup.ogm.aerospike.dialect.annotations.AerospikeEntity;
import com.xinhuagroup.ogm.aerospike.dialect.annotations.AerospikeIndex;
import com.xinhuagroup.ogm.aerospike.dialect.annotations.AerospikeKey;
import com.xinhuagroup.ogm.aerospike.dialect.annotations.AerospikeTransient;
import com.xinhuagroup.ogm.aerospike.dialect.mapper.EntityStructure;
import com.xinhuagroup.ogm.aerospike.dialect.mapper.EntityStructure.Index;
import com.xinhuagroup.ogm.aerospike.dialect.mapper.PersistableField;
import com.xinhuagroup.ogm.aerospike.exception.AerospikeIncompatibleEntityException;
import com.xinhuagroup.ogm.aerospike.impl.AerospikeClientPolicy;

/**
 * 实体结构读取工具
 * @author brucey
 *
 */
public class EntityStructureReader extends AbstractAerospikeStorageStrategy{
	private final AerospikeClientPolicy aerospikeClientPolicy;
	public EntityStructureReader(AerospikeClient aerospikeClient, AerospikeClientPolicy aerospikeClientPolicy) {
		super(aerospikeClient, aerospikeClientPolicy);
		this.aerospikeClientPolicy = aerospikeClientPolicy;
	}

	@Override
	public EntityStructure readAerospikeEntity(Class clazz,EntityStructure entityStructure) throws AerospikeIncompatibleEntityException{
		AerospikeEntity aerospikeEntity = (AerospikeEntity) clazz.getAnnotation(AerospikeEntity.class);
		if(aerospikeEntity == null){
			//抛出异常吗?
		}
		if(entityStructure == null){
			entityStructure = new EntityStructure();
			//缓存实体结构
			super.putEntityStructure(clazz, entityStructure);
		}
		//设置命名空间
		String nameSpace = aerospikeEntity.nameSpace().isEmpty() ? aerospikeClientPolicy.databaseName : aerospikeEntity.nameSpace();
		entityStructure.setNameSpace(nameSpace);
		//设置表名
		entityStructure.setSetName(aerospikeEntity.setName());
		//判断时候已经设置表名称,如果没有设置,则使用类名作为表明
		if(aerospikeEntity.setName() == null || aerospikeEntity.setName().isEmpty()){
			entityStructure.setSetName(clazz.getName().toLowerCase());
		}
		return entityStructure;
	}

	@Override
	public void readEntityStructure(Class clazz) throws IntrospectionException {
		//获取实体结构
		EntityStructure entityStructure = super.getEntityStructure(clazz);
		//获取实体的基本信息
		BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
		//读取字段属性信息
		PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
		for (Field field : this.getAllInstanceFields(clazz)) {
			boolean isKey = false;
			if(field.isAnnotationPresent(AerospikeTransient.class)){ //如果该字段属于透明字段,则继续循环
				continue ;
			}
			//判断该字段时候是主键字段
			if(field.isAnnotationPresent(javax.persistence.Id.class) || field.isAnnotationPresent(AerospikeKey.class)){
				entityStructure.getFields().add(field);
				isKey = true;
			}
			//读取第二索引字段
			AerospikeIndex aerospikeIndex = field.getAnnotation(AerospikeIndex.class);
			if(aerospikeIndex != null){
				String indexName = aerospikeIndex.name();
				if(StringUtils.isEmpty(indexName)){
					indexName = field.getName();
				}
				entityStructure.getSecondaryIndexs().put(field.getName(), new Index(indexName, aerospikeIndex.indexType()));
			}
			//获取字段的持久化信息
			for (PropertyDescriptor propertyDescriptor : props) {
				if(field.getName().equals(propertyDescriptor.getName())){
					PersistableField persistableField = new PersistableField();
					persistableField.setField(field);
					persistableField.setSetter(propertyDescriptor.getWriteMethod());
					persistableField.setGetter(propertyDescriptor.getReadMethod());
					persistableField.setType(field.getType());
					if(isKey){
						entityStructure.setPrimaryKey(persistableField);
					}
					if(!(Integer.TYPE == field.getType() || Long.TYPE == field.getType() || field.getType().isAssignableFrom(Integer.class) || field.getType().isAssignableFrom(Long.class))){
						persistableField.setSerializerRequired(true);
					}
					if(field.getName().length() > 14){
						String shortName = field.getName().substring(0,11) + "_" + entityStructure.getPersistableFields().size();
						entityStructure.getFieldShortName().put(field.getName(), shortName);
					}
				}
			}
		}
	}

	/**
	 * 通过反射获取所有的字段
	 * @param clazz
	 * @return
	 */
	private Set<Field> getAllInstanceFields(Class clazz){
		final Set<Field> instanceFields = new HashSet<Field>();
		ReflectionUtils.doWithFields(clazz, new FieldCallback() {
			@Override
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				//反射获取字段
				instanceFields.add(field);
			}
		},new FieldFilter() { //过滤字段
			@Override
			public boolean matches(Field field) {
				int modifiers = field.getModifiers();
				return !Modifier.isStatic(modifiers);
			}
		});
		return instanceFields;
	}

	@Override
	public void reloadClazzPolicies(Class clazz) {
		EntityStructure entityStructure = super.getEntityStructure(clazz);
		if(entityStructure != null){
			entityStructure.setBatchPolicy(aerospikeClientPolicy.batchPolicyDefault);
			entityStructure.setPolicy(aerospikeClientPolicy.readPolicyDefault);
			entityStructure.setWritePolicy(aerospikeClientPolicy.writePolicyDefault);
		}
	}
}
