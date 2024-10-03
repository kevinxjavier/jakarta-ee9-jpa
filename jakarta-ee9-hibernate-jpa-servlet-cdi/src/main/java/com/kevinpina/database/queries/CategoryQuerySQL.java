package com.kevinpina.database.queries;

public enum CategoryQuerySQL {

	SELECT_ALL("SELECT * FROM category"),
	SELECT_BY_ID("SELECT * FROM category WHERE id = ?");

	private String sql;

	CategoryQuerySQL(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}

}
