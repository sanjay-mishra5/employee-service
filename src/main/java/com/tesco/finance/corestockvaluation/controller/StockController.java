/**
 * 
 */
package com.tesco.finance.corestockvaluation.controller;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tesco.finance.corestockvaluation.domain.AggMaintenance;
import com.tesco.finance.corestockvaluation.domain.SRSInput;
import com.tesco.finance.corestockvaluation.domain.StockInfo;
import com.tesco.finance.corestockvaluation.persistence.AggMaintenanceRepository;
import com.tesco.finance.corestockvaluation.persistence.SRSInputRepository;
import com.tesco.finance.corestockvaluation.service.BulkInsertService;
import com.tesco.finance.corestockvaluation.service.CSVCreationService;

/**
 * @author BARATH
 *
 */
@RestController
public class StockController {

	@Autowired
	private BulkInsertService bulkInsertService;
	@Autowired
	private CSVCreationService csvCreationService;
	@Autowired
	private AggMaintenanceRepository aggMaintenanceRepository;
	@Autowired
	private SRSInputRepository sRSInputRepository;

	@GetMapping("/greeting")
	public String greeting(String name) {
		System.out.println("Inside");
		return "Hello";
	}

	@GetMapping(value = "/serviceimport")
	public ResponseEntity<List<StockInfo>> importWithBulkService() {
		return new ResponseEntity<List<StockInfo>>(bulkInsertService.bulkWithEntityManager(createItems()),
				HttpStatus.CREATED);
	}

	private List<StockInfo> createItems() {
		String date = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
		List<StockInfo> items = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			StockInfo item = new StockInfo();
			item.setClarifyingNote("mnvbdjgn");
			item.setLocation("Board");
			item.setTransactionDate(date);
			item.setHeadline("nbhdsbjbrj");
			item.setItem("Goods");
			items.add(item);
		}
		return items;
	}

	@GetMapping("/aggregate/export")
	public void exportToCSV(HttpServletResponse response) throws Exception {

		// csvCreationService.createCSVFile(response);

	}

	@GetMapping("/aggregate/scheduled")
	public void scheduledRecord(HttpServletResponse response) throws Exception {

		AggMaintenance ag = aggMaintenanceRepository.findTopByStatusOrderByMaintenanceCycleStartTimeAsc("START");
		if (ag != null) {
			try {

				ZonedDateTime startRange = ag.getMaintenanceCycleStartTime();
				ZonedDateTime endRange = ag.getMaintenanceCycleEndTime();

				csvCreationService.aggregateScheduling(ag, startRange, endRange);

			} catch (Exception e) {

			}

		}

	}
	
	@PostMapping("/upload")
	public String FileUpload() {

		return csvCreationService.FileUpload(sRSInputRepository.findAll(),"test");
	}


}
