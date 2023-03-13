package com.tp.filereader.index;

import io.vertx.core.json.JsonObject;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tp.filereader.common.config.AppConfiguration;
import com.tp.filereader.entity.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractIndex<T,R> {

	protected final String parentDirectory = AppConfiguration.get(AppConfiguration.REPOSITORY_INDEX, "./home/repository/indexes");
	protected final String indexName;

	protected final List<String> stopWords = Arrays.asList(" ",","); //Filters both words
	protected final CharArraySet stopSet = new CharArraySet(stopWords, true);

	protected static final Logger LOG = LoggerFactory.getLogger(AbstractIndex.class);


	public AbstractIndex(String indexName) {
		this.indexName = indexName;
	}

	/**
	 * Init the indexing import
	 */
	public abstract void init();

	/**
	 * Create document from model
	 * 
	 * @param model
	 */
	public abstract void createIndex(T model);

	/**
	 * Update document from model
	 * 
	 * @param model
	 */
	public abstract void updateIndex(T model);

	/**
	 * Create document from model list
	 * 
	 * @param models
	 */
	public abstract void createIndexes(List<T> models);

	/**
	 * Delete document
	 * @param term
	 */
	public abstract void deleteDocument(Term term);

	/**
	 * Get indexer
	 *
	 * @return
	 */
	public abstract SearchIndex getIndexer();

	/**
	 * Rebuild all document indexes
	 */
	public abstract JsonObject rebuildIndexes();
	
	/**
	 * Search keyword with offset and limit
	 * 
	 * @param keyword
	 * @param country
	 * @param offset
	 * @param limit
	 */
	public abstract List<R> search(String keyword, String country, int offset, int limit);
	
	/**
	 * Search hashtag with offset and limit
	 * 
	 * @param hashtag
	 * @param country
	 * @param offset
	 * @param limit
	 */
	public abstract List<R> searchByHashtag(String hashtag, String country, int offset, int limit);

	/**
	 *
	 * @param result
	 * @return
	 */
	public List<Data> buildData(List<Document> result) {
		if (result == null) return null;
		List<Data> resultList = new ArrayList<>(result.size());
		Data data;

		for (Document doc : result) {
			data = new Data();
			data.setId(Integer.parseInt(doc.get(DocumentConstant.ID)));
			data.setName(doc.get(DocumentConstant.NAME));
			data.setCreatedDate(Long.valueOf(doc.get(DocumentConstant.CREATED_DATE)));
			resultList.add(data);
		}
		return resultList;
	}
	
	/**
	 * Build result list
	 * @param result
	 * @return
	 */
	public List<SearchResult> buildResult(List<Document> result) {
		List<SearchResult> resultList = new ArrayList<SearchResult>(result.size());
		SearchResult searchResult;
		for (Document doc : result) {
			searchResult = new SearchResult(doc.get("id"), doc.get("name"), doc.get("keyword"), doc.get("country"), doc.get("categories"), doc.get("url"),
					doc.get("owner"), doc.get("fileSize"), doc.get("fileType"), doc.get("hashtags"), doc.get("subDescription"), doc.get("description"),
					doc.get("pageNumber"), doc.get("downcount"), doc.get("viewcount"),doc.get("isPublic")
					);
			resultList.add(searchResult);
		}
		return resultList;
	}
	
	

}