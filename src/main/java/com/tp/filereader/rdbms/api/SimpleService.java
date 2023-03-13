package com.tp.filereader.rdbms.api;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.tp.filereader.rdbms.EntityManagerHelper;

public class SimpleService {
	
	public boolean beginSession() {
		EntityManagerHelper.getEntityManager();
		return EntityManagerHelper.isCreateNewSession();
	}
	
	
	public void closeSession(boolean begunSession) {
		EntityManagerHelper.closeSession(begunSession);
	}
	
	/**
	 * Begin transaction
	 * 
	 * @return TRUE/FALSE
	 */
	public boolean beginTx() {
		boolean begunTx = false;
		EntityManager em = EntityManagerHelper.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		if (!tx.isActive() && em.isOpen()) {
			tx.begin();
			begunTx = true;
		}

		return begunTx;
	}
	
	/**
	 * Close the Transaction
	 * 
	 * @param begunTx TRUE/FALSE
	 */
	public void endTx(boolean begunTx) {
		EntityManagerHelper.closeEntityManager(begunTx);
	}

}
