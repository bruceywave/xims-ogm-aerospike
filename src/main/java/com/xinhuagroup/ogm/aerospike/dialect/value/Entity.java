package com.xinhuagroup.ogm.aerospike.dialect.value;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import org.hibernate.ogm.datastore.document.impl.DotPatternMapHelpers;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class Entity extends StructureValue {
	private static final String PATH_SEPARATOR = ".";
	private static final Pattern PATH_SPLIT_PATTERN = Pattern.compile(Pattern.quote(PATH_SEPARATOR));
	/**
	 * 字段树形以及字段值
	 */
	private final Map<String, Object> properties;

	public Entity() {
		this.properties = new HashMap<String, Object>();
	}

	public Entity(Map<String, Object> properties) {
		if (properties == null) {
			this.properties = new HashMap<String, Object>();
		} else {
			this.properties = properties;
		}
	}

	/**
	 * 获取所有的属性
	 * 
	 * @return
	 */
	@JsonIgnore
	public Map<String, Object> getProperties() {
		Map<String, Object> props = new HashMap<String, Object>(properties);
		return props;
	}

	@JsonAnyGetter
	public Map<String, Object> getPropertiesAsHierarchy() {
		Map<String, Object> hierarchicalProperties = new HashMap<String, Object>();
		for (Entry<String, Object> entry : properties.entrySet()) {
			String columnName = entry.getKey();
			if (isEmbeddedProperty(columnName)) {
				putEmbeddedProperty(hierarchicalProperties, columnName, entry.getValue());
			} else {
				hierarchicalProperties.put(columnName, entry.getValue());
			}
		}
		return hierarchicalProperties;
	}

	public static void putEmbeddedProperty(Map<String, Object> root, String name, Object value) {
		String[] pathElements = PATH_SPLIT_PATTERN.split(name);
		Map<String, Object> owner = root;
		for (int i = 0; i < pathElements.length - 1; i++) {
			String element = pathElements[i];
			@SuppressWarnings("unchecked")
			Map<String, Object> nextOwner = (Map<String, Object>) owner.get(element);
			if (nextOwner == null) {
				nextOwner = new HashMap<String, Object>();
				owner.put(element, nextOwner);
			}
			owner = nextOwner;
		}
		owner.put(pathElements[pathElements.length - 1], value);
	}

	/**
	 * 判断时候是引用属性
	 * 
	 * @param columnName
	 * @return
	 */
	public static boolean isEmbeddedProperty(String columnName) {
		return columnName.contains(PATH_SEPARATOR);
	}

	/**
	 * 设置属性
	 * 
	 * @param name
	 * @param value
	 */
	@JsonAnySetter
	@SuppressWarnings("unchecked")
	public void set(String name, Object value) {
		if (value instanceof Map) {
			setMapValue(name, (Map<String, Object>) value);
		} else {
			properties.put(name, value);
		}
	}

	/**
	 * 设置引用属性
	 * 
	 * @param name
	 * @param value
	 */
	private void setMapValue(String name, Map<String, Object> value) {
		for (Entry<String, Object> entry : value.entrySet()) {
			set(name + PATH_SEPARATOR + entry.getKey(), entry.getValue());
		}
	}

	/**
	 * 删除属性
	 * 
	 * @param name
	 */
	@JsonIgnore
	public void removeAssociation(String name) {
		if (properties.containsKey(name)) {
			properties.remove(name);
		} else {
			Set<String> keys = new HashSet<String>(properties.keySet());
			for (String key : keys) {
				if (key.startsWith(name + ".")) {
					removeAssociation(key);
				}
			}
			DotPatternMapHelpers.resetValue(properties, name);
		}
	}
}
