package ru.company.framework.helpers;

import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class ProductCart {
	private List<Product> productsInCart = new ArrayList<>();

	public void addToCart(Product product){
		productsInCart.add(product);
	}

	public Product getProductByName(String title) {
		Product product = productsInCart.stream().filter(i -> i.getProductName().contains(title)).findFirst().orElse(null);
		Assertions.assertNotNull(product, "В корзине нет такого товара");
		return product;
	}

	public Product getProductByCode(int code) {
		Product product = productsInCart.stream().filter(i -> i.getProductCode() == code).findFirst().orElse(null);
		Assertions.assertNotNull(product, "В корзине нет такого товара");
		return product;
	}

	public List<Product> getProductsInCart() {
		return productsInCart;
	}
}
