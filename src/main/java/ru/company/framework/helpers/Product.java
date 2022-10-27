package ru.company.framework.helpers;

import ru.company.framework.enums.AdditionalWarrantyDuration;
import ru.company.framework.enums.AdditionalWarrantyType;

import static ru.company.framework.enums.AdditionalWarrantyDuration.ONE_YEAR;
import static ru.company.framework.enums.AdditionalWarrantyType.NONE;

public class Product {
	private String productName;
	private int productCode;
	private int basePrice;
	private int fullPrice;
	private boolean chosenAdditionalWarranty = false;
	private AdditionalWarrantyType additionalWarrantyType = NONE;
	private AdditionalWarrantyDuration additionalWarrantyDuration = ONE_YEAR;
	private int additionalWarrantyPrice = 0;

	public Product(int productCode, String productName, int basePrice) {
		this.productCode = productCode;
		this.productName = productName;
		this.basePrice = basePrice;
	}

	public AdditionalWarrantyType getAdditionalWarrantyType() {
		return additionalWarrantyType;
	}

	public void setAdditionalWarrantyType(AdditionalWarrantyType additionalWarrantyType) {
		this.additionalWarrantyType = additionalWarrantyType;
	}

	public AdditionalWarrantyDuration getAdditionalWarrantyDuration() {
		return additionalWarrantyDuration;
	}

	public void setAdditionalWarrantyDuration(AdditionalWarrantyDuration additionalWarrantyDuration) {
		this.additionalWarrantyDuration = additionalWarrantyDuration;
	}

	public int getAdditionalWarrantyPrice() {
		return additionalWarrantyPrice;
	}

	public void setAdditionalWarrantyPrice(int additionalWarrantyPrice) {
		this.additionalWarrantyPrice = additionalWarrantyPrice;
	}

	public boolean isChosenAdditionalWarranty() {
		return chosenAdditionalWarranty;
	}

	public void setChosenAdditionalWarranty(boolean chosenAdditionalWarranty) {
		this.chosenAdditionalWarranty = chosenAdditionalWarranty;
	}

	public String getProductName() {
		return productName;
	}

	public int getProductCode() {
		return productCode;
	}

	public int getBasePrice() {
		return basePrice;
	}

	public int getFullPrice() {
		return fullPrice;
	}

	public void setBasePrice(int basePrice) {
		this.basePrice = basePrice;
	}

	public void setFullPrice(int fullPrice) {
		this.fullPrice = fullPrice;
	}
}
