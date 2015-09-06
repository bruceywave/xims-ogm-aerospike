package com.xinhuagroup.ogm.aerospike.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class JSON {
	private static ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 序列化对象为JSON字符串
	 * @param object
	 * @return
	 */
	public static String serialize(Object object) {
		Writer write = new StringWriter();
		try {
			objectMapper.writeValue(write, object);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return write.toString();
	}

	/**
	 * 反序列化JSON为指定对象
	 * @param json
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T deserialize(String json, Class<T> clazz) {
		Object object = null;
		try {
			object = objectMapper.readValue(json, TypeFactory.rawClass(clazz));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (T) object;
	}

	/**
	 * 反序列化对象为指定对象
	 * @param json
	 * @param typeRef
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T deserialize(String json, TypeReference<T> typeRef) {
		try {
			return (T) objectMapper.readValue(json, typeRef);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
