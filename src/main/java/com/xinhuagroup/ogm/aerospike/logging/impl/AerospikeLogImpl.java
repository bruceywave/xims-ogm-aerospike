package com.xinhuagroup.ogm.aerospike.logging.impl;

import java.io.Serializable;
import java.lang.annotation.ElementType;

import javax.transaction.SystemException;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.TransactionException;
import org.hibernate.ogm.dialect.spi.GridDialect;
import org.hibernate.ogm.exception.EntityAlreadyExistsException;
import org.hibernate.ogm.options.spi.AnnotationConverter;
import org.hibernate.service.spi.ServiceException;
import org.jboss.logging.Logger.Level;

import com.xinhuagroup.ogm.aerospike.logging.AerospikeLog;

public class AerospikeLogImpl implements AerospikeLog {

	@Override
	public void version(String versionString) {
		// TODO Auto-generated method stub

	}

	@Override
	public void persistenceXmlNotFoundInClassPath(String unitName) {
		// TODO Auto-generated method stub

	}

	@Override
	public HibernateException cannotInstantiateGridDialect(Class<?> dialectClass, Exception e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException gridDialectHasNoProperConstructor(Class<?> dialectClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException unexpectedDatastoreProvider(Class<?> found, Class<?> expected) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void useDatastoreProvider(Class<?> datastoreProviderClass) {
		// TODO Auto-generated method stub

	}

	@Override
	public void useGridDialect(Class<?> gridDialectClass) {
		// TODO Auto-generated method stub

	}

	@Override
	public TransactionException jtaTransactionBeginFailed(Exception e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionException jtaCommitFailed(Exception e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionException jtaRollbackFailed(Exception e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionException unableToMarkTransactionForRollback(Exception e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionException jtaCouldNotDetermineStatus(SystemException se) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionException unableToSetTimeout(SystemException se, int timeout) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException querySyntaxException(Exception qse, String queryString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void interruptedBatchIndexing() {
		// TODO Auto-generated method stub

	}

	@Override
	public HibernateException illegalDiscrimantorType(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException unableToConvertStringToDiscriminator(Exception e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createdQuery(String hqlQuery, Object queryObject) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unsupportedIndexerConfigurationOption(String optionName) {
		// TODO Auto-generated method stub

	}

	@Override
	public HibernateException mappingSubtypeNotInterface(Class<?> mappingType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException cannotCreateNewProxyInstance(Exception e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException cannotConvertAnnotation(Class<? extends AnnotationConverter<?>> converterClass, Exception e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException unableToLoadContext(String methodName, Class<?> contextClass, Exception e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException cannotCreateGlobalContextProxy(Class<?> contextClass, Exception e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException cannotCreateEntityContextProxy(Class<?> contextClass, Exception e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException cannotCreatePropertyContextProxy(Class<?> contextClass, Exception e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException getPropertyDoesNotExistException(String typeName, String property, ElementType elementType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException getUnsupportedElementTypeException(ElementType elementType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException unableToInstantiateType(Class<?> clazz, Exception e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException unableToLoadClass(String propertyName, String className, Exception e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException unexpectedClassType(String propertyName, Class<?> clazz, Class<?> expectedClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException unexpectedInstanceType(String propertyName, String instance, Class<?> actualClass, Class<?> expectedClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException ambigiousOptionConfiguration(String propertyName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException unknownAssociationStorageStrategy(String databaseName, String supportedValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException illegalPortValue(int value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException notAnInteger(String propertyName, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException unknownEnumerationValue(String propertyName, String value, String supportedValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException missingConfigurationProperty(String propertyName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException unsupportedPropertyType(String propertyName, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException closedOperationQueue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException invalidConfigurationUrl(String propertyName, String url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException couldNotRetrieveEntityForRetrievalOfGeneratedProperties(String entityType, Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IllegalArgumentException mustNotBeNull(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IllegalArgumentException parameterMustNotBeNull(String parameterName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException unableToFindGridType(String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dialectDoesNotSupportSequences(Class<?> dialectClass, String entityName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void catalogOptionNotSupportedForTableGenerator(String catalogName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void schemaOptionNotSupportedForTableGenerator(String schemaName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void catalogOptionNotSupportedForSequenceGenerator(String catalogName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void schemaOptionNotSupportedForSequenceGenerator(String schemaName) {
		// TODO Auto-generated method stub

	}

	@Override
	public HibernateException getIdentityGenerationStrategyNotSupportedException(String entityName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void usingNonAtomicOptimisticLocking(String entityName) {
		// TODO Auto-generated method stub

	}

	@Override
	public EntityAlreadyExistsException mustNotInsertSameEntityTwice(String primaryKey, Exception taee) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException couldNotConfigureProperty(String entityName, String string, Exception e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException unsupportedLockMode(Class<? extends GridDialect> dialectClass, LockMode lockMode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void noValidDatastoreProviderShortName(String providerName, String validProviderNames) {
		// TODO Auto-generated method stub

	}

	@Override
	public ServiceException unableToStartDatastoreProvider(Exception e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceException unableToConfigureDatastoreProvider(Exception e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException cannotLoadLuceneParserBackend(Exception e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unableToCloseSessionButSwallowingError(Exception e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void jbossTransactionManagerDetected() {
		// TODO Auto-generated method stub

	}

	@Override
	public void noJtaPlatformDetected() {
		// TODO Auto-generated method stub

	}

	@Override
	public IllegalArgumentException parameterSringMustNotBeEmpty(String parameterName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException getUnknownAliasException(String text) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException unableToParseHost(String text) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException errorOnEntityBatchLoad(String contextualInfo, Exception e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HibernateException notALong(String propertyName, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEnabled(Level level) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTraceEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void trace(Object message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void trace(Object message, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void trace(String loggerFqcn, Object message, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void trace(String loggerFqcn, Object message, Object[] params, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracev(String format, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracev(String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracev(String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracev(String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracev(Throwable t, String format, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracev(Throwable t, String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracev(Throwable t, String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracev(Throwable t, String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(String format, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(Throwable t, String format, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(Throwable t, String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(Throwable t, String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(Throwable t, String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(String format, int arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(String format, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(String format, int arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(String format, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(String format, int arg1, int arg2, Object arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(String format, int arg1, Object arg2, Object arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(Throwable t, String format, int arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(Throwable t, String format, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(Throwable t, String format, int arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(Throwable t, String format, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(Throwable t, String format, int arg1, int arg2, Object arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(Throwable t, String format, int arg1, Object arg2, Object arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(String format, long arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(String format, long arg1, long arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(String format, long arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(String format, long arg1, long arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(String format, long arg1, long arg2, Object arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(String format, long arg1, Object arg2, Object arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(Throwable t, String format, long arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(Throwable t, String format, long arg1, long arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(Throwable t, String format, long arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(Throwable t, String format, long arg1, long arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(Throwable t, String format, long arg1, long arg2, Object arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tracef(Throwable t, String format, long arg1, Object arg2, Object arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDebugEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void debug(Object message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debug(Object message, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debug(String loggerFqcn, Object message, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debug(String loggerFqcn, Object message, Object[] params, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugv(String format, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugv(String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugv(String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugv(String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugv(Throwable t, String format, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugv(Throwable t, String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugv(Throwable t, String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugv(Throwable t, String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(String format, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(Throwable t, String format, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(Throwable t, String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(Throwable t, String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(Throwable t, String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(String format, int arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(String format, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(String format, int arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(String format, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(String format, int arg1, int arg2, Object arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(String format, int arg1, Object arg2, Object arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(Throwable t, String format, int arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(Throwable t, String format, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(Throwable t, String format, int arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(Throwable t, String format, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(Throwable t, String format, int arg1, int arg2, Object arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(Throwable t, String format, int arg1, Object arg2, Object arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(String format, long arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(String format, long arg1, long arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(String format, long arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(String format, long arg1, long arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(String format, long arg1, long arg2, Object arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(String format, long arg1, Object arg2, Object arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(Throwable t, String format, long arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(Throwable t, String format, long arg1, long arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(Throwable t, String format, long arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(Throwable t, String format, long arg1, long arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(Throwable t, String format, long arg1, long arg2, Object arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugf(Throwable t, String format, long arg1, Object arg2, Object arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isInfoEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void info(Object message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void info(Object message, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void info(String loggerFqcn, Object message, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void info(String loggerFqcn, Object message, Object[] params, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void infov(String format, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void infov(String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void infov(String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void infov(String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void infov(Throwable t, String format, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void infov(Throwable t, String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void infov(Throwable t, String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void infov(Throwable t, String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void infof(String format, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void infof(String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void infof(String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void infof(String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void infof(Throwable t, String format, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void infof(Throwable t, String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void infof(Throwable t, String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void infof(Throwable t, String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warn(Object message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warn(Object message, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warn(String loggerFqcn, Object message, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warn(String loggerFqcn, Object message, Object[] params, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warnv(String format, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warnv(String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warnv(String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warnv(String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warnv(Throwable t, String format, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warnv(Throwable t, String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warnv(Throwable t, String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warnv(Throwable t, String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warnf(String format, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warnf(String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warnf(String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warnf(String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warnf(Throwable t, String format, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warnf(Throwable t, String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warnf(Throwable t, String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warnf(Throwable t, String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void error(Object message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void error(Object message, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void error(String loggerFqcn, Object message, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void error(String loggerFqcn, Object message, Object[] params, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void errorv(String format, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void errorv(String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void errorv(String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void errorv(String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void errorv(Throwable t, String format, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void errorv(Throwable t, String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void errorv(Throwable t, String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void errorv(Throwable t, String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void errorf(String format, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void errorf(String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void errorf(String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void errorf(String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void errorf(Throwable t, String format, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void errorf(Throwable t, String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void errorf(Throwable t, String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void errorf(Throwable t, String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fatal(Object message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fatal(Object message, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fatal(String loggerFqcn, Object message, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fatal(String loggerFqcn, Object message, Object[] params, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fatalv(String format, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fatalv(String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fatalv(String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fatalv(String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fatalv(Throwable t, String format, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fatalv(Throwable t, String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fatalv(Throwable t, String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fatalv(Throwable t, String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fatalf(String format, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fatalf(String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fatalf(String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fatalf(String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fatalf(Throwable t, String format, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fatalf(Throwable t, String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fatalf(Throwable t, String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fatalf(Throwable t, String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void log(Level level, Object message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void log(Level level, Object message, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void log(Level level, String loggerFqcn, Object message, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void log(String loggerFqcn, Level level, Object message, Object[] params, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logv(Level level, String format, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logv(Level level, String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logv(Level level, String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logv(Level level, String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logv(Level level, Throwable t, String format, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logv(Level level, Throwable t, String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logv(Level level, Throwable t, String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logv(Level level, Throwable t, String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logv(String loggerFqcn, Level level, Throwable t, String format, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logv(String loggerFqcn, Level level, Throwable t, String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logv(String loggerFqcn, Level level, Throwable t, String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logv(String loggerFqcn, Level level, Throwable t, String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logf(Level level, String format, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logf(Level level, String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logf(Level level, String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logf(Level level, String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logf(Level level, Throwable t, String format, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logf(Level level, Throwable t, String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logf(Level level, Throwable t, String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logf(Level level, Throwable t, String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logf(String loggerFqcn, Level level, Throwable t, String format, Object param1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logf(String loggerFqcn, Level level, Throwable t, String format, Object param1, Object param2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logf(String loggerFqcn, Level level, Throwable t, String format, Object param1, Object param2, Object param3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logf(String loggerFqcn, Level level, Throwable t, String format, Object... params) {
		// TODO Auto-generated method stub

	}

}
