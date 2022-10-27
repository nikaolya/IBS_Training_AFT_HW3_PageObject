package ru.company.framework.enums;

public enum AdditionalWarrantyDuration {
	ONE_YEAR("12 мес"),
	TWO_YEAR("24 мес");

	private String name;

	AdditionalWarrantyDuration(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
