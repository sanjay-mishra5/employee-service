/**
 * 
 */
package com.tesco.finance.corestockvaluation.domain;

import java.time.ZonedDateTime;

/**
 * @author BARATH
 *
 */
public class Product {

	private String gtin;
	private String stockKeepingUnit;
	private Long priceAfterDiscounts;
	private Long priceBeforeDiscounts;
	private String unitOfMeasure;
	private Long quantity;
	private ZonedDateTime paidDate;

	public String getGtin() {
		return gtin;
	}

	public void setGtin(String gtin) {
		this.gtin = gtin;
	}

	public String getStockKeepingUnit() {
		return stockKeepingUnit;
	}

	public void setStockKeepingUnit(String stockKeepingUnit) {
		this.stockKeepingUnit = stockKeepingUnit;
	}

	public Long getPriceAfterDiscounts() {
		return priceAfterDiscounts;
	}

	public void setPriceAfterDiscounts(Long priceAfterDiscounts) {
		this.priceAfterDiscounts = priceAfterDiscounts;
	}

	public Long getPriceBeforeDiscounts() {
		return priceBeforeDiscounts;
	}

	public void setPriceBeforeDiscounts(Long priceBeforeDiscounts) {
		this.priceBeforeDiscounts = priceBeforeDiscounts;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public ZonedDateTime getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(ZonedDateTime paidDate) {
		this.paidDate = paidDate;
	}

	@Override
	public String toString() {
		return "Product [gtin=" + gtin + ", stockKeepingUnit=" + stockKeepingUnit + ", priceAfterDiscounts="
				+ priceAfterDiscounts + ", priceBeforeDiscounts=" + priceBeforeDiscounts + ", unitOfMeasure="
				+ unitOfMeasure + ", quantity=" + quantity + ", paidDate=" + paidDate + "]";
	}



}
