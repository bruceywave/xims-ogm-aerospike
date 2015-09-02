package com.xinhuagroup.ogm.aerospike.dialect.model;

import java.util.Collections;
import java.util.List;

import com.xinhuagroup.ogm.aerospike.dialect.value.DocumentAssociation;

public class DocumentBasedAssociation extends AbstractAerospikeAssociation {
	private final DocumentAssociation document;

	public DocumentBasedAssociation(DocumentAssociation document) {
		this.document = document;
	}

	@Override
	public Object getRows() {
		return document.getRows();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setRows(Object rows) {
		if ( rows instanceof List ) {
			document.setRows( (List) rows );
		}
		else {
			document.setRows( Collections.singletonList( rows ) );
		}
	}

	@Override
	public Object getOwningDocument() {
		return document;
	}

}
