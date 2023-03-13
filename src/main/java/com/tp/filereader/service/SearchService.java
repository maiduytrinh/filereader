package com.tp.filereader.service;

import java.util.List;

import com.tp.filereader.index.SearchResult;

import io.vertx.core.json.JsonObject;

public interface SearchService {

	/**
	 * 
	 * Fulltext search for keyword
	 * 
	 * @param country
	 * @param keyword
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<SearchResult> searchKeyword(String country, String keyword, Integer offset, Integer limit);
	
	/**
	 * Fulltext search for hashtag
	 * 
	 * @param country
	 * @param hashtag
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<SearchResult> searchByHashtag(String country, String hashtag, Integer offset, Integer limit);
	
	/**
	 * Rebuild all keyword index
	 */
	JsonObject rebuildKeywordIndex();

}
