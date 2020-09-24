/**
 * 
 */
package com.tesco.finance.corestockvaluation.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author BARATH
 *
 */
@Entity
@Table(name = "SRSInput_table")
public class SRSInput implements Serializable {

	@Id
	private String aggId;
	private String location;
	private String itemNumber;
	private Date tranDate;
	private Long quantity;
	private Long retailPrice;
	private Integer tranType;
	private Character bondedStockInd;
	private Character locationType;
	private String refNo;
	private String TescoOriginCountry;
	private String batchId;

	public String getAggId() {
		return aggId;
	}

	public void setAggId(String aggId) {
		this.aggId = aggId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Character getLocationType() {
		return locationType;
	}

	public void setLocationType(Character locationType) {
		this.locationType = locationType;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public Date getTranDate() {
		return tranDate;
	}

	public void setTranDate(Date tranDate) {
		this.tranDate = tranDate;
	}

	public Integer getTranType() {
		return tranType;
	}

	public void setTranType(Integer tranType) {
		this.tranType = tranType;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(Long retailPrice) {
		this.retailPrice = retailPrice;
	}

	public Character getBondedStockInd() {
		return bondedStockInd;
	}

	public void setBondedStockInd(Character bondedStockInd) {
		this.bondedStockInd = bondedStockInd;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getTescoOriginCountry() {
		return TescoOriginCountry;
	}

	public void setTescoOriginCountry(String tescoOriginCountry) {
		TescoOriginCountry = tescoOriginCountry;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	
	

}
