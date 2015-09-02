package com.xinhuagroup.ogm.aerospike.logging;

import org.hibernate.ogm.util.impl.Log;
import org.jboss.logging.MessageLogger;

/**
 * aerospike 日志处理工具
 * @author brucey
 *
 */
@SuppressWarnings("deprecation")
@MessageLogger(projectCode = "OGM")
public interface AerospikeLog extends Log{
	String ERROR_DESCRIPTION = "HTTP response status code: %03d, error: '%s', reason: '%s'";
	
}
