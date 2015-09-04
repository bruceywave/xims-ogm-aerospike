package com.xinhuagroup.ogm.aerospike.dialect.value;

import org.hibernate.MappingException;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.ogm.type.descriptor.impl.WrappedGridTypeDescriptor;
import org.hibernate.ogm.type.impl.AbstractGenericBasicType;
import org.hibernate.type.descriptor.java.LongTypeDescriptor;

@SuppressWarnings({ "serial", "unchecked" })
public class AerospikeJsonLongType extends AbstractGenericBasicType<Long>{
	public static final AerospikeJsonLongType INSTANCE = new AerospikeJsonLongType();
	public AerospikeJsonLongType() {
		super( WrappedGridTypeDescriptor.INSTANCE, LongTypeDescriptor.INSTANCE );
	}

	@Override
	public String getName() {
		return "aerospike_long";
	}

	@Override
	public int getColumnSpan(Mapping mapping) throws MappingException {
		return 1;
	}

}
