package com.xinhuagroup.ogm.aerospike.dialect.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
/**
 * 持久化字段
 * @author brucey
 *
 */
public class PersistableField {
	private Field field;
	private Class<?> type;
	private Method getter;
	private Method setter;
	private boolean serializerRequired;
	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}
	public Class<?> getType() {
		return type;
	}
	public void setType(Class<?> type) {
		this.type = type;
	}
	public Method getGetter() {
		return getter;
	}
	public void setGetter(Method getter) {
		this.getter = getter;
	}
	public Method getSetter() {
		return setter;
	}
	public void setSetter(Method setter) {
		this.setter = setter;
	}
	public boolean isSerializerRequired() {
		return serializerRequired;
	}
	public void setSerializerRequired(boolean serializerRequired) {
		this.serializerRequired = serializerRequired;
	}
}
