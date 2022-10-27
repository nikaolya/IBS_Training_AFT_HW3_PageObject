package ru.company.framework.managers;

import ru.company.framework.pages.CartPage;
import ru.company.framework.pages.blocks.Header;
import ru.company.framework.pages.ProductPage;
import ru.company.framework.pages.SearchResultPage;

public class PageManager {
	private static PageManager pageManager = null;
	private PageManager() {
	}

	private HomePage homePage;
	private Header header;
	private SearchResultPage searchResultPage;
	private ProductPage productPage;
	private CartPage cartPage;

	public static PageManager getPageManager() {
		if (pageManager == null) {
			pageManager = new PageManager();
		}
		return pageManager;
	}

	public HomePage getHomePage() {
		if (homePage == null) {
			homePage = new HomePage();
		}
		return homePage;
	}

	public Header getHeader() {
		if (header == null) {
			header = new Header();
		}
		return header;
	}

	public SearchResultPage getSearchResultPage() {
		if (searchResultPage == null) {
			searchResultPage = new SearchResultPage();
		}
		return searchResultPage;
	}

	public ProductPage getProductPage() {
		return new ProductPage();
	}

	public CartPage getCartPage() {
		if (cartPage == null) {
			cartPage = new CartPage();
		}
		return cartPage;
	}
}
