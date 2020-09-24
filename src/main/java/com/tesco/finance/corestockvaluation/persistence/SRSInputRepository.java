/**
 * 
 */
package com.tesco.finance.corestockvaluation.persistence;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tesco.finance.corestockvaluation.domain.AggRecord;
import com.tesco.finance.corestockvaluation.domain.SRSInput;

/**
 * @author BARATH
 *
 */
public interface SRSInputRepository extends JpaRepository<SRSInput, String> {

	@Query(value = "select sum(Qty) as qty,sum(retail_price) as  retail_price,store_id,stock_keeping_unit,finance_country, event_payload.paid_date from event_payload,stspayload_info  where event_payload.event_id=stspayload_info.event_id  and event_payload.paid_date >= :start and event_payload.paid_date <:end group by finance_country, event_payload.paid_date,store_id,stock_keeping_unit", nativeQuery = true)
	List<AggRecord> getAggregatedRecord(ZonedDateTime start, ZonedDateTime end);

	@Query(value = "select  count(*) from  event_payload  where    paid_date >:start and paid_date <=:end", nativeQuery = true)
	Long getCountbyTimeStampRange(ZonedDateTime start, ZonedDateTime end);



}
