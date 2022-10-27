package ru.company.framework.enums;

public enum AdditionalWarrantyType {
	NONE("Не выбрано"),
	SELLERS("Гарантия от продавца"),
	ADDITIONAL("Доп. гарантия");

	private String name;

	AdditionalWarrantyType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
