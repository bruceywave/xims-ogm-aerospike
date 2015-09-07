package com.xinhuagroup.ogm.aerospike;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.hibernate.engine.spi.QueryParameters;
import org.hibernate.ogm.datastore.document.association.spi.impl.DocumentHelpers;
import org.hibernate.ogm.datastore.document.impl.DotPatternMapHelpers;
import org.hibernate.ogm.datastore.document.options.AssociationStorageType;
import org.hibernate.ogm.datastore.document.options.spi.AssociationStorageOption;
import org.hibernate.ogm.datastore.map.impl.MapHelpers;
import org.hibernate.ogm.dialect.multiget.spi.MultigetGridDialect;
import org.hibernate.ogm.dialect.query.spi.BackendQuery;
import org.hibernate.ogm.dialect.query.spi.ClosableIterator;
import org.hibernate.ogm.dialect.query.spi.NoOpParameterMetadataBuilder;
import org.hibernate.ogm.dialect.query.spi.ParameterMetadataBuilder;
import org.hibernate.ogm.dialect.query.spi.QueryableGridDialect;
import org.hibernate.ogm.dialect.spi.AssociationContext;
import org.hibernate.ogm.dialect.spi.AssociationTypeContext;
import org.hibernate.ogm.dialect.spi.BaseGridDialect;
import org.hibernate.ogm.dialect.spi.ModelConsumer;
import org.hibernate.ogm.dialect.spi.NextValueRequest;
import org.hibernate.ogm.dialect.spi.TupleAlreadyExistsException;
import org.hibernate.ogm.dialect.spi.TupleContext;
import org.hibernate.ogm.model.key.spi.AssociationKey;
import org.hibernate.ogm.model.key.spi.AssociationKeyMetadata;
import org.hibernate.ogm.model.key.spi.EntityKey;
import org.hibernate.ogm.model.key.spi.EntityKeyMetadata;
import org.hibernate.ogm.model.key.spi.RowKey;
import org.hibernate.ogm.model.spi.Association;
import org.hibernate.ogm.model.spi.AssociationKind;
import org.hibernate.ogm.model.spi.Tuple;
import org.hibernate.ogm.type.spi.GridType;
import org.hibernate.type.Type;
import org.parboiled.Parboiled;
import org.parboiled.errors.ErrorUtils;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.support.ParsingResult;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Key;
import com.xinhuagroup.ogm.aerospike.dialect.model.AbstractAerospikeAssociation;
import com.xinhuagroup.ogm.aerospike.dialect.model.AerospikeAssociationSnapshot;
import com.xinhuagroup.ogm.aerospike.dialect.model.AerospikeTupleSnapshot;
import com.xinhuagroup.ogm.aerospike.dialect.storage.AerospikeStorageStrategy;
import com.xinhuagroup.ogm.aerospike.dialect.storage.RecordUtils;
import com.xinhuagroup.ogm.aerospike.dialect.value.DocumentAssociation;
import com.xinhuagroup.ogm.aerospike.dialect.value.Entity;
import com.xinhuagroup.ogm.aerospike.impl.AerospikeDatastoreProvider;
import com.xinhuagroup.ogm.aerospike.query.impl.AerospikeQueryDescriptor;
import com.xinhuagroup.ogm.aerospike.query.parsing.nativequery.impl.AerospikeQueryDescriptorBuilder;
import com.xinhuagroup.ogm.aerospike.query.parsing.nativequery.impl.NativeQueryParser;

@SuppressWarnings("serial")
public class AerospikeDialect extends BaseGridDialect implements MultigetGridDialect, QueryableGridDialect<AerospikeQueryDescriptor> {
	public static String ID_FIELDNAME = "";
	private final AerospikeClient aerospikeClient;
	private final AerospikeStorageStrategy aerospikeOperation;

	public AerospikeDialect(AerospikeDatastoreProvider aerospikeDatastoreProvider) {
		this.aerospikeClient = aerospikeDatastoreProvider.getAerospikeClient();
		this.aerospikeOperation = new AerospikeStorageStrategy(aerospikeClient, aerospikeDatastoreProvider.getAerospikeClientPolicy());
	}

	@Override
	public GridType overrideType(Type type) {
		// return aerospikeOperation.convert(type);
		return null;
	}

	@Override
	public Tuple getTuple(EntityKey key, TupleContext tupleContext) {
		Entity entity = aerospikeOperation.getEntity(key, tupleContext);
		if (entity != null) {
			return new Tuple(new AerospikeTupleSnapshot(entity.getProperties()));
		}
		return null;
	}

	/**
	 * 创建一个新的TUPLE
	 */
	@Override
	public Tuple createTuple(EntityKey key, TupleContext tupleContext) {
		return new Tuple(new AerospikeTupleSnapshot(new HashMap<String, Object>()));
	}

	@Override
	public void insertOrUpdateTuple(EntityKey key, Tuple tuple, TupleContext tupleContext) throws TupleAlreadyExistsException {
		Map<String, Object> properties = ((AerospikeTupleSnapshot) tuple.getSnapshot()).getProperties();
		MapHelpers.applyTupleOpsOnMap(tuple, properties);
		aerospikeOperation.insertOrUpdate(key, properties);
		//AbstractEntityTuplizer
	}

	@Override
	public void removeTuple(EntityKey key, TupleContext tupleContext) {
		aerospikeOperation.removeEntity(key);
	}

	/**
	 * 获取引用键
	 */
	@Override
	public Association getAssociation(AssociationKey key, AssociationContext associationContext) {
		AbstractAerospikeAssociation aerospikeAssociation = null;
		if (isStoredInEntityStructure(key.getMetadata(), associationContext.getAssociationTypeContext())) {
			// 获取记录
			Entity entity = aerospikeOperation.getEntity(key.getEntityKey());
			if (entity != null && DotPatternMapHelpers.hasField(entity.getPropertiesAsHierarchy(), key.getMetadata().getCollectionRole())) {
				aerospikeAssociation = AbstractAerospikeAssociation.fromEmbeddedAssociation(entity, key.getMetadata());
			}
		}
		return aerospikeAssociation != null ? new Association(new AerospikeAssociationSnapshot(aerospikeAssociation, key)) : null;
	}

	/**
	 * 创建引用键
	 */
	@Override
	public Association createAssociation(AssociationKey key, AssociationContext associationContext) {
		AbstractAerospikeAssociation aerospikeAssociation = null;
		if (isStoredInEntityStructure(key.getMetadata(), associationContext.getAssociationTypeContext())) {
			Entity entity = aerospikeOperation.getEntity(key.getEntityKey());
			if (entity == null) {
				entity = new Entity();
				// 这里也存在着问题
				aerospikeOperation.insertOrUpdate(key.getEntityKey(), entity.getProperties());
			}
			aerospikeAssociation = AbstractAerospikeAssociation.fromEmbeddedAssociation(entity, key.getMetadata());
		} else {
			// 暂时不管这一步
			DocumentAssociation association = new DocumentAssociation();
			AbstractAerospikeAssociation.fromAssociationDocument(association);
		}
		return new Association(new AerospikeAssociationSnapshot(aerospikeAssociation, key));
	}

	@Override
	public void insertOrUpdateAssociation(AssociationKey associationKey, Association association, AssociationContext associationContext) {
		// 获取数据行
		Object rows = getAssociationRows(association, associationKey, associationContext);
		AbstractAerospikeAssociation aerospikeAssociation = ((AerospikeAssociationSnapshot) association.getSnapshot()).getAerospikeAssociation();
		aerospikeAssociation.setRows(rows);
		if (isStoredInEntityStructure(associationKey.getMetadata(), associationContext.getAssociationTypeContext())) {
			aerospikeOperation.insertOrUpdate(associationKey.getEntityKey(), ((Entity) aerospikeAssociation.getOwningDocument()).getProperties());
		} else {
			storeAssociation(associationKey.getEntityKey(), (DocumentAssociation) aerospikeAssociation.getOwningDocument());
			// 这里还有所问题
			// aerospikeOperation.insertOrUpdate(associationKey,
			// ((DocumentAssociation)
			// aerospikeAssociation.getOwningDocument()).getRows());
		}
	}

	private void storeAssociation(EntityKey entityKey, DocumentAssociation document) {
		// 首先是删除原有的
		aerospikeOperation.removeEntity(entityKey);
		for (Object row : document.getRows()) {
			aerospikeOperation.insertOrUpdate(entityKey, (Map<String, Object>) row);
		}
	}

	@Override
	public void removeAssociation(AssociationKey key, AssociationContext associationContext) {
		if (isStoredInEntityStructure(key.getMetadata(), associationContext.getAssociationTypeContext())) {
			Entity entity = aerospikeOperation.getEntity(key.getEntityKey());
			if (entity != null) {
				entity.removeAssociation(key.getMetadata().getCollectionRole());
				aerospikeOperation.insertOrUpdate(key.getEntityKey(), entity.getProperties());
			}
		} else {
			aerospikeOperation.removeEntity(key.getEntityKey());
		}
	}

	@Override
	public boolean isStoredInEntityStructure(AssociationKeyMetadata associationKeyMetadata, AssociationTypeContext associationTypeContext) {
		AssociationStorageType associationStorage = getAssociationStorageType(associationTypeContext);
		if (associationKeyMetadata.isOneToOne() || associationKeyMetadata.getAssociationKind() == AssociationKind.EMBEDDED_COLLECTION || associationStorage == AssociationStorageType.IN_ENTITY) {
			return true;
		}
		return false;
	}

	/**
	 * 获取下一个ID，当采用TableSequence的时候，就会执行该方法
	 */
	@Override
	public Number nextValue(NextValueRequest request) {
		Random r = new Random();
		long num = Math.abs(r.nextLong() % 10000000000L);
		String s = String.valueOf(num);
		for (int i = 0; i < 10 - s.length(); i++) {
			s = "0" + s;
		}
		return Long.parseLong(s);
	}

	@Override
	public void forEachTuple(ModelConsumer consumer, EntityKeyMetadata... entityKeyMetadatas) {
		// 首先循环遍历
		for (EntityKeyMetadata entityKeyMetadata : entityKeyMetadatas) {
			List<Key> keys = aerospikeOperation.scanEntity(entityKeyMetadata);
			for (Key key : keys) {
				Entity entity = aerospikeOperation.getEntity(key);
				addKeyValuesFromKeyName(entityKeyMetadata, key, entity);
				consumer.consume(new Tuple(new AerospikeTupleSnapshot(entity.getProperties())));
			}
		}
	}

	private void addKeyValuesFromKeyName(EntityKeyMetadata entityKeyMetadata, Key key, Entity entity) {
		if (key.setName.equals(entityKeyMetadata.getTable())) {
			Map<String, Object> keyMaps = keyToMap(entityKeyMetadata, key);
			for (Map.Entry<String, Object> keyMap : keyMaps.entrySet()) {
				entity.set(keyMap.getKey(), keyMap.getValue());
			}
		}
	}

	private Map<String, Object> keyToMap(EntityKeyMetadata entityKeyMetadata, Key key) {
		if (entityKeyMetadata.getColumnNames().length == 1) {
			return Collections.singletonMap(entityKeyMetadata.getColumnNames()[0], key.userKey);
		}
		return null;
	}

	@Override
	public List<Tuple> getTuples(EntityKey[] entityKeys, TupleContext tupleContext) {
		// 获取所有的实体
		List<Entity> entities = aerospikeOperation.getEntitys(entityKeys);
		List<Tuple> tuples = new ArrayList<Tuple>(entityKeys.length);
		int index = 0;
		for (Entity entity : entities) {
			if (entity != null) {
				EntityKey key = entityKeys[index];
				addIdToEntity(entity, key.getColumnNames(), key.getColumnValues());
				tuples.add(new Tuple(new AerospikeTupleSnapshot(entity.getProperties())));
			}
			index++;
		}
		return tuples;
	}

	private void addIdToEntity(Entity entity, String[] cloumnNames, Object[] columnValues) {
		for (int i = 0; i < cloumnNames.length; i++) {
			entity.set(cloumnNames[i], columnValues[i]);
		}
	}

	private AssociationStorageType getAssociationStorageType(AssociationTypeContext associationTypeContext) {
		return associationTypeContext.getOptionsContext().getUnique(AssociationStorageOption.class);
	}

	@SuppressWarnings("unchecked")
	private Object getAssociationRows(Association association, AssociationKey associationKey, AssociationContext associationContext) {
		boolean organizeByRowKey = DotPatternMapHelpers.organizeAssociationMapByRowKey(association, associationKey, associationContext);
		if (isStoredInEntityStructure(associationKey.getMetadata(), associationContext.getAssociationTypeContext()) && organizeByRowKey) {
			String rowKeyColumn = organizeByRowKey ? associationKey.getMetadata().getRowKeyIndexColumnNames()[0] : null;
			Map<String, Object> rows = new HashMap<>();
			for (RowKey rowKey : association.getKeys()) {
				Map<String, Object> row = (Map<String, Object>) getAssociationRow(association.get(rowKey), associationKey);
				String rowKeyValue = (String) row.remove(rowKeyColumn);
				// 如果着是一个单列的值,打开它
				if (row.keySet().size() == 1) {
					rows.put(rowKeyValue, row.values().iterator().next());
				} else {
					rows.put(rowKeyValue, row);
				}
			}
			return rows;
		}
		List<Object> rows = new ArrayList<Object>(association.size());
		for (RowKey rowKey : association.getKeys()) {
			rows.add(getAssociationRow(association.get(rowKey), associationKey));
		}
		return rows;
	}

	private Object getAssociationRow(Tuple tuple, AssociationKey associationKey) {
		// 获取持久化的列
		String[] columnsToPersist = associationKey.getMetadata().getColumnsWithoutKeyColumns(tuple.getColumnNames());
		// 如果只有单列存储,则返回它自己
		if (columnsToPersist.length == 1) {
			return tuple.get(columnsToPersist[0]);
		}
		Map<String, Object> properties = new HashMap<String, Object>();
		String prefix = getColumnSharedPrefixOfAssociatedEntityLink(associationKey);
		for (String column : columnsToPersist) {
			Object value = tuple.get(column);
			if (value != null) {
				String columnName = column.startsWith(prefix) ? column.substring(prefix.length()) : column;
				properties.put(columnName, value);
			}
		}
		return RecordUtils.getPropertiesAsHierarchy(properties);
	}

	private String getColumnSharedPrefixOfAssociatedEntityLink(AssociationKey associationKey) {
		String[] associationKeyColumns = associationKey.getMetadata().getAssociatedEntityKeyMetadata().getAssociationKeyColumns();
		String prefix = DocumentHelpers.getColumnSharedPrefix(associationKeyColumns);
		return prefix == null ? "" : prefix + ".";
	}

	@Override
	public ClosableIterator<Tuple> executeBackendQuery(BackendQuery<AerospikeQueryDescriptor> backendQuery, QueryParameters queryParameters) {
		AerospikeQueryDescriptor queryDescriptor = backendQuery.getQuery();
		EntityKeyMetadata entityKeyMetadata = backendQuery.getSingleEntityKeyMetadataOrNull();
		switch (queryDescriptor.getOperation()) {
		case FIND:
			return aerospikeOperation.queryEntitys(queryDescriptor, queryParameters, entityKeyMetadata);
//			return doFind(queryDescriptor, queryParameters, collection, entityKeyMetadata);
		case AGGREGATE:
			System.out.println(123);
//			return doAggregate(queryDescriptor, queryParameters, collection, entityKeyMetadata);
		case COUNT:
			System.out.println(1111111);
//			return doCount(queryDescriptor, collection);
		default:
			throw new IllegalArgumentException("Unexpected query operation: " + queryDescriptor);
		}
	}

	@Override
	public ParameterMetadataBuilder getParameterMetadataBuilder() {
		return NoOpParameterMetadataBuilder.INSTANCE;
	}

	@Override
	public AerospikeQueryDescriptor parseNativeQuery(String nativeQuery) {
		NativeQueryParser parser = Parboiled.createParser(NativeQueryParser.class);
		ParsingResult<AerospikeQueryDescriptorBuilder> parseResult = new RecoveringParseRunner<AerospikeQueryDescriptorBuilder>(parser.Query()).run(nativeQuery);
		if (parseResult.hasErrors()) {
			throw new IllegalArgumentException("Unsupported native query: " + ErrorUtils.printParseErrors(parseResult.parseErrors));
		}
		return parseResult.resultValue.build();
	}
}
