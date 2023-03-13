package com.tp.filereader.rdbms.api;

import java.io.Serializable;
import java.util.List;

import com.tp.filereader.common.ListAccess;
import com.tp.filereader.common.ResultContext;

public interface GenericService<E, ID extends Serializable> {
	/**
	 * Get the number of entities with the specified type and id from the database.
	 *
	 * @return the entity, null if none is found
	 */
	Long count();

	/**
	 * Return an entity which is associated with specified id.
	 *
	 * @return an entity. Otherwise, return NULL.
	 */
	ResultContext<E> find(ID id);

	/**
	 * Get a list entities by given offset and limit
	 *
	 * @return a listAccess
	 */
	ListAccess<E> find();

	/**
	 * Insert a new entity. If the entity already exist, use update(E entity)
	 * instead	
	 *
	 * @return the new entity
	 */
	ResultContext<E> create(E entity);

	/**
	 * Insert a list of new entities in the persistence context. If the entities
	 * already exist, use update(E entity) instead
	 */
	void createAll(List<E> entities);

	/**
	 * Update the entity in the persistence context. If the entity does not already
	 * exist, use create(E entity) instead
	 *
	 * @return the just created entity
	 */
	ResultContext<E> update(E entity);

	/**
	 * Delete the specified entity from the persistence context.
	 * 
	 * @return TRUE/FALSE
	 */
	Boolean delete(E entity);

	/**
	 * Delete the specified key from the persistence context.
	 * 
	 * @return TRUE/FALSE
	 */
	Boolean delete(ID id);

	/**
	 * Remove all the specified entities from the persistence context.
	 */
	void deleteAll(List<E> entities);

}
