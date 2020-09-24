/**
 * 
 */
package com.tesco.finance.corestockvaluation.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

/**
 * @author BARATH
 *
 */
@Entity
@Table(name = "eventPayload")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class EventInfo implements Serializable {

	@Id
	private String eventId;
	@Type(type = "jsonb")
	@Column(columnDefinition = "jsonb")
	private EventPayload payload;

	private String traceId;
	private String type;
	private String clientId;
	private ZonedDateTime timestamp;
	private String orderReference;
	private String storeId;
	private ZonedDateTime paidDate;
	private String channel;
	private String financeCountry;
	private String currency;

	@OneToMany(mappedBy = "eventPayload", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<STSInfo> stsInfoDetails;

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public EventPayload getPayload() {
		return payload;
	}

	public void setPayload(EventPayload payload) {
		this.payload = payload;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getOrderReference() {
		return orderReference;
	}

	public void setOrderReference(String orderReference) {
		this.orderReference = orderReference;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getFinanceCountry() {
		return financeCountry;
	}

	public void setFinanceCountry(String financeCountry) {
		this.financeCountry = financeCountry;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Set<STSInfo> getStsInfoDetails() {
		return stsInfoDetails;
	}

	public void setStsInfoDetails(Set<STSInfo> stsInfoDetails) {
		this.stsInfoDetails = stsInfoDetails;
		stsInfoDetails.forEach(entity -> entity.setEventPayload(this));
	}

	public ZonedDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(ZonedDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public ZonedDateTime getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(ZonedDateTime paidDate) {
		this.paidDate = paidDate;
	}
	
	

}
