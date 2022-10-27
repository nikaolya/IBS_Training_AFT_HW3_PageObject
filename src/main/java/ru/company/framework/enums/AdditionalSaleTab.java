package ru.company.framework.enums;

public enum AdditionalSaleTab {
	ACCESSORIES("Аксессуары"),
	WARRANTY("Гарантия"),
	ANALOG("Аналоги");

	private String name;

	AdditionalSaleTab(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
