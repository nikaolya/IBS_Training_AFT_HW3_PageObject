package ru.company.framework.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.company.framework.managers.DriverManager;
import ru.company.framework.managers.PageManager;
import ru.company.framework.managers.PropManager;
import ru.company.framework.pages.blocks.Header;

import java.time.Duration;

public class BasePage {
	protected final DriverManager driverManager = DriverManager.getDriverManager();
	protected PageManager pageManager = PageManager.getPageManager();
	private final PropManager props = PropManager.getPropManager();

	protected Actions actions = new Actions(driverManager.getDriver());
	protected JavascriptExecutor js = (JavascriptExecutor) driverManager.getDriver();
	protected WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), Duration.ofSeconds(10));

	public BasePage() {
		PageFactory.initElements(driverManager.getDriver(), this);
	}

	protected WebElement scrollToElementJs(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true);", element);
		return element;
	}

	public Header getHeader(){
		return pageManager.getHeader();
	}

	public int convertStringToInteger(String text){
		return Integer.parseInt(text.replaceAll("\\D", ""));
	}

	protected void waitForBadgeChange(int badge){
		Wait<WebDriver> wait = new FluentWait<>(driverManager.getDriver())
				.withTimeout(Duration.ofSeconds(10))
				.pollingEvery(Duration.ofMillis(500))
				.ignoring(NoSuchElementException.class);
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				if (pageManager.getHeader().getCartBadge() != badge){
					return true;
				} else {
					return false;
				}
			}
		});
	}
}
