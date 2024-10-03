package com.kevinpina.database.fields;

public enum CategoryFieldSQL {

	CATEGORY("category"), ID("id"), NAME("name");

	private String field;

	CategoryFieldSQL(String field) {
		this.field = field;
	}

	public String getField() {
		return field;
	}
}
