package com.xinhuagroup.ogm.aerospike.dialect.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import com.aerospike.client.Record;

public class RecordUtils {
	private static final String PATH_SEPARATOR = ".";
	private static final Pattern PATH_SPLIT_PATTERN = Pattern.compile( Pattern.quote( PATH_SEPARATOR ) );
	
	public static Map<String, Object> getPropertiesAsHierarchy(Map<String, Object> properties){
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
	
	@SuppressWarnings("unchecked")
	private static void putEmbeddedProperty(Map<String, Object> hierarchicalProperties, String columnName, Object value) {
		String[] pathElements = PATH_SPLIT_PATTERN.split(columnName);
		Map<String, Object> owner = hierarchicalProperties;
		for (int i = 0; i < pathElements.length - 1; i++) {
			String element = pathElements[i];
			Map<String, Object> nextOwner = (Map<String, Object>) owner.get(element);
			if (nextOwner == null) {
				nextOwner = new HashMap<String, Object>();
				owner.put(element, nextOwner);
			}
			owner = nextOwner;
		}
		owner.put(pathElements[pathElements.length - 1], value);
	}

	private static boolean isEmbeddedProperty(String columnName) {
		return columnName.contains(PATH_SEPARATOR);
	}
	
}
