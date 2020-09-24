/**
 * 
 */
package com.tesco.finance.corestockvaluation.domain;

import java.time.ZonedDateTime;
import java.util.List;

import com.couchbase.client.core.deps.com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author BARATH
 *
 */

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
public class EventPayload {

	
	private Event event;
	private String storeId;
	private String orderReference;
	private String paidDate;
	private String channel;
	private String financeCountry;
	private String currency;
	private List<Product> products;
	private List<Return> returns;
	private List<Reward> rewards;

	@JsonIgnore
	private Exception deSerializerException;

}
