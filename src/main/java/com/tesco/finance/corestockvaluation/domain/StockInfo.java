/**
 * 
 */
package com.tesco.finance.corestockvaluation.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author BARATH
 *
 */

@Entity
@Table(name = "STOCK_INFO")
public class StockInfo {

	/*
	 * @Id
	 * 
	 * @GenericGenerator(name = "assigned-sequence", strategy =
	 * "com.tesco.finance.corestockvaluation.domain.STSIdGenerator", parameters = {
	 * 
	 * @org.hibernate.annotations.Parameter(name = "sequence_name", value =
	 * "hibernate_sequence"), })
	 * 
	 * @GeneratedValue(generator = "assigned-sequence", strategy =
	 * GenerationType.SEQUENCE)
	 */
	@Id
	@Column(name = "stsid")
	private String stsId;
	@Column(name = "location", nullable = false)
	private String location;
	@Column(name = "item", nullable = false)
	private String item;
	@Column(name = "headline", nullable = false)
	private String headline;
	@Column(name = "clarifyingNote", nullable = false)
	private String clarifyingNote;
	@Column(name = "transactionDate", nullable = false)
	private String transactionDate;

	public String getStsId() {
		return stsId;
	}

	public void setStsId(String stsId) {
		this.stsId = stsId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getClarifyingNote() {
		return clarifyingNote;
	}

	public void setClarifyingNote(String clarifyingNote) {
		this.clarifyingNote = clarifyingNote;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

}
