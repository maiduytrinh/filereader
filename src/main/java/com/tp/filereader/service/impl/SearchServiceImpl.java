package com.tp.filereader.service.impl;

import java.util.List;

import com.tp.filereader.entity.Documents;
import com.tp.filereader.index.AbstractIndex;
import com.tp.filereader.index.KeywordIndex;
import com.tp.filereader.index.SearchResult;
import com.tp.filereader.service.SearchService;

import io.vertx.core.json.JsonObject;

public class SearchServiceImpl implements SearchService{
	private final AbstractIndex<Documents, SearchResult> keywordIndex = new KeywordIndex();
	
	@Override
	public List<SearchResult> searchKeyword(String country, String keyword, Integer offset, Integer limit) {
		return keywordIndex.search(keyword, country, offset, limit);
	}
	
	@Override
	public List<SearchResult> searchByHashtag(String country, String hashtag, Integer offset, Integer limit) {
		return keywordIndex.searchByHashtag(hashtag, country, offset, limit);
	}

	@Override
	public JsonObject rebuildKeywordIndex() {
		return keywordIndex.rebuildIndexes();
		
	}
}
