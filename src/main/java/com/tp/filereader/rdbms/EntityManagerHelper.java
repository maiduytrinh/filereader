package com.tp.filereader.rdbms;

import org.hibernate.internal.SessionImpl;

import javax.persistence.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class EntityManagerHelper {

	/** */
	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("tp-pu");
	/** */
    private static ThreadLocal<EntityManager> instance = new ThreadLocal<>();
    
    private static AtomicBoolean isCreateNewEntityManager = new AtomicBoolean(false);

	private EntityManagerHelper() {
	}

	/**
	 * Returns the EntityManager instance from ThreadLocal
	 * 
	 * @return
	 */
	public static EntityManager getEntityManager() {
		if (instance.get() == null) {
			return createEntityManager();
		} else {
			isCreateNewEntityManager.set(false);
			return instance.get();
		}
	}
	
	/**
	 * Gets the status to create new session or not
	 * @return
	 */
	public static Boolean isCreateNewSession( ) {
		return isCreateNewEntityManager.get();
	}

	/**
	 * Return a completely new instance of EntityManager. The EntityManager instance
	 * is put in the threadLocal for future using.
	 * 
	 * @return return a completely new instance of EntityManager
	 */
	public static EntityManager createEntityManager() {
		EntityManager em = entityManagerFactory.createEntityManager();
		em.setFlushMode(FlushModeType.COMMIT);
		instance.set(em);
		isCreateNewEntityManager.set(true);
		return instance.get();
	}
	
	/**
	 * Initialize when application start avoid delay time in first request.
	 * 
	 */
	public static void init() {
		EntityManager em = null;
		try {
			em = entityManagerFactory.createEntityManager();
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
				em = null;
			}
		}
		
	}
	
	/**
	 * Reset the session
	 */
	public static void reset() {
	    instance.set(null);
	    isCreateNewEntityManager.set(false);
	}

	/**
	 * Closed the existing instance of EntityManager To be sure the transaction MUST
	 * be committed before closing Handles the rollBack if there is any exception
	 */
	public static void closeEntityManager() {
		EntityManager em = getEntityManager();
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			if (tx.isActive()) {
				tx.commit();
			}
		} catch (Exception ex) {
			try {
				if (tx != null && tx.isActive()) {
					tx.rollback();
				}
			} catch (Exception rbEx) {
				throw new RuntimeException("Could not roll back transaction", rbEx);
			}
			throw ex;
		} finally {
			em.close();
			em = null;
			instance.set(null);
		}
	}
	
	public static void closeSession(boolean begunSession) {
		EntityManager em = getEntityManager();
		try {
			if (begunSession && em.isOpen()) {
				em.close();
				em = null;
				instance.set(null);
			}
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public static void closeEntityManager(boolean begunTx) {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			if ((begunTx) && (tx.isActive())) {
				tx.commit();
			}
		} catch (Exception ex) {
			try {
				if (tx != null && tx.isActive()) {
					tx.rollback();
				}
			} catch (Exception rbEx) {
				throw new RuntimeException("Could not roll back transaction", rbEx);
			}
			throw ex;
		} finally {
			if (begunTx) {
				em.close();
				em = null;
				instance.set(null);
			}
			
		}

	}

	/**
	 * Cleanup the database;
	 * 
	 * @throws Exception
	 */
	public static void clearDatabase() throws Exception {
		EntityManager em = getEntityManager();
		Connection c = ((SessionImpl) em.getDelegate()).connection();
		Statement s = c.createStatement();
		s.execute("SET DATABASE REFERENTIAL INTEGRITY FALSE");
		Set<String> tables = new HashSet<String>();
		ResultSet rs = s.executeQuery("select table_name " + "from INFORMATION_SCHEMA.system_tables "
				+ "where table_type='TABLE' and table_schem='PUBLIC'");
		while (rs.next()) {
			if (!rs.getString(1).startsWith("DUAL_")) {
				tables.add(rs.getString(1));
			}
		}
		rs.close();
		for (String table : tables) {
			s.executeUpdate("DELETE FROM " + table);
		}
		s.execute("SET DATABASE REFERENTIAL INTEGRITY TRUE");
		s.close();
	}

}
