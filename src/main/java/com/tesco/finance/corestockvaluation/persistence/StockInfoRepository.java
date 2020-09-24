/**
 * 
 */
package com.tesco.finance.corestockvaluation.persistence;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tesco.finance.corestockvaluation.domain.StockInfo;

/**
 * @author BARATH
 *
 */
@Repository
public class StockInfoRepository extends SimpleJpaRepository<StockInfo, Long> {

	private EntityManager entityManager;

	public StockInfoRepository(EntityManager entityManager) {
		super(StockInfo.class, entityManager);
		this.entityManager = entityManager;
	}
	
	

	@Transactional(transactionManager = "chainedKafkaTransactionManager")
	public List<StockInfo> save(List<StockInfo> items) {
		items.forEach(item -> entityManager.persist(item));
		return items;
		
	}

}
