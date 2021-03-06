package com.xinhuagroup.ogm.aerospike.query;

import java.util.Iterator;

import org.hibernate.ogm.dialect.query.spi.ClosableIterator;
import org.hibernate.ogm.model.key.spi.EntityKeyMetadata;
import org.hibernate.ogm.model.spi.Tuple;

import com.aerospike.client.Record;
import com.aerospike.client.query.KeyRecord;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.ResultSet;
import com.xinhuagroup.ogm.aerospike.dialect.model.AerospikeTupleSnapshot;
import com.xinhuagroup.ogm.aerospike.dialect.value.Entity;
import com.xinhuagroup.ogm.aerospike.query.impl.AerospikeQueryDescriptor;

public class AerospikeQueryResultsCursor implements ClosableIterator<Tuple>{
	private final RecordSet recordSet;
	private final EntityKeyMetadata entitykeyMetadata;
	private final Class<?> targetType;

	public AerospikeQueryResultsCursor(RecordSet recordSet,EntityKeyMetadata entityKeyMetadata,Class<?> targetType) {
		this.recordSet = recordSet;
		this.entitykeyMetadata = entityKeyMetadata;
		this.targetType = targetType;
	}

	@Override
	public boolean hasNext() {
		return recordSet.next();
	}

	@Override
	public Tuple next() {
		Record record =recordSet.getRecord();
		if(record != null)
			return new Tuple(new AerospikeTupleSnapshot(record.bins,targetType));
		else 
			return new Tuple(new AerospikeTupleSnapshot(new Entity().getProperties()));
	}
	@Override
	public void close() {
		recordSet.close();
	}
}
