/**
 * 
 */
package com.tesco.finance.corestockvaluation.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author BARATH
 *
 */
@Entity
@Table(name = "STSPayload_Info")
public class STSInfo implements Serializable {

	@Id
	private String stsId;
	private Long qty;
	private Long retailPrice;
	private String unitOfMeasure;
	private Long P1;
	private Long P2;
	private Long P3;
	private String gtin;
	private String stockKeepingUnit;
	private ZonedDateTime paidDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "eventId", nullable = false)
	private EventInfo eventPayload;

	public String getStsId() {
		return stsId;
	}

	public void setStsId(String stsId) {
		this.stsId = stsId;
	}

	public Long getQty() {
		return qty;
	}

	public void setQty(Long qty) {
		this.qty = qty;
	}

	public Long getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(Long retailPrice) {
		this.retailPrice = retailPrice;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public Long getP1() {
		return P1;
	}

	public void setP1(Long p1) {
		P1 = p1;
	}

	public Long getP2() {
		return P2;
	}

	public void setP2(Long p2) {
		P2 = p2;
	}

	public Long getP3() {
		return P3;
	}

	public void setP3(Long p3) {
		P3 = p3;
	}

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

	public EventInfo getEventPayload() {
		return eventPayload;
	}

	public void setEventPayload(EventInfo eventPayload) {
		this.eventPayload = eventPayload;
	}

	public ZonedDateTime getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(ZonedDateTime paidDate) {
		this.paidDate = paidDate;
	}

	
	
}
