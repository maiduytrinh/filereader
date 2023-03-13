package com.tp.filereader.storage;

import com.tp.filereader.entity.Documents;
import com.tp.filereader.rdbms.api.GenericJPA;
import com.tp.filereader.rdbms.api.Pageable;

public interface DataStorage extends GenericJPA<Documents, Integer> {
	/**
  	 * Get pageable by country
  	 * 
  	 * @param pageable
  	 * @param country
  	 * @return
  	 */
  	Pageable<Documents> getPageableByCountry(Pageable<Documents> pageable, String country);
}
