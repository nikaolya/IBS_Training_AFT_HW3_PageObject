package ru.company.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SearchResultPage extends BasePage {

	@FindBy(xpath = "//div[@data-id='product' and @data-preview-slider-inited]")
	private List<WebElement> productsList;

	private final String PRODUCT_LINK_NESTED_XPATH = ".//a[contains(@class, 'catalog-product__name')]";

	public WebElement findProductByName(String productName){
		WebElement product = productsList.stream()
				.filter(i -> i.findElement(By.xpath(PRODUCT_LINK_NESTED_XPATH))
						.getText().contains(productName)).findFirst().orElse(null);
		assertNotNull(product, "На странице нет такого продукта");
		return product;
	}

	public WebElement findProductByCode(int productCode){
		WebElement product = productsList.stream()
				.filter(i -> i.getAttribute("data-code").equals(String.valueOf(productCode)))
				.findFirst().orElse(null);
		assertNotNull(product, "На странице нет такого продукта");
		return product;
	}

	public ProductPage openProductPage(String productName){
		findProductByName(productName).findElement(By.xpath(PRODUCT_LINK_NESTED_XPATH)).click();
		return pageManager.getProductPage();
	}

	public ProductPage openProductPage(int productCode){
		findProductByCode(productCode).click();
		return pageManager.getProductPage();
	}
}
