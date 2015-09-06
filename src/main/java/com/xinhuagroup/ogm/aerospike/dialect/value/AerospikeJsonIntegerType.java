package com.xinhuagroup.ogm.aerospike.dialect.value;

import org.hibernate.MappingException;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.ogm.type.descriptor.impl.WrappedGridTypeDescriptor;
import org.hibernate.ogm.type.impl.AbstractGenericBasicType;
import org.hibernate.type.descriptor.java.IntegerTypeDescriptor;

@SuppressWarnings({ "serial", "unchecked" })
public class AerospikeJsonIntegerType extends AbstractGenericBasicType<Integer>{
	public static final AerospikeJsonIntegerType INSTANCE = new AerospikeJsonIntegerType();
	public AerospikeJsonIntegerType() {
		super( WrappedGridTypeDescriptor.INSTANCE, IntegerTypeDescriptor.INSTANCE );
	}

	@Override
	public String getName() {
		return "aerospike_integer";
	}

	@Override
	public int getColumnSpan(Mapping mapping) throws MappingException {
		return 1;
	}

}
