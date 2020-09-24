/**
 * 
 */
package com.tesco.finance.corestockvaluation.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tesco.finance.corestockvaluation.domain.SaleConsolidation;
import com.tesco.finance.corestockvaluation.domain.StockInfo;

/**
 * @author BARATH
 *
 */
@Service
public class BulkInsertService {
	private EntityManagerFactory emf;

	@Autowired
	public BulkInsertService(EntityManagerFactory emf) {

		this.emf = emf;
	}

	public List<StockInfo> bulkWithEntityManager(List<StockInfo> items) {
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
		items.forEach(item -> entityManager.persist(item));
		entityManager.getTransaction().commit();
		entityManager.close();

		return items;
	}

	public List<SaleConsolidation> SalesConsolidationbulkWithEntityManager(List<SaleConsolidation> items) {
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
		items.forEach(item -> entityManager.persist(item));
		entityManager.getTransaction().commit();
		entityManager.close();

		return items;
	}
}
