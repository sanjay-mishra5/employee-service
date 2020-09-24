/**
 * 
 */
package com.tesco.finance.corestockvaluation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tesco.finance.corestockvaluation.domain.StockInfo;
import com.tesco.finance.corestockvaluation.persistence.StockInfoRepository;

/**
 * @author BARATH
 *
 */
@Service
public class StockInfoInsertService {

	private StockInfoRepository stockInfoRepository;

	@Autowired
	public StockInfoInsertService(StockInfoRepository stockInfoRepository) {

		this.stockInfoRepository = stockInfoRepository;
	}
	
	public List<StockInfo> getItems() {
		return stockInfoRepository.findAll();
	}

	public List<StockInfo> bulkImport(List<StockInfo> items) {
		return stockInfoRepository.save(items);
	}

}



