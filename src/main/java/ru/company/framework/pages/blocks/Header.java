package ru.company.framework.pages.blocks;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.company.framework.enums.HeaderButton;
import ru.company.framework.helpers.Product;
import ru.company.framework.helpers.ProductCart;
import ru.company.framework.managers.PageManager;
import ru.company.framework.pages.BasePage;
import ru.company.framework.pages.CartPage;
import ru.company.framework.pages.SearchResultPage;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Header extends BasePage {
	@FindBy(xpath = "//button[contains(@class, 'confirm-city__btn')]")
	private WebElement confirmCityBtn;

	@FindBy(xpath = "//form[@class='presearch']//input")
	private WebElement inputField;

	@FindBy(xpath = "//form[contains(@class, 'presearch_autocomplete')]//input")
	public WebElement inputFieldAfterClick;

	@FindBy(xpath = "//div[@class='buttons']/a")
	public List<WebElement> buttons;

	@FindBy(xpath = "//span[contains(@class, 'cart-link__badge')]")
	public WebElement cartBadge;

	private final String CART_TOTAL_PRICE_NESTED_XPATH = ".//span[@class='cart-link__price']";

	public Header confirmAutomaticCitySelection(){
		confirmCityBtn.click();
		return this;
	}

	public SearchResultPage searchFor(String text){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		inputField.click();
		inputFieldAfterClick.sendKeys(text, Keys.ENTER);
		return pageManager.getSearchResultPage();
	}

	public WebElement getHeaderButton(HeaderButton button){
		wait.until(ExpectedConditions.visibilityOfAllElements(buttons));
		WebElement element = buttons.stream()
				.filter(i -> i.getAttribute("class").contains(button.getXpath())).findFirst().orElse(null);
		Assertions.assertNotNull(element, String.format("Не найдена кнопка %s", button));
		return element;
	}

	public int getTotalCartPrice(){
		wait.until(ExpectedConditions.visibilityOf(getHeaderButton(HeaderButton.CART).findElement(By.xpath(CART_TOTAL_PRICE_NESTED_XPATH))));
		String priceAsString = getHeaderButton(HeaderButton.CART).findElement(By.xpath(CART_TOTAL_PRICE_NESTED_XPATH)).getText();
		return convertStringToInteger(priceAsString);
	}

	public int getCartBadge(){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		if (cartBadge.getAttribute("class").contains("empty")){
			return 0;
		} else {
			return convertStringToInteger(cartBadge.getText());
		}
	}

	public Header checkTotalPriceCorrectness(ProductCart cart){
		int sum = cart.getProductsInCart().stream().mapToInt(Product::getFullPrice).sum();
		assertThat("Суммарная стоимость покупки посчитана не верно", sum, is(getTotalCartPrice()));
		return this;
	}

	public CartPage pressCartButton(){
		wait.until(ExpectedConditions.elementToBeClickable(getHeaderButton(HeaderButton.CART)));
		getHeaderButton(HeaderButton.CART).click();
		return pageManager.getCartPage();
	}



}
