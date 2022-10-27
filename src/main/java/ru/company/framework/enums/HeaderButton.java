package ru.company.framework.enums;

public enum HeaderButton {
	COMPARE("compare-link"),
	WISHLIST("wishlist-link"),
	CART("cart-link");

	private String xpath;

	HeaderButton(String xpath) {
		this.xpath = xpath;
	}

	public String getXpath() {
		return xpath;
	}
}
