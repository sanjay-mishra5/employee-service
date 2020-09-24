/**
 * 
 */
package com.tesco.finance.corestockvaluation.domain;

import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author BARATH
 *
 */
@Entity
@Table(name = "AggMaintenanceTable")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class AggMaintenance {

	@Id
	private String maintenanceCycleId;
	private ZonedDateTime maintenanceCycleStartTime;
	private ZonedDateTime maintenanceCycleEndTime;
	private String status;
	private Long processedcount;
	public String getMaintenanceCycleId() {
		return maintenanceCycleId;
	}
	public void setMaintenanceCycleId(String maintenanceCycleId) {
		this.maintenanceCycleId = maintenanceCycleId;
	}
	public ZonedDateTime getMaintenanceCycleStartTime() {
		return maintenanceCycleStartTime;
	}
	public void setMaintenanceCycleStartTime(ZonedDateTime maintenanceCycleStartTime) {
		this.maintenanceCycleStartTime = maintenanceCycleStartTime;
	}
	public ZonedDateTime getMaintenanceCycleEndTime() {
		return maintenanceCycleEndTime;
	}
	public void setMaintenanceCycleEndTime(ZonedDateTime maintenanceCycleEndTime) {
		this.maintenanceCycleEndTime = maintenanceCycleEndTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getProcessedcount() {
		return processedcount;
	}
	public void setProcessedcount(Long processedcount) {
		this.processedcount = processedcount;
	}
	
}
