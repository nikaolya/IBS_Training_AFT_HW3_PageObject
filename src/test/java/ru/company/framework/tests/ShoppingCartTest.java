package ru.company.framework.tests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.*;
import ru.company.framework.BaseTest;
import ru.company.framework.enums.AdditionalWarrantyDuration;
import ru.company.framework.enums.AdditionalWarrantyType;
import ru.company.framework.helpers.ProductCart;
import ru.company.framework.managers.PropManager;
import ru.company.framework.utils.ConstProp;


public class ShoppingCartTest extends BaseTest {

	@BeforeEach
	public void setup() {
		driverManager.getDriver().get(PropManager.getPropManager().getProperty(ConstProp.BASE_URL));
	}

	@AfterEach
	public void tearDown() {
		driverManager.quitDriver();
	}

	@Test
	@Tags({@Tag("UI")})
	@DisplayName("Добавление нескольких товаров в корзину")
	@Severity(SeverityLevel.NORMAL)
	public void shouldChangePriceInShoppingCartTest(){
		ProductCart cart = new ProductCart();

		pageManager.getHeader().confirmAutomaticCitySelection()
				.searchFor("Телевизор LG")
				.openProductPage("Телевизор LED LG 55UN68006LA")
				.selectAdditionalWarranty(AdditionalWarrantyType.ADDITIONAL, AdditionalWarrantyDuration.TWO_YEAR)
				.addToCart(cart);

		pageManager.getHeader()
				.searchFor("робот пылесос polaris")
				.openProductPage("Polaris PVCR 1229 IQ Home Aqua")
				.addToCart(cart);

		pageManager.getHeader()
				.checkTotalPriceCorrectness(cart)
				.pressCartButton()
				.checkThatCartPageIsOpened()
				.checkProductCartContent(cart)
				.decreaseProductQuantity(cart, 5323754, 1)
				.increaseProductQuantity(cart, 5341805,2)
				.restoreRemovedProduct(cart, 5323754);
	}
}
