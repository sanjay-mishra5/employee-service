/**
 * 
 */
package com.tesco.finance.corestockvaluation.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author BARATH
 *
 */
@Entity
@Table(name = "SALE_CONSOLIDATION")
public class SaleConsolidation {

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "increment")
	@Column(name = "ID")
	private Long id;
	@Column(name = "LIKey", nullable = false)
	private Long liKey;
	@Column(name = "LConactItem", nullable = false)
	private String lconactItem;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLiKey() {
		return liKey;
	}
	public void setLiKey(Long liKey) {
		this.liKey = liKey;
	}
	public String getLconactItem() {
		return lconactItem;
	}
	public void setLconactItem(String lconactItem) {
		this.lconactItem = lconactItem;
	}
	
	
	

}
