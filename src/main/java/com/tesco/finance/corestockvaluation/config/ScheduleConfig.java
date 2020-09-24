/**
 * 
 */
package com.tesco.finance.corestockvaluation.config;

import java.time.ZonedDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import com.tesco.finance.corestockvaluation.domain.AggMaintenance;
import com.tesco.finance.corestockvaluation.persistence.AggMaintenanceRepository;
import com.tesco.finance.corestockvaluation.service.CSVCreationService;

/**
 * @author BARATH
 *
 */
@Configuration
public class ScheduleConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleConfig.class);
	@Autowired
	private AggMaintenanceRepository aggMaintenanceRepository;
	@Autowired
	private CSVCreationService csvCreationService;

	@Scheduled(fixedDelay = 60000)
	public void scheduleAggregation() throws Exception {

		try {

			AggMaintenance ag = aggMaintenanceRepository.findTopByStatusOrderByMaintenanceCycleStartTimeAsc("START");
			if (ag != null) {
				ZonedDateTime startRange = ag.getMaintenanceCycleStartTime();
				ZonedDateTime endRange = ag.getMaintenanceCycleEndTime();

				LOGGER.info("===================Job has been Scheduled with  TimeStamp FROM "
						+ ag.getMaintenanceCycleStartTime() + " TO " + ag.getMaintenanceCycleEndTime());
				csvCreationService.aggregateScheduling(ag, startRange, endRange);

			} else {
				LOGGER.info("===================No Job has been Scheduled==========");
			}

		} finally {

		}

	}

}
