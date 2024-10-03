package com.kevinpina.database.fields;

public enum ProductFieldSQL {

	PRODUCT("product"), ID("id"), NAME("name"), PRICE("price"), DATE("date"), SKU("sku"), CATEGORY_ID("category_id");

	private String field;

	ProductFieldSQL(String field) {
		this.field = field;
	}

	public String getField() {
		return field;
	}

}
