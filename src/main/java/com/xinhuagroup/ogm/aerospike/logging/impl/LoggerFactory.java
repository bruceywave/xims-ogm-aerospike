package com.xinhuagroup.ogm.aerospike.logging.impl;

import org.jboss.logging.Logger;

import com.xinhuagroup.ogm.aerospike.logging.AerospikeLog;

/**
 * aerospike ogm 日志工厂
 * @author brucey
 *
 */
public class LoggerFactory {
	private static final CallerProvider callerProvider = new CallerProvider();

	public static AerospikeLog getLogger() {
		return Logger.getMessageLogger(AerospikeLog.class, callerProvider.getCallerClass().getCanonicalName() );
	}

	private static class CallerProvider extends SecurityManager {
		public Class<?> getCallerClass() {
			return getClassContext()[2];
		}
	}
}
