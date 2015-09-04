package com.xinhuagroup.ogm.aerospike.dialect.storage;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.ogm.dialect.spi.TupleContext;
import org.hibernate.ogm.model.key.spi.AssociationKey;
import org.hibernate.ogm.model.key.spi.EntityKey;
import org.hibernate.ogm.model.key.spi.EntityKeyMetadata;
import org.hibernate.ogm.model.spi.Tuple;
import org.hibernate.ogm.type.impl.AbstractGenericBasicType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.AerospikeException;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.ScanCallback;
import com.aerospike.client.Value;
import com.xinhuagroup.ogm.aerospike.dialect.value.AerospikeJsonIntegerType;
import com.xinhuagroup.ogm.aerospike.dialect.value.AerospikeJsonLongType;
import com.xinhuagroup.ogm.aerospike.dialect.value.Entity;
import com.xinhuagroup.ogm.aerospike.impl.AerospikeClientPolicy;

/**
 * 所有存储都调用该类
 * 
 * @author brucey
 *
 */
public class AerospikeStorageStrategy  {
	private final AerospikeClient aerospikeClient;
	private final AerospikeClientPolicy aerospikeClientPolicy;
	
	private static final Map<Type, AbstractGenericBasicType<?>> conversionMap = createGridTypeConversionMap();

	public AerospikeStorageStrategy(AerospikeClient aerospikeClient, AerospikeClientPolicy aerospikeClientPolicy) {
		this.aerospikeClient = aerospikeClient;
		this.aerospikeClientPolicy = aerospikeClientPolicy;
	}

	/**
	 * 获取实体
	 * 
	 * @param entityKey
	 * @return
	 */
	public Entity getEntity(Object entityKey) {
		Record record = aerospikeClient.get(null, createKey(entityKey));
		if (record != null)
			return new Entity(record.bins);
		else
			return null;
	}
	
	/**
	 * 获取实体,并转换实体类型,针对int类型
	 * @param entityKey
	 * @param tupleContext
	 * @return
	 */
	public Entity getEntity(Object entityKey,TupleContext tupleContext) {
		Record record = aerospikeClient.get(null, createKey(entityKey));
		if (record != null){
			Map<String, Object> properties = record.bins;
			convertDataType(properties,tupleContext);
			Entity entity = new Entity(properties);
			
			return entity;
		}else{
			return null;
		}
	}
	
	/**
	 * 获取当前的实体类
	 * @param tupleContext
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Class getObject(TupleContext tupleContext){
		try {
			String optionContexts = tupleContext.getOptionsContext().toString();
			String regex = "=class";
			int start = optionContexts.indexOf("=class");
			int end = optionContexts.indexOf(",");
			String clazz = optionContexts.substring(start + regex.length(), end);
			return Class.forName(clazz.trim());
		}  catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 转化实体数据类型
	 * @param entity
	 * @param tupleContext
	 */
	private void convertDataType(Map<String, Object> properties,TupleContext tupleContext){
		if(tupleContext == null) return ;
		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			for (Field  field : this.getObject(tupleContext).getDeclaredFields()) {
				if(field.getName().equals(entry.getKey()) && (field.getType() == Integer.class || field.getType() == int.class)){
					properties.put(entry.getKey(), Integer.valueOf(entry.getValue().toString()));
				}
			}
		}
	}
	/**
	 * 通过EntityKey 列表获取所有的对象
	 * @param entityKeys
	 * @return
	 */
	public List<Entity> getEntitys(Object[] entityKeys){
		Key[] keys = new Key[entityKeys.length];
		//获取所有的KEY
		for (int i = 0; i < entityKeys.length; i++) {
			keys[i] = createKey(entityKeys[i]);
		}
		Record[] records = aerospikeClient.get(null, keys);
		if(records != null){
			List<Entity> entitys = new ArrayList<Entity>();
			for (Record record : records) {
				entitys.add(new Entity(record.bins));
			}
			return entitys;
		}
		return null;
	}

	/**
	 * 删除实体
	 * 
	 * @param entityKey
	 */
	public void removeEntity(Object entityKey) {
		aerospikeClient.delete(null, createKey(entityKey));
	}

	public List<Key> scanEntity(EntityKeyMetadata entityKeyMetadata){
		List<Key> keys = new ArrayList<Key>();
		aerospikeClient.scanAll(null, aerospikeClientPolicy.databaseName, entityKeyMetadata.getTable(), new ScanCallback() {
			@Override
			public void scanCallback(Key key, Record record) throws AerospikeException {
				Map<String, Object> keyProperties = new HashMap<String, Object>();
				keys.add(key);
			}
		}, entityKeyMetadata.getColumnNames());
		return keys;
	}
	
	/**
	 * 插入或者更新数据
	 * 
	 * @param entityKey
	 * @param properties
	 */
	public void insertOrUpdate(Object entityKey, Map<String, Object> properties) {
		Bin[] bins = new Bin[properties.size()];
		int index = 0;
		for (Map.Entry<String, Object> mapEntry : properties.entrySet()) {
			bins[index++] = new Bin(mapEntry.getKey(), mapEntry.getValue());
		}
		aerospikeClient.put(null, createKey(entityKey), bins);
	}

	/**
	 * 创建KEY
	 * 
	 * @param entityKey
	 * @return
	 */
	public Key createKey(Object entityKey) {
		if (entityKey instanceof EntityKey)
			return this.createKey(aerospikeClientPolicy.databaseName, ((EntityKey) entityKey).getTable(), ((EntityKey) entityKey).getColumnValues()[0]);
		if (entityKey instanceof AssociationKey) {
			AssociationKey key = (AssociationKey) entityKey;
			String table = key.getTable();
			Object value = key.getColumnValues()[0];
			return this.createKey(aerospikeClientPolicy.databaseName, table, value);
		}
		if(entityKey instanceof Key)
			return (Key) entityKey;
		return null;
	}

	/**
	 * 获取KEY
	 * 
	 * @param nameSpace
	 * @param setName
	 * @param key
	 * @return
	 */
	private Key createKey(String nameSpace, String setName, Object key) {
		if (key instanceof Integer) {
			return new Key(nameSpace, setName, (Integer) key);
		} else if (key instanceof Long) {
			return new Key(nameSpace, setName, (Long) key);
		} else if (key instanceof String) {
			return new Key(nameSpace, setName, (String) key);
		} else if (key instanceof byte[]) {
			return new Key(nameSpace, setName, (byte[]) key);
		} else {
			return new Key(nameSpace, setName, Value.get(key));
		}
	}
	
	/**
	 * java 数据类型与数据库类型的相互转换
	 * @return
	 */
	private static Map<Type, AbstractGenericBasicType<?>> createGridTypeConversionMap() {
		Map<Type, AbstractGenericBasicType<? extends Object>> conversion = new HashMap<Type, AbstractGenericBasicType<? extends Object>>();
		conversion.put( StandardBasicTypes.INTEGER,AerospikeJsonLongType.INSTANCE);
		return conversion;
	}
	
	@SuppressWarnings("unchecked")
	public AbstractGenericBasicType<Object> convert(Type type) {
		return (AbstractGenericBasicType<Object>) conversionMap.get( type );
	}
}
