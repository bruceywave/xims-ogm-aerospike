package com.xinhuagroup.ogm.aerospike.dialect.model;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import org.hibernate.ogm.model.spi.TupleSnapshot;

public class AerospikeTupleSnapshot implements TupleSnapshot{
	
	private final Map<String, Object> properties;

	public AerospikeTupleSnapshot(Map<String, Object> properties){
		this.properties = properties;
	}
	
	public AerospikeTupleSnapshot(Map<String, Object> properties,Class<?> targetType){
		this.properties = properties;
		convertVlaue(properties, targetType);
	}
	/**
	 * 转化数据类型
	 * @param properties
	 * @param targetType
	 */
	private void convertVlaue(Map<String, Object> properties,Class<?> targetType){
		if(targetType == null) return ;
		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			for (Field  field : targetType.getDeclaredFields()) {
				if(field.getName().equals(entry.getKey()) && (field.getType() == Integer.class || field.getType() == int.class)){
					properties.put(entry.getKey(), Integer.valueOf(entry.getValue().toString()));
				}
			}
		}
	}

	/**
	 * 通过列名获取对应的数据
	 */
	@Override
	public Object get(String column) {
		return properties.get(column);
	}

	/**
	 * 判断对象时候为空
	 */
	@Override
	public boolean isEmpty() {
		return properties.isEmpty();
	}

	/**
	 * 获取所有的列名称
	 */
	@Override
	public Set<String> getColumnNames() {
		return properties.keySet();
	}

	/**
	 * 获取所有的数据
	 * @return
	 */
	public Map<String, Object> getProperties() {
		return properties;
	}
}
