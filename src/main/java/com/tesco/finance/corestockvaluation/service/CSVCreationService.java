/**
 * 
 */
package com.tesco.finance.corestockvaluation.service;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.uuid.Generators;
import com.tesco.finance.corestockvaluation.domain.AggMaintenance;
import com.tesco.finance.corestockvaluation.domain.SRSInput;
import com.tesco.finance.corestockvaluation.persistence.AggMaintenanceRepository;
import com.tesco.finance.corestockvaluation.persistence.SRSInputRepository;
import com.tesco.finance.corestockvaluation.persistence.SRSInputRepositoryKlass;
import com.tesco.finance.corestockvaluation.util.SRSUtil;

@Service
public class CSVCreationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CSVCreationService.class);

	@Autowired
	private SRSInputRepository sRSInputRepository;

	@Autowired
	private AggMaintenanceRepository aggMaintenanceRepository;

	@Autowired
	private SRSInputRepositoryKlass srsInputRepositoryKlass;

	@Autowired
	private SRSUtil srsUtil;

	@Value("${ftp.server.name}")
	private String FTP_ADDRESS;

	@Value("${ftp.user.name}")
	private String LOGIN;

	@Value("${ftp.user.password}")
	private String PSW;

	@Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED)
	public void aggregateScheduling(AggMaintenance ag, ZonedDateTime startTime, ZonedDateTime endTime)
			throws Exception {

		LOGGER.info("CSVCreationService  Beginning ");
		AggMaintenance anotherRecord = new AggMaintenance();

		try {

			insertIntoAggregatetable(ag, startTime, endTime);
			ag.setStatus("SUCCESS");
			ag.setProcessedcount(srsInputRepositoryKlass.getCountbyTimeStampRange(startTime, endTime));
			// srsInputRepositoryKlass.getCountbyTimeStampRange(startTime, endTime);
			anotherRecord.setMaintenanceCycleStartTime(endTime);
			anotherRecord.setMaintenanceCycleEndTime(endTime.plusMinutes(15));
			anotherRecord.setStatus("START");
			anotherRecord.setProcessedcount(0L);
			anotherRecord.setMaintenanceCycleId(Generators.timeBasedGenerator().generate().toString());
			aggMaintenanceRepository.save(anotherRecord);
			aggMaintenanceRepository.save(ag);
		} catch (Exception e) {
			e.printStackTrace();
			ag.setStatus("FAILED");
			aggMaintenanceRepository.save(ag);
		}

		LOGGER.info("CSVCreationService  Leaving ");
	}

	@Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRES_NEW)
	public void insertIntoAggregatetable(AggMaintenance ag, ZonedDateTime startTime, ZonedDateTime endTime)
			throws IOException {

		List<SRSInput> aggregatedRecordList = new ArrayList<SRSInput>();
		srsInputRepositoryKlass.getAggregatedRecord(startTime, endTime).forEach(e -> {

			SRSInput aggregatedRecord = new SRSInput();
			aggregatedRecord.setAggId(Generators.timeBasedGenerator().generate().toString());
			aggregatedRecord.setBondedStockInd('N');
			aggregatedRecord.setTescoOriginCountry(e[4].toString());
			aggregatedRecord.setQuantity(Long.parseLong(e[0].toString()));
			aggregatedRecord.setRetailPrice(Long.parseLong(e[1].toString()));
			aggregatedRecord.setItemNumber(e[3].toString());
			aggregatedRecord.setLocation(e[2].toString());
			aggregatedRecord.setLocationType('S');
			aggregatedRecord.setTranDate(srsUtil.StringtodateConverter(e[5].toString()));
			// aggregatedRecord.setPaidDate();
			aggregatedRecord.setBatchId(ag.getMaintenanceCycleId());
			aggregatedRecord.setTranType(2);
			aggregatedRecordList.add(aggregatedRecord);

		});

		List<SRSInput> insertedList = sRSInputRepository.saveAll(aggregatedRecordList);

		FileUpload(insertedList, srsUtil.Filename(startTime, endTime));
	}

	public InputStream WriteCSVFile(List<SRSInput> insertedList) {
		final ByteArrayOutputStream outB = new ByteArrayOutputStream();
		Writer out = new BufferedWriter(new OutputStreamWriter(outB, Charset.defaultCharset()));
		try (final CSVPrinter csvFilePrinter = new CSVPrinter(out,
				CSVFormat.DEFAULT.withHeader("location", "location_type", "item_number", "tran_date",
						"transaction_type", "quantity", "retail_price", "bonded_stock_ind", "ref_no",
						"tesco_origin_country", "batch_id"))) {
			for (SRSInput aggregatedRecord : insertedList) {
				csvFilePrinter.printRecord(aggregatedRecord.getLocation(), aggregatedRecord.getLocationType(),
						aggregatedRecord.getItemNumber(), aggregatedRecord.getTranDate(),
						aggregatedRecord.getTranType(), aggregatedRecord.getQuantity(),
						aggregatedRecord.getRetailPrice(), aggregatedRecord.getBondedStockInd(),
						aggregatedRecord.getRefNo(), aggregatedRecord.getTescoOriginCountry(),
						aggregatedRecord.getBatchId());
			}
			csvFilePrinter.flush();
			final byte[] bytes = outB.toByteArray();

			csvFilePrinter.close();
			return new ByteArrayInputStream(bytes);
		} catch (IOException e) {

		}
		return null;
	}

	public String FileUpload(List<SRSInput> insertedList, String fname) {
		FTPClient con = null;
		String message = null;

		try {
			con = new FTPClient();
			FTPClientConfig conf = new FTPClientConfig();
			conf.setServerTimeZoneId("Asia/Kolkata");
			con.configure(conf);
			con.connect(FTP_ADDRESS);

			if (con.login(LOGIN, PSW)) {
				con.enterLocalPassiveMode();

				String filename = fname + ".csv";

				File file = new File(filename);
				file.createNewFile();
				con.setFileType(FTP.BINARY_FILE_TYPE);

				boolean result = con.storeFile(file.getName(), WriteCSVFile(insertedList));
				if (result) {
					file.delete();
				}
				con.logout();
				con.disconnect();

				System.out.println("You successfully uploaded");

			}
		} catch (Exception e) {
			System.out.println("Error in uploaded");
			message = "FAILED";
			e.printStackTrace();
		}

		return message;
	}

}
