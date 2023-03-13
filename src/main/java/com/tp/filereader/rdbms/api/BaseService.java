package com.tp.filereader.rdbms.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tp.filereader.common.ResultContext;
import com.tp.filereader.rdbms.EntityManagerHelper;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseService<E> implements Service<E> {

	/** . */
	protected static final Logger LOG = LoggerFactory.getLogger(BaseService.class);

	public BaseService() {

	}

	@Override
	public boolean beginSession() {
		EntityManagerHelper.getEntityManager();
		return EntityManagerHelper.isCreateNewSession();
	}
	
	@Override
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

	@Override
	public void preCreate(E entity) {
		LOG.debug("preCreate() invoked...");
	}

	@Override
	public void postCreate(E entity) {
		LOG.debug("postCreate() invoked...");
	}

	@Override
	public void preUpdate(E entity) {
		LOG.debug("preUpdate() invoked...");

	}

	@Override
	public void postUpdate(E entity) {
		LOG.debug("postUpdate() invoked...");

	}

	@Override
	public void preDelete(E entity) {
		LOG.debug("preDelete() invoked...");
	}

	@Override
	public void postDelete(E entity) {
		LOG.debug("postDelete() invoked...");
	}

	@Override
	public void start() {

	}

	@Override
	public void stop() {

	}

	public void appendTo(Object entity, String getMethod, String setMethod, GenericJPA storage){
		try {
			Method getAppendId = entity.getClass().getDeclaredMethod(getMethod);
			Integer appendId = (Integer) getAppendId.invoke(entity);
			if (null != appendId && appendId > 0){
				final ResultContext<Object> serviceResult = storage.find(appendId);
				if (serviceResult.succeeded()){
					Method setAppend = entity.getClass().getDeclaredMethod(setMethod, Object.class);
					setAppend.invoke(entity, serviceResult.result());
				}
			}

		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
