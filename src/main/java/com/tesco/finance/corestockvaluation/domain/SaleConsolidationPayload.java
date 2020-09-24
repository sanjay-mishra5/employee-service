/**
 * 
 */
package com.tesco.finance.corestockvaluation.domain;

import javax.persistence.Column;

import com.couchbase.client.core.deps.com.fasterxml.jackson.annotation.JsonIgnore;

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
public class SaleConsolidationPayload {

	private String lconactItem;
	private Long liKey;
	@JsonIgnore
	private Exception deSerializerException;
	
	
}
