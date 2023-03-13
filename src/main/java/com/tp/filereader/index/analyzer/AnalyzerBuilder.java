package com.tp.filereader.index.analyzer;



import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ar.ArabicAnalyzer;
import org.apache.lucene.analysis.bn.BengaliAnalyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.fa.PersianAnalyzer;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.th.ThaiAnalyzer;
import org.slf4j.Logger;

import com.tp.filereader.logger.TPLogger;


/**
 * Created by The TP Entertainment
 * Author : Phung Quang Nam
 *          nam.phung@tp.com.vn
 * Tue 23 08 2022
 */
public class AnalyzerBuilder {
	private static final Logger LOG = TPLogger.getLogger(AnalyzerBuilder.class);
	
	private static ArabicAnalyzer arabicAnalyzer;
	private static CJKAnalyzer cjkAnalyzer;
	private static ThaiAnalyzer thaiAnalyzer;
	private static RussianAnalyzer russianAnalyzer;
	private static PersianAnalyzer persianAnalyzer;
	private static BengaliAnalyzer bengaliAnalyzer;
	private static StandardAnalyzer standardAnalyzer;

	public static Analyzer getInstanceByCountry(String country) {
		LOG.info("getInstanceByCountry {}", country);
		switch (country.toLowerCase()) {
		case "eg":
			if (arabicAnalyzer == null) {
				arabicAnalyzer = new ArabicAnalyzer();
			}
			return arabicAnalyzer;
		case "pk":
			if (arabicAnalyzer == null) {
				arabicAnalyzer = new ArabicAnalyzer();
			}
			return arabicAnalyzer;
		case "jp":
			if (cjkAnalyzer == null) {
				LOG.info("Indexing by country JP");
				cjkAnalyzer = new CJKAnalyzer();
			}
			return cjkAnalyzer;
		case "kr":
			if (cjkAnalyzer == null) {
				LOG.info("Indexing by country KR");
				cjkAnalyzer = new CJKAnalyzer();
			}
			return cjkAnalyzer;
		case "tw":
			if (cjkAnalyzer == null) {
				LOG.info("Indexing by country TW");
				cjkAnalyzer = new CJKAnalyzer();
			}
			return cjkAnalyzer;
		case "ru":
			if (russianAnalyzer == null) {
				russianAnalyzer = new RussianAnalyzer();
			}
			return russianAnalyzer;
		case "ua":
			if (russianAnalyzer == null) {
				russianAnalyzer = new RussianAnalyzer();
			}
			return russianAnalyzer;
		case "th":
			if (thaiAnalyzer == null) {
				thaiAnalyzer = new ThaiAnalyzer();
			}
			return thaiAnalyzer;
		case "ir":
			if (persianAnalyzer == null) {
				persianAnalyzer = new PersianAnalyzer();
			}
			return persianAnalyzer;
		case "bd":
			if (bengaliAnalyzer == null) {
				bengaliAnalyzer = new BengaliAnalyzer();
			}
			return bengaliAnalyzer;
		default:
			break;
		}
		if (standardAnalyzer == null) {
			standardAnalyzer = new StandardAnalyzer();
		}
		return standardAnalyzer;
		
	}
	
	public static Analyzer getInstanceByLang(String lang) {
		LOG.info("getInstanceByLang {}", lang);
		switch (lang.toLowerCase()) {
		case "ar":
			if (arabicAnalyzer == null) {
				arabicAnalyzer = new ArabicAnalyzer();
			}
			return arabicAnalyzer;
		case "ur":
			if (arabicAnalyzer == null) {
				arabicAnalyzer = new ArabicAnalyzer();
			}
			return arabicAnalyzer;
		case "ja":
			if (cjkAnalyzer == null) {
				cjkAnalyzer = new CJKAnalyzer();
			}
			return cjkAnalyzer;
		case "ko":
			if (cjkAnalyzer == null) {
				cjkAnalyzer = new CJKAnalyzer();
			}
			
			return cjkAnalyzer;
		case "zh":
			if (cjkAnalyzer == null) {
				cjkAnalyzer = new CJKAnalyzer();
			}
			return cjkAnalyzer;
		case "cn":
			if (cjkAnalyzer == null) {
				cjkAnalyzer = new CJKAnalyzer();
			}
			return cjkAnalyzer;
		case "ru":
			if (russianAnalyzer == null) {
				russianAnalyzer = new RussianAnalyzer();
			}
			return russianAnalyzer;
		case "uk":
			if (russianAnalyzer == null) {
				russianAnalyzer = new RussianAnalyzer();
			}
			return russianAnalyzer;
		case "th":
			if (thaiAnalyzer == null) {
				thaiAnalyzer = new ThaiAnalyzer();
			}
			return thaiAnalyzer;
		case "fa":
			if (persianAnalyzer == null) {
				persianAnalyzer = new PersianAnalyzer();
			}
			return persianAnalyzer;
		case "bn":
			if (bengaliAnalyzer == null) {
				bengaliAnalyzer = new BengaliAnalyzer();
			}
			return bengaliAnalyzer;
		default:
			break;
		}
		
		if (standardAnalyzer == null) {
			standardAnalyzer = new StandardAnalyzer();
		}
		return standardAnalyzer;
	}
	
}
