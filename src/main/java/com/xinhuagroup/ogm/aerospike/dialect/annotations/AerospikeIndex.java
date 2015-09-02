package com.xinhuagroup.ogm.aerospike.dialect.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.aerospike.client.query.IndexType;

@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface AerospikeIndex {
	public String name() default ""; //索引名称
	
	public IndexType indexType() default IndexType.STRING; //索引类型
}
