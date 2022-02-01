package com.bashkarsampath.app.enums;

public enum UserRole {

	ROLE_ADMIN("ADMIN"), ROLE_CEO("CEO"), ROLE_MANAGER("MANAGER"),
	ROLE_SUPERVISOR("SUPERVISOR"), ROLE_CREATOR("CREATOR"),
	ROLE_ANALYST("ANALYST"), ROLE_EMPLOYEE("EMPLOYEE");

	private String value;

	UserRole(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
