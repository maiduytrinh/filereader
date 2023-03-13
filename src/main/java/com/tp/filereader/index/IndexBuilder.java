package com.tp.filereader.index;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.store.Directory;

/**
 * Created by The TP Entertainment
 * Author : Phung Quang Nam
 *          nam.phung@tp.com.vn
 * Tue 30 08 2022
 */
public class IndexBuilder {
	
	private static SearchIndex arabicIndexer;
	private static SearchIndex cjkIndexer;
	private static SearchIndex ruIndexer;
	private static SearchIndex thIndexer;
	private static SearchIndex irIndexer;
	private static SearchIndex bdIndexer;
	private static SearchIndex standardIndexer;
	
	public static SearchIndex getIndexer(Directory directoryIndex, Analyzer analyzer, String country) {
		switch (country.toLowerCase()) {
		case "eg":
			if (arabicIndexer == null) {
				arabicIndexer = new SearchIndex(directoryIndex, analyzer);
			}
			return arabicIndexer;
		case "pk":
			if (arabicIndexer == null) {
				arabicIndexer = new SearchIndex(directoryIndex, analyzer);
			}
			return arabicIndexer;
		case "jp":
			if (cjkIndexer == null) {
				cjkIndexer = new SearchIndex(directoryIndex, analyzer);
			}
			return cjkIndexer;
		case "kr":
			if (cjkIndexer == null) {
				cjkIndexer = new SearchIndex(directoryIndex, analyzer);
			}
			return cjkIndexer;
		case "tw":
			if (cjkIndexer == null) {
				cjkIndexer = new SearchIndex(directoryIndex, analyzer);
			}
			return cjkIndexer;
		case "ru":
			if (ruIndexer == null) {
				ruIndexer = new SearchIndex(directoryIndex, analyzer);
			}
			return ruIndexer;
		case "ua":
			if (ruIndexer == null) {
				ruIndexer = new SearchIndex(directoryIndex, analyzer);
			}
			return ruIndexer;
		case "th":
			if (thIndexer == null) {
				thIndexer = new SearchIndex(directoryIndex, analyzer);
			}
			return thIndexer;
		case "ir":
			if (irIndexer == null) {
				irIndexer = new SearchIndex(directoryIndex, analyzer);
			}
			return irIndexer;
		case "bd":
			if (bdIndexer == null) {
				bdIndexer = new SearchIndex(directoryIndex, analyzer);
			}
			return bdIndexer;
		default:
			break;
		}
		if (standardIndexer == null) {
			standardIndexer = new SearchIndex(directoryIndex, analyzer);
		}
		return standardIndexer;
	}

}
