package com.tp.filereader.rdbms.api;

public interface Service<E> {

	void preCreate(E entity);

	void postCreate(E entity);

	void preUpdate(E entity);

	void postUpdate(E entity);

	void preDelete(E entity);

	void postDelete(E entity);

	void start();

	void stop();
	
	/**
	 * Begin the transaction
	 * @return
	 */
	public boolean beginTx();
	
	/**
	 * Close the Transaction
	 * 
	 * @param begunTx TRUE/FALSE
	 */
	public void endTx(boolean begunTx);
	
	/**
	 * Begin the session
	 * @return
	 */
	public boolean beginSession();
	
	/**
	 * Close the Session
	 * 
	 * @param begunSession TRUE/FALSE
	 */
	public void closeSession(boolean begunSession);

}
