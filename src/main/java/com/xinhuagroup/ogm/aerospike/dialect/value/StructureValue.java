package com.xinhuagroup.ogm.aerospike.dialect.value;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public abstract class StructureValue {
	public StructureValue() {
	}

	@Override
	public String toString() {
		return JsonToStringHelper.toString(this);
	}
	
	private static class JsonToStringHelper {
		private static final ObjectWriter writer = new ObjectMapper().writerWithDefaultPrettyPrinter();

		private static String toString(StructureValue structuredValue) {
			try {
				return writer.writeValueAsString(structuredValue);
			} catch (Exception e) {
				return e.toString();
			}
		}
	}
}
