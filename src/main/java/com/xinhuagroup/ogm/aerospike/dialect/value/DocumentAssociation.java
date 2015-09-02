package com.xinhuagroup.ogm.aerospike.dialect.value;

import java.util.ArrayList;
import java.util.List;

public class DocumentAssociation extends StructureValue {
	private List<Object> rows = new ArrayList<Object>();

	public DocumentAssociation() {
	}

	public List<Object> getRows() {
		return rows;
	}

	public void setRows(List<Object> rows) {
		this.rows = rows;
	}
}
