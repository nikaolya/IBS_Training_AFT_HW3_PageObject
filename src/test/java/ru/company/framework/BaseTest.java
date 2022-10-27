package ru.company.framework;

import org.openqa.selenium.support.ui.WebDriverWait;
import ru.company.framework.managers.DriverManager;
import ru.company.framework.managers.PageManager;

import java.time.Duration;

public class BaseTest {
	protected PageManager pageManager = PageManager.getPageManager();
	protected DriverManager driverManager = DriverManager.getDriverManager();
	protected WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), Duration.ofSeconds(10));
}
