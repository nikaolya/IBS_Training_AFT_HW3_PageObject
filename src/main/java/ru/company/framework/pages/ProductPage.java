package ru.company.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.*;
import ru.company.framework.enums.AdditionalSaleTab;
import ru.company.framework.enums.AdditionalWarrantyDuration;
import ru.company.framework.enums.AdditionalWarrantyType;
import ru.company.framework.helpers.Product;
import ru.company.framework.helpers.ProductCart;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

public class ProductPage extends BasePage{
	private Product product;

	public ProductPage(){
		product = new Product(getProductCode(), getTitle(), getActualPrice());
	}

	@FindBy(xpath = "//h1[@data-product-title]")
	private WebElement title;

	@FindBy(xpath = "//div[contains(@class, 'product-buy__price')]")
	private WebElement activePrice;

	@FindBy(xpath = "//div[@class='product-card-top__code']")
	private WebElement productCode;

	@FindBy(xpath = "//span[@class='additional-sales-tabs__title-price']")
	private WebElement warrantyPrice;

	@FindBy(xpath = "//div[contains(@class, 'product-buy__sub')]")
	private WebElement priceIsChangedLabel;

	@FindBy(xpath = "//div[contains(@class, 'additional-sales-tabs__title') and not(contains(@class, 'wrap'))]")
	private List<WebElement> additionalSaleTabsList;

	@FindBy(xpath = "//div[contains(@class, 'additional-sales-tabs__content') and contains(@class, 'active')]")
	private WebElement additionalSaleContent;

	@FindBy(xpath = "//span[@class='ui-radio__content']")
	private List<WebElement> additionalWarrantyTypeList;

	@FindBy(xpath = "//div[contains(@class, 'top__buy')]//button[contains(@class, 'buy-btn')]")
	private WebElement addtoCartBtn;

	@FindBy(xpath = "//div[@data-role = 'soft-bundle-widget-summary']/div[contains(text(), 'Комплект')]")
	private WebElement setSummary;

	private final String WARRANTY_DURATION_NESTED_XPATH = "./span[contains(@class, 'product-warranty__period')]";

	private ProductPage saveProductDetails(ProductCart cart){
		product.setFullPrice(getActualPrice());
		assertThat("Цена с учетом гарантии посчитана не верно",
				product.getBasePrice() + product.getAdditionalWarrantyPrice(), is(product.getFullPrice()));
		cart.addToCart(product);
		return this;
	}

	private String getTitle(){
		return title.getText();
	}

	private int getActualPrice(){
		return convertStringToInteger(activePrice.getText().split("₽")[0]);
	}

	private int getWarrantyPrice(){
		try {
			WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), Duration.ofSeconds(1));
			wait.until(ExpectedConditions.visibilityOf(warrantyPrice));
			return convertStringToInteger(warrantyPrice.getText());
		} catch (TimeoutException ignore){
			return 0;
		}
	}

	private int getProductCode(){
		return convertStringToInteger(productCode.getText());
	}

	private void waitUntilPriceIsChanged(){
		wait.until(ExpectedConditions.visibilityOf(priceIsChangedLabel));
		assertThat("Цена товара не изменена", priceIsChangedLabel.getText(), is("цена изменена"));
	}

	private WebElement getAdditionalSaleTab(AdditionalSaleTab option){
		WebElement element = additionalSaleTabsList.stream().filter(i -> i.getText().contains(option.getName())).findFirst().orElse(null);
		Assertions.assertNotNull(element, "Нет такой вкладки на странице");
		return element;
	}

	private List<WebElement> selectAdditionalWarrantyType(AdditionalWarrantyType option){
		return additionalWarrantyTypeList.stream()
				.filter(i -> i.getText().contains(option.getName())).collect(Collectors.toList());
	}

	private WebElement selectAdditionalWarrantyDuration(List<WebElement> list, AdditionalWarrantyDuration option){
		WebElement element = list.stream().filter(i -> i.findElement(By.xpath(WARRANTY_DURATION_NESTED_XPATH)).getText()
				.contains(option.getName())).findFirst().orElse(null);
		Assertions.assertNotNull(element, "Нет такой продолжительности страховки");
		return element;
	}

	public ProductPage selectAdditionalWarranty(AdditionalWarrantyType type, AdditionalWarrantyDuration duration){
		try{
			getAdditionalSaleTab(AdditionalSaleTab.WARRANTY).click();
		} catch (NullPointerException e){
			Assertions.fail("Кнопка \"Гарантия\" не нажата");
		}
		wait.until(ExpectedConditions.visibilityOfAllElements(additionalWarrantyTypeList));

		List<WebElement> warrantyTypeList = selectAdditionalWarrantyType(type);
		WebElement selector = selectAdditionalWarrantyDuration(warrantyTypeList, duration);
		wait.until(ExpectedConditions.elementToBeClickable(selector)).click();
		waitUntilPriceIsChanged();

		product.setChosenAdditionalWarranty(true);
		product.setAdditionalWarrantyType(type);
		product.setAdditionalWarrantyDuration(duration);
		product.setAdditionalWarrantyPrice(getWarrantyPrice());

		return this;
	}

	public ProductPage addToCart(ProductCart cart){
		saveProductDetails(cart);
		int before = getHeader().getCartBadge();
		addtoCartBtn.click();
		waitForBadgeChange(before);
		int after = getHeader().getCartBadge();
		assertThat("Количество товаров в корзине не увеличилось", after>before, is(true));
		return this;
	}
}
