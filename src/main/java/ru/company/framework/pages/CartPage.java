package ru.company.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.*;
import ru.company.framework.helpers.Product;
import ru.company.framework.helpers.ProductCart;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CartPage extends BasePage{

	@FindBy(xpath = "//h1[contains(@class, 'cart-title')]")
	private WebElement title;

	@FindBy(xpath = "//div[contains(@class, 'cart-items__products')]/div[contains(@class, 'cart-items__product')]")
	private List<WebElement> productsList;

	@FindBy(xpath = "//div[contains(@class, 'summary__price')]//span[contains(@class, 'price__current')]")
	private WebElement totalPrice;

	@FindBy(xpath = "//div[contains(@class, 'cart-tab-total-amount')]//span[@class='restore-last-removed']")
	private WebElement restoreRemovedProductBtn;

	private final String CHOSEN_WARRANTY_NESTED_XPATH = ".//div[contains(@class, 'additional-warranties-row__warranty')]/div[contains(@class, 'checked')]";
	private final String CHOSEN_WARRANTY_PRICE_NESTED_XPATH = "/following-sibling::span[contains(@class, 'price')]";
	private final String PRODUCT_TITLE_NESTED_XPATH = ".//div[contains(@class, 'cart-items__product-name')]";
	private final String PRODUCT_CODE_NESTED_XPATH = ".//div[@class = 'cart-items__product-code']";
	private final String PRODUCT_PRICE_NESTED_XPATH = ".//div[@class = 'cart-items__product-price']//span[contains(@class, 'price__current')]";
	private final String MINUS_BUTTON_NESTED_XPATH = ".//button[./i[contains(@class, 'count-buttons__icon-minus')]]";
	private final String PLUS_BUTTON_NESTED_XPATH = ".//button[./i[contains(@class, 'count-buttons__icon-plus')]]";



	public CartPage checkThatCartPageIsOpened(){
		wait.until(ExpectedConditions.visibilityOf(title));
		assertThat("Страница Корзина не открылась", title.getText(), is("Корзина"));
		return this;
	}

	public WebElement findProductInCart(int code){
		WebElement product = productsList.stream()
					.filter(i -> i.findElement(By.xpath(PRODUCT_CODE_NESTED_XPATH)).getText().equals(String.valueOf(code)))
					.findFirst().orElse(null);
		Assertions.assertNotNull(product, "В корзине нет такого товара");
		return product;
	}

	public CartPage checkProductCartContent(ProductCart cart){
		WebElement product;
		for (Product item : cart.getProductsInCart()){
			product = findProductInCart(item.getProductCode());
			assertThat("Имя товара не верно",
					getTitle(product), containsString(item.getProductName()));
			assertThat("Цена товара не верна",
					getPrice(product), is(item.getBasePrice()));
			if (item.isChosenAdditionalWarranty()){
				assertThat("Выбрана не верная гарантия",
						product.findElement(By.xpath(CHOSEN_WARRANTY_NESTED_XPATH)).getText(),
						containsString(item.getAdditionalWarrantyDuration().getName()));
			}
		}
		return this;
	}

	public int getTotalPrice(){
		return convertStringToInteger(totalPrice.getText());
	}

	public CartPage decreaseProductQuantity(ProductCart cart, int productCode, int quantity){
		WebElement product = findProductInCart(productCode);
		int price1 = getTotalPrice();
		int productPrice = getPrice(product);
		if (cart.getProductByCode(productCode).isChosenAdditionalWarranty()){
			productPrice += getWarrantyPrice(product);
		}
		for (int i = 0; i < quantity; i++) {
			int badge = pageManager.getHeader().getCartBadge();
			product.findElement(By.xpath(MINUS_BUTTON_NESTED_XPATH)).click();
			waitForBadgeChange(badge);
		}
		int price2 = getTotalPrice();
		assertThat("Изменение общей стоимости корзины не верно", price2, is(price1 - productPrice * quantity));
		return this;
	}

//	private void waitForPriceChange(int price){
//		Wait<WebDriver> wait = new FluentWait<>(driverManager.getDriver())
//				.withTimeout(Duration.ofSeconds(10))
//				.pollingEvery(Duration.ofMillis(500))
//				.ignoring(NumberFormatException.class);
//		wait.until(new ExpectedCondition<Boolean>() {
//			@Override
//			public Boolean apply(WebDriver webDriver) {
//				if (getTotalPrice() != price){
//					return true;
//				} else {
//					return false;
//				}
//			}
//		});
//	}

	public CartPage increaseProductQuantity(ProductCart cart, int productCode, int quantity){
		WebElement product = findProductInCart(productCode);
		int price1 = getTotalPrice();
		int productPrice = getPrice(product);
		if (cart.getProductByCode(productCode).isChosenAdditionalWarranty()){
			productPrice += getWarrantyPrice(product);
		}
		for (int i = 0; i < quantity; i++) {
			int badge = pageManager.getHeader().getCartBadge();
			product.findElement(By.xpath(PLUS_BUTTON_NESTED_XPATH)).click();
			waitForBadgeChange(badge);
		}
		int price2 = getTotalPrice();
		assertThat("Изменение общей стоимости корзины не верно", price2, is(price1 + productPrice * quantity));
		return this;
	}

	public CartPage restoreRemovedProduct(ProductCart cart, int productCode){
		Product product = cart.getProductByCode(productCode);
		int price1 = getTotalPrice();
		int productPrice = product.getBasePrice();
		if (product.isChosenAdditionalWarranty()){
			productPrice += product.getAdditionalWarrantyPrice();
		}
		int badge = pageManager.getHeader().getCartBadge();
		restoreRemovedProductBtn.click();
		waitForBadgeChange(badge);
		int price2 = getTotalPrice();
		assertThat("Изменение общей стоимости корзины не верно", price2, is(price1 + productPrice));
		return this;
	}

	private int getPrice(WebElement product){
		return convertStringToInteger(product.findElement(By.xpath(PRODUCT_PRICE_NESTED_XPATH)).getText());
	}

	private String getTitle(WebElement product){
		return product.findElement(By.xpath(PRODUCT_TITLE_NESTED_XPATH)).getText();
	}

	private int getWarrantyPrice(WebElement product){
		return convertStringToInteger(product.findElement(By.xpath(CHOSEN_WARRANTY_NESTED_XPATH
				+ CHOSEN_WARRANTY_PRICE_NESTED_XPATH)).getText());
	}
}
