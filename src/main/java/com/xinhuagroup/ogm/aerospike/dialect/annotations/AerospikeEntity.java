package com.xinhuagroup.ogm.aerospike.dialect.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AerospikeEntity {
	public String nameSpace() default "";//数据库名称
	
	public String setName() default ""; //表名
}
