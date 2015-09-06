package com.xinhuagroup.ogm.aerospike.dialect.storage;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.engine.spi.QueryParameters;
import org.hibernate.engine.spi.TypedValue;
import org.hibernate.ogm.dialect.spi.TupleContext;
import org.hibernate.ogm.model.key.spi.AssociationKey;
import org.hibernate.ogm.model.key.spi.EntityKey;
import org.hibernate.ogm.model.key.spi.EntityKeyMetadata;
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
import com.aerospike.client.query.Filter;
import com.aerospike.client.query.IndexType;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;
import com.aerospike.client.task.IndexTask;
import com.xinhuagroup.ogm.aerospike.dialect.value.AerospikeJsonLongType;
import com.xinhuagroup.ogm.aerospike.dialect.value.Entity;
import com.xinhuagroup.ogm.aerospike.impl.AerospikeClientPolicy;
import com.xinhuagroup.ogm.aerospike.query.AerospikeQueryResultsCursor;
import com.xinhuagroup.ogm.aerospike.query.impl.AerospikeQueryDescriptor;

/**
 * 所有存储都调用该类
 * 
 * @author brucey
 *
 */
public class AerospikeStorageStrategy {
	private final AerospikeClient aerospikeClient;
	private final AerospikeClientPolicy aerospikeClientPolicy;
	private final String NAMESPACE;

	private static final Map<Type, AbstractGenericBasicType<?>> conversionMap = createGridTypeConversionMap();

	public AerospikeStorageStrategy(AerospikeClient aerospikeClient, AerospikeClientPolicy aerospikeClientPolicy) {
		this.aerospikeClient = aerospikeClient;
		this.aerospikeClientPolicy = aerospikeClientPolicy;
		this.NAMESPACE = aerospikeClientPolicy.databaseName;
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
	 * 
	 * @param entityKey
	 * @param tupleContext
	 * @return
	 */
	public Entity getEntity(Object entityKey, TupleContext tupleContext) {
		Record record = aerospikeClient.get(null, createKey(entityKey));
		if (record != null) {
			Map<String, Object> properties = record.bins;
			convertDataType(properties, tupleContext);
			Entity entity = new Entity(properties);

			return entity;
		} else {
			return null;
		}
	}

	/**
	 * 获取当前的实体类
	 * 
	 * @param tupleContext
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Class getObject(TupleContext tupleContext) {
		try {
			String optionContexts = tupleContext.getOptionsContext().toString();
			String regex = "=class";
			int start = optionContexts.indexOf("=class");
			int end = optionContexts.indexOf(",");
			String clazz = optionContexts.substring(start + regex.length(), end);
			return Class.forName(clazz.trim());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 转化实体数据类型
	 * 
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
	 * 
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

	public List<Key> scanEntity(EntityKeyMetadata entityKeyMetadata) {
		List<Key> keys = new ArrayList<Key>();
		aerospikeClient.scanAll(null, aerospikeClientPolicy.databaseName, entityKeyMetadata.getTable(), new ScanCallback() {
			@Override
			public void scanCallback(Key key, Record record) throws AerospikeException {
				keys.add(key);
			}
		}, entityKeyMetadata.getColumnNames());
		return keys;
	}

	@SuppressWarnings("deprecation")
	public AerospikeQueryResultsCursor queryEntitys(AerospikeQueryDescriptor queryDescriptor, QueryParameters queryParameters, EntityKeyMetadata entityKeyMetadata) {
		Statement statement = new Statement();
		// 设置命名空间也就是数据库名称
		statement.setNamespace(aerospikeClientPolicy.databaseName);
		// 设置表名
		statement.setSetName(queryDescriptor.getCollectionName());
		//添加索引
		addIndex(queryDescriptor, queryParameters,entityKeyMetadata);
		// 设置查询参数
		// 1.根据查询参数创建索引，如果查询参数和主键一致，则不需要创建索引
		Entity criteria = queryDescriptor.getCriteria();
		Map<String, Object> params = null;
		Filter[] filters = null;
		if(criteria != null){
			 filters = new Filter[criteria.getProperties().size()];
			 params = criteria.getProperties();
		}
		if (params != null && !params.isEmpty()) {
			int index = 0;
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				if(entry.getValue() instanceof Integer){
					filters[index] = Filter.equal(entry.getKey(), ((Number)entry.getValue()).longValue());
				}else if(entry.getValue() instanceof String){
					filters[index] = Filter.equal(entry.getKey(), (String)entry.getValue());
				}else{
					filters[index] = Filter.equal(entry.getKey(),Value.get(entry.getValue()));
				}
				index ++;
			}
		}
		statement.setFilters(filters);
		RecordSet recordSet = aerospikeClient.query(null, statement);
		return new AerospikeQueryResultsCursor(recordSet, entityKeyMetadata);
	}
	private void addIndex(AerospikeQueryDescriptor queryDescriptor,QueryParameters queryParameters,EntityKeyMetadata entityKeyMetadata){
		//取得表名称
		String tableName = queryDescriptor.getCollectionName();
		Entity criteria = queryDescriptor.getCriteria();
		if(criteria != null && !criteria.getProperties().isEmpty()){
			for (Map.Entry<String, Object> entry : criteria.getProperties().entrySet()) {
				if(entry.getKey().equals(entityKeyMetadata.getColumnNames()[0]))
					continue;
				String indexName = NAMESPACE +"_" + tableName + "_" + entry.getKey();
				//首先获取索引，判断索引时候存在
				Record record = aerospikeClient.get(null, createKey(NAMESPACE, tableName, indexName));
				if(record != null && 1 == record.getInt(indexName)){
//					log.exsitIndex()
					continue;
				}
				IndexTask indexTask = addIndex(tableName, indexName, entry.getKey(), getIndexType(entry.getValue()));
				indexTask.waitTillComplete();
			}
		}
	}
	
	private IndexType getIndexType(Object value){
		if(value instanceof Integer || value instanceof Long){
			return IndexType.NUMERIC;
		}
		if(value instanceof String)
			return IndexType.STRING;
		else 
			return IndexType.STRING;
	}
	
	private IndexTask addIndex(String setName,String indexName,String binName,IndexType indexType){
		return addIndex(NAMESPACE,setName,indexName,binName,indexType);
	}

	private IndexTask addIndex(String namespace,String setName,String indexName,String binName,IndexType indexType){
		IndexTask indexTask = aerospikeClient.createIndex(null, namespace, setName, indexName, binName, indexType);
		return indexTask;
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
		if (entityKey instanceof Key)
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
	 * 
	 * @return
	 */
	private static Map<Type, AbstractGenericBasicType<?>> createGridTypeConversionMap() {
		Map<Type, AbstractGenericBasicType<? extends Object>> conversion = new HashMap<Type, AbstractGenericBasicType<? extends Object>>();
		conversion.put(StandardBasicTypes.INTEGER, AerospikeJsonLongType.INSTANCE);
		return conversion;
	}

	@SuppressWarnings("unchecked")
	public AbstractGenericBasicType<Object> convert(Type type) {
		return (AbstractGenericBasicType<Object>) conversionMap.get(type);
	}
}
