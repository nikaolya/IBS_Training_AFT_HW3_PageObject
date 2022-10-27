package ru.company.framework.managers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropManager {
	private final Properties properties = new Properties();

	private static PropManager INSTANCE = null;

	private PropManager() {
		loadApplicationProperties();
		loadCustomProperties();
	}

	public static PropManager getPropManager() {
		if (INSTANCE == null) {
			INSTANCE = new PropManager();
		}
		return INSTANCE;
	}

	private void loadApplicationProperties() {
		final String fileName = String.format("src\\main\\resources\\%s.properties",
				System.getProperty("propFile", "application"));
		try (FileInputStream inputStream = new FileInputStream(fileName)) {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadCustomProperties() {
		properties.forEach((key, value) -> System.getProperties()
				.forEach((customUserKey, customUserValue) -> {
					if (key.toString().equals(customUserKey.toString()) &&
							!value.toString().equals(customUserValue.toString())) {
						properties.setProperty(key.toString(), customUserValue.toString());
					}
				}));
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}
}
