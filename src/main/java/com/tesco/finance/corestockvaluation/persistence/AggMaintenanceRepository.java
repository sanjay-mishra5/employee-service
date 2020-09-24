/**
 * 
 */
package com.tesco.finance.corestockvaluation.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tesco.finance.corestockvaluation.domain.AggMaintenance;

/**
 * @author BARATH
 *
 */
public interface AggMaintenanceRepository extends JpaRepository<AggMaintenance, String> {

	AggMaintenance findTopByStatusOrderByMaintenanceCycleStartTimeAsc(String st);

}
