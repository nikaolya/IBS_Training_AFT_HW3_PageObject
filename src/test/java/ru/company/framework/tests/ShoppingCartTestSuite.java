package ru.company.framework.tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Test Suite: Корзина")
@SelectClasses({ShoppingCartTest.class})
public class ShoppingCartTestSuite {

}
