package com.xinhuagroup.ogm.aerospike.query.impl;

import java.util.HashMap;
import java.util.Map;

import org.antlr.runtime.tree.Tree;
import org.hibernate.hql.ast.common.JoinType;
import org.hibernate.hql.ast.origin.hql.resolve.path.PathedPropertyReference;
import org.hibernate.hql.ast.origin.hql.resolve.path.PathedPropertyReferenceSource;
import org.hibernate.hql.ast.origin.hql.resolve.path.PropertyPath;
import org.hibernate.hql.ast.spi.QueryResolverDelegate;

public class AerospikeResolverDelegate implements QueryResolverDelegate {
	private final Map<String, String> aliasToEntityType = new HashMap<String, String>();
	private final Map<String, PropertyPath> aliasToPropertyPath = new HashMap<String, PropertyPath>();
	private String alias;

	@Override
	public void registerPersisterSpace(Tree entityName, Tree alias) {
		String put = aliasToEntityType.put(alias.getText(), entityName.getText());
		if (put != null && !put.equalsIgnoreCase(entityName.getText())) {
			throw new UnsupportedOperationException("Alias reuse currently not supported: alias " + alias.getText() + " already assigned to type " + put);
		}
	}

	@Override
	public void registerJoinAlias(Tree alias, PropertyPath path) {
		PropertyPath put = aliasToPropertyPath.put( alias.getText(), path );
		if ( put != null && !put.equals( path ) ) {
			throw new UnsupportedOperationException( "Alias reuse currently not supported: alias " + alias + " already assigned to type " + put );
		}
	}

	@Override
	public boolean isUnqualifiedPropertyReference() {
		return true;
	}

	@Override
	public PathedPropertyReferenceSource normalizeUnqualifiedPropertyReference(Tree property) {
		return new PathedPropertyReference( property.getText(), null, isAlias( property ) );
	}

	@Override
	public boolean isPersisterReferenceAlias() {
		return aliasToEntityType.containsKey( alias );
	}

	@Override
	public PathedPropertyReferenceSource normalizeUnqualifiedRoot(Tree root) {
		String entityNameForAlias = aliasToEntityType.get( root.getText() );
		if ( entityNameForAlias == null ) {
//			throw log.getUnknownAliasException( root.getText() );
		}
		return new PathedPropertyReference( root.getText(), null, true );
	}

	@Override
	public PathedPropertyReferenceSource normalizeQualifiedRoot(Tree identifier381) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PathedPropertyReferenceSource normalizePropertyPathIntermediary(PropertyPath path, Tree propertyName) {
		return new PathedPropertyReference( propertyName.getText(), null, false );
	}

	@Override
	public PathedPropertyReferenceSource normalizeIntermediateIndexOperation(PathedPropertyReferenceSource propertyReferenceSource, Tree collectionProperty, Tree selector) {
		return propertyReferenceSource;
	}

	@Override
	public void normalizeTerminalIndexOperation(PathedPropertyReferenceSource propertyReferenceSource, Tree collectionProperty, Tree selector) {
		throw new UnsupportedOperationException( "Not implemented yet" );
	}

	@Override
	public PathedPropertyReferenceSource normalizeUnqualifiedPropertyReferenceSource(Tree identifier394) {
		return null;
	}

	@Override
	public PathedPropertyReferenceSource normalizePropertyPathTerminus(PropertyPath path, Tree propertyNameNode) {
		return new PathedPropertyReference( propertyNameNode.getText(), null, false );
	}

	@Override
	public void pushFromStrategy(JoinType joinType, Tree assosiationFetchTree, Tree propertyFetchTree, Tree alias) {
		this.alias = alias.getText();
	}

	@Override
	public void pushSelectStrategy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void popStrategy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void propertyPathCompleted(PropertyPath path) {
		// TODO Auto-generated method stub

	}
	
	private boolean isAlias(Tree root) {
		return aliasToEntityType.containsKey( root.getText() ) || aliasToPropertyPath.containsKey( root.getText() );
	}

}
