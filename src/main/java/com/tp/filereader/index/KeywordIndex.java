package com.tp.filereader.index;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanQuery.Builder;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;

import com.google.common.base.Stopwatch;
import com.tp.filereader.Utils;
import com.tp.filereader.common.config.AppConfiguration;
import com.tp.filereader.entity.Documents;
import com.tp.filereader.index.analyzer.AnalyzerBuilder;
import com.tp.filereader.rdbms.PageImpl;
import com.tp.filereader.rdbms.api.Pageable;
import com.tp.filereader.storage.DataStorage;
import com.tp.filereader.storage.impl.DataJPAImpl;

import io.vertx.core.json.JsonObject;

public class KeywordIndex extends AbstractIndex<Documents, SearchResult> {
	private static final Logger LOG = com.tp.filereader.logger.TPLogger.getLogger(KeywordIndex.class);

	private final static String INDEX_NAME = "/keyword";

	private SearchIndex indexer = null;
	private Directory directory = null;

	private Boolean isIndexing = false;

	public KeywordIndex() {
		super(INDEX_NAME);
		try {
			directory = FSDirectory.open(Paths.get(parentDirectory + INDEX_NAME));
			indexer = new SearchIndex(directory, new StandardAnalyzer());
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

	}
	
	public List<SearchResult> search(String keyword, String country, int offset, int limit) {
		String[] sensitiveKey = "sex,fuck,bitch, ass, dick, pussy, slut, damn, sexy, goddamn, shit, bullshit, cazzo, scheiße".split(",");
		for(String s : sensitiveKey) {
			if(keyword.contains(s)) {
				keyword = keyword.replaceAll("(?i)" + s, "");
			}
		}
		String lang = Utils.langDetect(keyword);
		LOG.info("Detect language of keyword {} is {}, offset {}, limit {}", keyword, lang, offset, limit);
		List<SearchResult> extractResult = null;
		if (Utils.specialLanguages.contains(lang) || Utils.specialCountries.contains(country.toLowerCase())) {
			extractResult = searchExtractWithSpecialLang(keyword, lang, country, offset, limit);
		} else {
			extractResult = searchExtract(keyword, country, offset, limit);
		}
		if (offset == 0 && extractResult.size() < limit) {
			LOG.info("search extract {}, continue with FuzzyQuery", extractResult.size());
			Builder builder = buildQueryFuzzySearch(keyword, country);
			final List<Document> result = indexer.searchIndex(builder.build(), offset, limit);
			List<SearchResult> searchKeywords = buildResult(result);
			for (SearchResult row : searchKeywords) {
				if (!extractResult.contains(row)) {
					extractResult.add(row);
				}
			}
		}
		return extractResult;
	}
	
	private Builder buildQueryFuzzySearch(String keyword, String country) {
		final String[] queryData = keyword.toLowerCase().split(" ");
		final Builder builder = new BooleanQuery.Builder();
		Query query;
		for (int i = 0; i < queryData.length; i++) {
			query = new FuzzyQuery(new Term("keyword", queryData[i]));
			builder.add(query, BooleanClause.Occur.MUST);
		}

		Query queryCountry = new TermQuery(new Term("country", country));
		builder.add(queryCountry, BooleanClause.Occur.MUST);

		return builder;
	}
	
	private List<SearchResult> searchExtractWithSpecialLang(String keyword, String lang, String country, int offset, int limit) {
		try {
			QueryParser queryParser = new QueryParser("keyword", AnalyzerBuilder.getInstanceByLang(lang));
			Query q = queryParser.parse(keyword);
			final Builder builder = new BooleanQuery.Builder();
			builder.add(q, BooleanClause.Occur.MUST);
			Query queryCountry = new TermQuery(new Term("country", country));
			builder.add(queryCountry, BooleanClause.Occur.MUST);
			final List<Document> result = indexer.searchIndex(builder.build(), offset, limit);
			final List<SearchResult> searchKeywords = buildResult(result);
			return searchKeywords;
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
		}
		return new ArrayList<SearchResult>();
	}

	private List<SearchResult> searchExtract(String keyword, String country, int offset, int limit) {
		final String[] queryData = keyword.toLowerCase().split(" ");
		final Builder builder = new BooleanQuery.Builder();
		Query query;
		for (int i = 0; i < queryData.length; i++) {
			query = new TermQuery(new Term("keyword", queryData[i]));
			builder.add(query, BooleanClause.Occur.MUST);
		}
		Query queryCountry = new TermQuery(new Term("country", country));
		builder.add(queryCountry, BooleanClause.Occur.MUST);
		final List<Document> result = indexer.searchIndex(builder.build(), offset, limit);
		final List<SearchResult> searchKeywords = buildResult(result);
		return searchKeywords;
	}

	public List<SearchResult> searchByHashtag(String hashtag, String country, int offset, int limit) {
		final String[] queryData = hashtag.toLowerCase().split(" ");
		final Builder builder = new BooleanQuery.Builder();
		Query query;
		for (int i = 0; i < queryData.length; i++) {
			query = new TermQuery(new Term("hashtags", queryData[i]));
			builder.add(query, BooleanClause.Occur.MUST);
		}
		query = new TermQuery(new Term("country", country));
		builder.add(query, BooleanClause.Occur.MUST);
		final List<Document> result = indexer.searchIndex(builder.build(), offset, limit);
		List<SearchResult> searchHT = new ArrayList<>();
		searchHT = buildResult(result);
		return searchHT;
	}
	
	@Override
	public void init() {
		try {
            boolean isFuzzyActivate = AppConfiguration.getBoolean(AppConfiguration.SERVICE_FUZZY_SEARCH_ACTIVATE, "true");
            if (!isFuzzyActivate) {
                return;
            }
            if (DirectoryReader.indexExists(directory) || isIndexing) {
                LOG.info("DataIndex::directory indexing is existing.");
                return;
            }
            isIndexing = true;
//            initAllIndexing();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            if (isIndexing) {
                isIndexing = false;
            }
        }
	}
	
	
	private JsonObject initAllIndexing() {
		final Stopwatch timer = Stopwatch.createStarted();
		Long inserted = 0L;
		DataStorage storage = new DataJPAImpl();
		for (String country : Utils.supportedCountries) {
			indexer = new SearchIndex(directory, AnalyzerBuilder.getInstanceByCountry(country.toLowerCase()));
			LOG.info("Lucence KeywordIndex with country {} =============================", country);
			Pageable<Documents> pageable = new PageImpl<>(0, 1000);
			Set<Integer> urls = new HashSet<Integer>();
			Pageable<Documents> nextPageList;
			int records = 0;
			do {
				nextPageList = pageable.nextPageable();
				pageable = storage.getPageableByCountry(nextPageList, country);
				records += pageable.getList().size();
				inserted += pageable.getList().size();
				List<Documents> filterData = processDuplicatedURLs(urls, pageable.getList());
				createPageIndex(filterData, country);
			} while (pageable.hasNext());
			
			LOG.info("Index for country {} with {} records", country, records);
		}
		long minutes = timer.elapsed(TimeUnit.MINUTES);

		LOG.info("DataIndex::Indexing took(minute: " + timer.stop());

		JsonObject result = new JsonObject();
		result.put("inserted", inserted);
		result.put("indexing took minute(s)", minutes);
		return result;

	}
	
	private void createPageIndex(List<? extends Documents> list, String country) {
		List<Document> docs = new ArrayList<>();
		if (list == null) return;
		for (Documents data : list) {
				docs.add(create(data, country));
		}

		if (docs.size() > 0) {
			LOG.info("index country {} with size {}", country, docs.size());
			indexer.indexDocuments(docs);
		}
	}
	
	private void createPageIndex(List<Documents> list) {
        List<Document> docs = new ArrayList<>();

        if (list == null) return;

        for (Documents documents : list) {
            Document doc = create(documents);
            docs.add(doc);
        }

        if (docs.size() > 0) {
            indexer.indexDocuments(docs);
        }
    }
	
	private static Document create(Documents model, String country) {
        final Document document = new Document();
        String hashtag = model.getHashtags().substring(0, model.getHashtags().length() - 1).replace(",", " ");
        document.add(new StringField("id", model.getId().toString(), Field.Store.YES));
        document.add(new StringField("name", model.getName() != null ? model.getName() : "none", Field.Store.YES));
        String keyword = Utils.getQueryInput(model.getKeyword(), false);
        document.add(new TextField("keyword", keyword != null ? keyword : "none" , Field.Store.YES));        
        document.add(new StringField("country", StringUtils.isEmpty(country) ? model.getCountry() : country, Field.Store.YES));        
        document.add(new StringField("categories", model.getCategories()!= null ? model.getCategories() : "none", Field.Store.YES));        
        document.add(new StringField("url", model.getUrl() != null ? model.getUrl() : "none" , Field.Store.YES));        
        document.add(new StringField("owner", model.getOwner() != null ? model.getOwner() : "none", Field.Store.YES));        
        document.add(new StringField("fileSize", model.getFileSize() != null ? model.getFileSize() : "none", Field.Store.YES));        
        document.add(new StringField("fileType", model.getFileType() != null ? model.getFileType() : "none", Field.Store.YES));       
        document.add(new TextField("hashtags", hashtag != null ? hashtag : "none", Field.Store.YES));       
        document.add(new StringField("subDescription", model.getSubDescription()!= null ? model.getSubDescription() : "none", Field.Store.YES));        
        document.add(new StringField("description", model.getDescription() != null ? model.getDescription() : "none", Field.Store.YES));        
        document.add(new StringField("downcount", model.getDowncount().toString(), Field.Store.YES));    
        document.add(new StringField("viewcount", model.getViewcount().toString(), Field.Store.YES)); 
        document.add(new StringField("isPublic", model.getIsPublic().toString(), Field.Store.YES)); 
        return document;
    }
	
	private List<Documents> processDuplicatedURLs(Set<Integer> urlSet, List<? extends Documents> list) {
		List<Documents> result = new ArrayList<Documents>();
		for (Documents docs : list) {
			if (!urlSet.contains(docs.getUrl().hashCode())) {
				urlSet.add(docs.getUrl().hashCode());
				result.add(docs);
			}
		}
		return result;
	}

	private Document create(Documents model) {
		return create(model, "");
	}

	@Override
	public void createIndex(Documents model) {
		final Document doc = create(model);
		indexer.indexDocument(doc);
		
	}

	@Override
	public void updateIndex(Documents model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createIndexes(List<Documents> models) {
		createPageIndex(models);
	}

	@Override
	public void deleteDocument(Term term) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SearchIndex getIndexer() {
		// TODO Auto-generated method stub
		return indexer;
	}

	@Override
	public JsonObject rebuildIndexes() {
		// TODO Auto-generated method stub
		Long deleted = indexer.deleteAll();
		JsonObject result = initAllIndexing();
		result.put("deleted", deleted);
		
		return result;
	}

}
