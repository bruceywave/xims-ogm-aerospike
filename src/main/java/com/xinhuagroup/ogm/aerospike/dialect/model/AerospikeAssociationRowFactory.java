package com.xinhuagroup.ogm.aerospike.dialect.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.ogm.datastore.document.association.spi.AssociationRow.AssociationRowAccessor;
import org.hibernate.ogm.datastore.document.association.spi.StructureOptimizerAssociationRowFactory;
import org.hibernate.ogm.datastore.document.impl.DotPatternMapHelpers;

public class AerospikeAssociationRowFactory extends StructureOptimizerAssociationRowFactory<Map<String, Object>> {

	public final static AerospikeAssociationRowFactory INSTANCE = new AerospikeAssociationRowFactory();

	private AerospikeAssociationRowFactory() {
		super(Map.class);
	}

	@Override
	protected Map<String, Object> getSingleColumnRow(String columnName, Object value) {
		return Collections.singletonMap(columnName, value);
	}

	@Override
	protected AssociationRowAccessor<Map<String, Object>> getAssociationRowAccessor(String[] prefixedColumns, String prefix) {
		return prefix != null ? new AerospikeAssociationRowAccessor(prefixedColumns, prefix) : AerospikeAssociationRowAccessor.INSTANCE;
	}

	private static class AerospikeAssociationRowAccessor implements AssociationRowAccessor<Map<String, Object>> {
		public final static AerospikeAssociationRowAccessor INSTANCE = new AerospikeAssociationRowAccessor();

		private final List<String> prefixedColumns;
		private final String prefix;

		public AerospikeAssociationRowAccessor() {
			this(null, null);
		}

		public AerospikeAssociationRowAccessor(String[] prefixedColumns, String prefix) {
			this.prefix = prefix;
			if (prefix != null) {
				this.prefixedColumns = Arrays.asList(prefixedColumns);
			} else {
				this.prefixedColumns = Collections.emptyList();
			}
		}

		/**
		 * 只能存在一个前缀
		 * 
		 * @param prefixedColumn
		 * @return
		 */
		private String unprefix(String prefixedColumn) {
			return prefixedColumn.substring(prefix.length() + 1); // name + "."
		}

		@Override
		public Set<String> getColumnNames(Map<String, Object> row) {
			Set<String> columnNames = new HashSet<>(row.keySet());
			addColumnNames(row, columnNames, "");
			for (String prefixedColumn : prefixedColumns) {
				String unprefixedColumn = unprefix(prefixedColumn);
				if (columnNames.contains(unprefixedColumn)) {
					columnNames.remove(unprefixedColumn);
					columnNames.add(prefixedColumn);
				}
			}
			return columnNames;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		private void addColumnNames(Map<String, Object> row, Set<String> columnNames, String prefix) {
			for (String field : row.keySet()) {
				Object sub = row.get(field);
				if (sub instanceof Map) {
					addColumnNames((Map) sub, columnNames, DotPatternMapHelpers.flatten(prefix, field));
				} else {
					columnNames.add(DotPatternMapHelpers.flatten(prefix, field));
				}
			}
		}

		@Override
		public Object get(Map<String, Object> row, String column) {
			if (prefixedColumns.contains(column)) {
				column = unprefix(column);
			}
			if (row.containsKey(column)) {
				return row.get(column);
			}
			return DotPatternMapHelpers.getValueOrNull(row, column);
		}
	}
}
