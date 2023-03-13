package com.tp.filereader.index;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class SearchIndex {

	private Directory directoryIndex;
	private Analyzer analyzer;

	public SearchIndex(Directory directoryIndex, Analyzer analyzer) {
		super();
		this.directoryIndex = directoryIndex;
		this.analyzer = analyzer;
	}

	public void indexDocument(Document document) {
		try {
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
			IndexWriter writter = new IndexWriter(directoryIndex, indexWriterConfig);
			writter.addDocument(document);
			writter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void indexDocuments(List<Document> documents) {
		try {
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
			IndexWriter writter = new IndexWriter(directoryIndex, indexWriterConfig);
			writter.addDocuments(documents);
			writter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateDocument(Term term, Document document) {
		try {
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
			IndexWriter writter = new IndexWriter(directoryIndex, indexWriterConfig);
			writter.updateDocument(term, document);
			writter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Long deleteAll() {
		try {
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
			IndexWriter writter = new IndexWriter(directoryIndex, indexWriterConfig);
			final Long deletedCount = writter.deleteAll();
			writter.close();
			return deletedCount;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0L;
	}

	public void deleteDocument(Term term) {
		try {
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
			IndexWriter writter = new IndexWriter(directoryIndex, indexWriterConfig);
			writter.deleteDocuments(term);
			writter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Document searchIndex(Query query) {
		try {
			IndexReader indexReader = DirectoryReader.open(directoryIndex);
			IndexSearcher searcher = new IndexSearcher(indexReader);
			TopDocs topDocs = searcher.search(query, 1);
			Document document = null;

			if (topDocs.totalHits.value != 0) {
				document = searcher.doc(topDocs.scoreDocs[0].doc);
			}

			return document;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public List<Document> searchIndex(Query query, int limit) {
		if (limit <= 0) {
			return new ArrayList<>();
		}
		try {
			IndexReader indexReader = DirectoryReader.open(directoryIndex);
			IndexSearcher searcher = new IndexSearcher(indexReader);
			TopDocs topDocs = searcher.search(query, limit);
			List<Document> documents = new ArrayList<>();
			for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
				documents.add(searcher.doc(scoreDoc.doc));
			}

			return documents;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public List<Document> searchIndex(Query query, int offset, int limit) {
		if (limit <= 0) {
			return new ArrayList<>();
		}
		try {
			IndexReader indexReader = DirectoryReader.open(directoryIndex);
			IndexSearcher searcher = new IndexSearcher(indexReader);
			//Get the last element of the previous page
			ScoreDoc lastSd = getLastScoreDoc(query, searcher, offset, limit);
			if (offset > 0 && lastSd == null) {
				return new ArrayList<>();
			}
			//Search for elements on the next page through the last element
			TopDocs topDocs = searcher.searchAfter(lastSd, query, limit);
			List<Document> documents = new ArrayList<>();
			for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
				documents.add(searcher.doc(scoreDoc.doc));
			}
			return documents;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public List<Document> searchIndexDistinct(Query query, int offset, int limit, String field) {
		if (limit <= 0) {
			return new ArrayList<>();
		}
		try {
			IndexReader indexReader = DirectoryReader.open(directoryIndex);
			IndexSearcher searcher = new IndexSearcher(indexReader);
			//Get the last element of the previous page
			ScoreDoc lastSd = getLastScoreDoc(query, searcher, offset, limit);
			if (offset > 0 && lastSd == null) {
				return new ArrayList<>();
			}
			//Search for elements on the next page through the last element
			TopDocs topDocs = searcher.searchAfter(lastSd, query, limit);
			List<Document> documents = new ArrayList<>();
			List<String> values = new ArrayList<>();
			for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
				Document doc = searcher.doc(scoreDoc.doc);
				if (values.contains(doc.get(field))) continue;
				documents.add(searcher.doc(scoreDoc.doc));
				values.add(doc.get(field));
			}
			return documents;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}


	/**
	 * Get the previous page
	 * 
	 * @param offset
	 * @param limit
	 * @param query
	 * @param indexer
	 * @return
	 * @throws IOException
	 */
	protected ScoreDoc getLastScoreDoc(Query query, IndexSearcher indexer, int offset,int limit) throws IOException {
		if(offset == 0) return null;//If it is the first page, return empty
		TopDocs tds = indexer.search(query, offset);
		if (tds.scoreDocs.length < offset) return null;
		return tds.scoreDocs[offset - 1];
	}

	protected ScoreDoc getLastScoreDoc(Query query, IndexSearcher indexer, int offset,int limit, Sort sort) throws IOException {
		if(offset == 0) return null;//If it is the first page, return empty
		TopDocs tds = indexer.search(query, offset, sort);
		if (tds.scoreDocs.length < offset) return null;
		return tds.scoreDocs[offset - 1];
	}


	public List<Document> searchIndex(Query query, Sort sort, int limit) {
		if (limit <= 0) {
			return new ArrayList<>();
		}
		try {
			IndexReader indexReader = DirectoryReader.open(directoryIndex);
			IndexSearcher searcher = new IndexSearcher(indexReader);
			TopDocs topDocs = searcher.search(query, limit, sort);
			List<Document> documents = new ArrayList<>();
			for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
				documents.add(searcher.doc(scoreDoc.doc));
			}

			return documents;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public List<Document> searchIndex(Query query, Sort sort, int offset, int limit) {
		if (limit <= 0) {
			return new ArrayList<>();
		}
		try {
			IndexReader indexReader = DirectoryReader.open(directoryIndex);
			IndexSearcher searcher = new IndexSearcher(indexReader);
			//Get the last element of the previous page
			ScoreDoc lastSd = getLastScoreDoc(query, searcher, offset, limit, sort);
			if (offset > 0 && lastSd == null) {
				return new ArrayList<>();
			}
			//Search for elements on the next page through the last element
			TopDocs topDocs = searcher.searchAfter(lastSd, query, limit, sort);
			List<Document> documents = new ArrayList<>();
			for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
				documents.add(searcher.doc(scoreDoc.doc));
			}

			return documents;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public int count(Query query) {
		try {
			IndexReader indexReader = DirectoryReader.open(directoryIndex);
			IndexSearcher searcher = new IndexSearcher(indexReader);
			return searcher.count(query);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

}