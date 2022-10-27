package ru.company.framework.managers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.company.framework.utils.ConstProp;

public class DriverManager {
	private WebDriver driver;
	private static DriverManager driverManager = null;
	private final PropManager props = PropManager.getPropManager();

	private DriverManager() {
	}

	public static DriverManager getDriverManager() {
		if (driverManager == null) {
			driverManager = new DriverManager();
		}
		return driverManager;
	}

	public WebDriver getDriver() {
		if (driver == null) {
			initDriver();
		}
		return driver;
	}

	public void quitDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}

	private void initDriver() {
		if (props.getProperty(ConstProp.BROWSER).equals("chrome")){
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized");
			driver = new ChromeDriver(options);
		}
	}
}
